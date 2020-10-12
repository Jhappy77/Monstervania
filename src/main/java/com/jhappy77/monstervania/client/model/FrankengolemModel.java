package com.jhappy77.monstervania.client.model;



import com.google.common.collect.ImmutableList;
import com.jhappy77.monstervania.Monstervania;
import com.jhappy77.monstervania.entities.FrankengolemEntity;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.entity.model.IronGolemModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.IronGolemEntity;
import net.minecraft.util.math.MathHelper;

public class FrankengolemModel <T extends FrankengolemEntity> extends EntityModel<T> {
    private final ModelRenderer golemBody;
    private final ModelRenderer golemHead;
    private final ModelRenderer golemRightArm;
    private final ModelRenderer golemLeftArm;
    private final ModelRenderer golemRightLeg;
    private final ModelRenderer golemLeftLeg;

    public FrankengolemModel() {
        textureWidth = 128;
        textureHeight = 128;

        golemBody = new ModelRenderer(this);
        golemBody.setRotationPoint(0.0F, -7.0F, 0.0F);
        golemBody.setTextureOffset(0, 40).addBox(-9.0F, -2.0F, -6.0F, 18.0F, 12.0F, 11.0F, 0.0F, false);
        golemBody.setTextureOffset(0, 70).addBox(-4.5F, 10.0F, -3.0F, 9.0F, 5.0F, 6.0F, 0.5F, false);

        golemHead = new ModelRenderer(this);
        golemHead.setRotationPoint(0.0F, -7.0F, -2.0F);
        golemHead.setTextureOffset(0, 0).addBox(-4.0F, -12.0F, -5.5F, 8.0F, 10.0F, 8.0F, 0.0F, false);
        golemHead.setTextureOffset(24, 0).addBox(-1.0F, -5.0F, -7.5F, 2.0F, 4.0F, 2.0F, 0.0F, false);
        golemHead.setTextureOffset(24, 18).addBox(4.0F, -9.0F, -2.5F, 2.0F, 2.0F, 2.0F, 0.0F, false);
        golemHead.setTextureOffset(24, 18).addBox(-6.0F, -9.0F, -2.5F, 2.0F, 2.0F, 2.0F, 0.0F, false);

        golemRightArm = new ModelRenderer(this);
        golemRightArm.setRotationPoint(0.0F, -7.0F, 0.0F);
        golemRightArm.setTextureOffset(60, 21).addBox(-13.0F, -2.5F, -3.0F, 4.0F, 30.0F, 6.0F, 0.0F, false);

        golemLeftArm = new ModelRenderer(this);
        golemLeftArm.setRotationPoint(0.0F, -7.0F, 0.0F);
        golemLeftArm.setTextureOffset(60, 58).addBox(9.0F, -2.5F, -3.0F, 4.0F, 30.0F, 6.0F, 0.0F, false);

        golemRightLeg = new ModelRenderer(this);
        golemRightLeg.setRotationPoint(-5.0F, 11.0F, 0.0F);
        golemRightLeg.setTextureOffset(37, 0).addBox(-2.5F, -3.0F, -3.0F, 6.0F, 16.0F, 5.0F, 0.0F, false);

        golemLeftLeg = new ModelRenderer(this);
        golemLeftLeg.setRotationPoint(4.0F, 11.0F, 0.0F);
        golemLeftLeg.setTextureOffset(60, 0).addBox(-2.5F, -3.0F, -3.0F, 6.0F, 16.0F, 5.0F, 0.0F, false);
    }

    public Iterable<ModelRenderer> getParts() {
        return ImmutableList.of(this.golemHead, this.golemBody, this.golemLeftLeg, this.golemRightLeg, this.golemRightArm, this.golemLeftArm);
    }

    /**
     * Sets this entity's model rotation angles
     */
    public void setRotationAngles(T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.golemHead.rotateAngleY = netHeadYaw * ((float)Math.PI / 180F);
        this.golemHead.rotateAngleX = headPitch * ((float)Math.PI / 180F);
        this.golemLeftLeg.rotateAngleX = -1.5F * MathHelper.func_233021_e_(limbSwing, 13.0F) * limbSwingAmount;
        this.golemRightLeg.rotateAngleX = 1.5F * MathHelper.func_233021_e_(limbSwing, 13.0F) * limbSwingAmount;
        this.golemLeftLeg.rotateAngleY = 0.0F;
        this.golemRightLeg.rotateAngleY = 0.0F;
        int i = entityIn.getAttackTimer();
        if(i>0) {
            this.golemRightArm.rotateAngleX = -2.0F + 1.5F * MathHelper.func_233021_e_((float) i, 10.0F);
            this.golemLeftArm.rotateAngleX = -2.0F + 1.5F * MathHelper.func_233021_e_((float) i, 10.0F);
        }
    }

    public void setLivingAnimations(T entityIn, float limbSwing, float limbSwingAmount, float partialTick) {
        int i = entityIn.getAttackTimer();
        if (i > 0) {
            this.golemRightArm.rotateAngleX = -2.0F + 1.5F * MathHelper.func_233021_e_((float)i - partialTick, 10.0F);
            this.golemLeftArm.rotateAngleX = -2.0F + 1.5F * MathHelper.func_233021_e_((float)i - partialTick, 10.0F);
            Monstervania.LOGGER.debug("franku attacku???");
        } else {
                this.golemRightArm.rotateAngleX = (-0.2F + 1.5F * MathHelper.func_233021_e_(limbSwing, 13.0F)) * limbSwingAmount;
                this.golemLeftArm.rotateAngleX = (-0.2F - 1.5F * MathHelper.func_233021_e_(limbSwing, 13.0F)) * limbSwingAmount;
        }
    }

    @Override
    public void render(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha){
        golemBody.render(matrixStack, buffer, packedLight, packedOverlay);
        golemHead.render(matrixStack, buffer, packedLight, packedOverlay);
        golemRightArm.render(matrixStack, buffer, packedLight, packedOverlay);
        golemLeftArm.render(matrixStack, buffer, packedLight, packedOverlay);
        golemRightLeg.render(matrixStack, buffer, packedLight, packedOverlay);
        golemLeftLeg.render(matrixStack, buffer, packedLight, packedOverlay);
    }

    public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }
}
