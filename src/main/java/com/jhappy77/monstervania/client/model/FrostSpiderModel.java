package com.jhappy77.monstervania.client.model;

import com.jhappy77.monstervania.entities.FrostSpiderEntity;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.entity.model.SpiderModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;

public class FrostSpiderModel<T extends FrostSpiderEntity> extends EntityModel<T> {
        private final ModelRenderer spiderHead;
        private final ModelRenderer spiderNeck;
        private final ModelRenderer spiderBody;
        private final ModelRenderer spiderLeg1;
        private final ModelRenderer spiderLeg2;
        private final ModelRenderer spiderLeg3;
        private final ModelRenderer spiderLeg4;
        private final ModelRenderer spiderLeg5;
        private final ModelRenderer spiderLeg6;
        private final ModelRenderer spiderLeg7;
        private final ModelRenderer spiderLeg8;

        public FrostSpiderModel() {
            textureWidth = 64;
            textureHeight = 32;

            spiderHead = new ModelRenderer(this);
            spiderHead.setRotationPoint(0.0F, 15.0F, -3.0F);
            spiderHead.setTextureOffset(32, 4).addBox(-5.0F, -6.0F, -8.0F, 10.0F, 10.0F, 8.0F, 0.0F, false);

            spiderNeck = new ModelRenderer(this);
            spiderNeck.setRotationPoint(0.0F, 15.0F, 0.0F);
            spiderNeck.setTextureOffset(0, 0).addBox(-3.0F, -3.0F, -3.0F, 6.0F, 6.0F, 6.0F, 0.0F, false);

            spiderBody = new ModelRenderer(this);
            spiderBody.setRotationPoint(0.0F, 15.0F, 9.0F);
            spiderBody.setTextureOffset(0, 12).addBox(-9.0F, -11.0F, -7.0F, 19.0F, 15.0F, 19.0F, 0.0F, false);

            spiderLeg1 = new ModelRenderer(this);
            spiderLeg1.setRotationPoint(4.0F, 15.0F, 4.0F);
            spiderLeg1.setTextureOffset(18, 0).addBox(-23.0F, -1.0F, -1.0F, 16.0F, 2.0F, 2.0F, 0.0F, false);

            spiderLeg2 = new ModelRenderer(this);
            spiderLeg2.setRotationPoint(-4.0F, 15.0F, 4.0F);
            spiderLeg2.setTextureOffset(18, 0).addBox(7.0F, -1.0F, -1.0F, 16.0F, 2.0F, 2.0F, 0.0F, false);

            spiderLeg3 = new ModelRenderer(this);
            spiderLeg3.setRotationPoint(4.0F, 15.0F, 1.0F);
            spiderLeg3.setTextureOffset(18, 0).addBox(-23.0F, -1.0F, -1.0F, 16.0F, 2.0F, 2.0F, 0.0F, false);

            spiderLeg4 = new ModelRenderer(this);
            spiderLeg4.setRotationPoint(-4.0F, 15.0F, 1.0F);
            spiderLeg4.setTextureOffset(18, 0).addBox(7.0F, -1.0F, -1.0F, 16.0F, 2.0F, 2.0F, 0.0F, false);

            spiderLeg5 = new ModelRenderer(this);
            spiderLeg5.setRotationPoint(4.0F, 15.0F, -2.0F);
            spiderLeg5.setTextureOffset(18, 0).addBox(-23.0F, -1.0F, -1.0F, 16.0F, 2.0F, 2.0F, 0.0F, false);

            spiderLeg6 = new ModelRenderer(this);
            spiderLeg6.setRotationPoint(-4.0F, 15.0F, -2.0F);
            spiderLeg6.setTextureOffset(18, 0).addBox(7.0F, -1.0F, -1.0F, 16.0F, 2.0F, 2.0F, 0.0F, false);

            spiderLeg7 = new ModelRenderer(this);
            spiderLeg7.setRotationPoint(4.0F, 15.0F, -5.0F);
            spiderLeg7.setTextureOffset(18, 0).addBox(-23.0F, -1.0F, -1.0F, 16.0F, 2.0F, 2.0F, 0.0F, false);

            spiderLeg8 = new ModelRenderer(this);
            spiderLeg8.setRotationPoint(-4.0F, 15.0F, -5.0F);
            spiderLeg8.setTextureOffset(18, 0).addBox(7.0F, -1.0F, -1.0F, 16.0F, 2.0F, 2.0F, 0.0F, false);
        }


        @Override
        public void render(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha){
            spiderHead.render(matrixStack, buffer, packedLight, packedOverlay);
            spiderNeck.render(matrixStack, buffer, packedLight, packedOverlay);
            spiderBody.render(matrixStack, buffer, packedLight, packedOverlay);
            spiderLeg1.render(matrixStack, buffer, packedLight, packedOverlay);
            spiderLeg2.render(matrixStack, buffer, packedLight, packedOverlay);
            spiderLeg3.render(matrixStack, buffer, packedLight, packedOverlay);
            spiderLeg4.render(matrixStack, buffer, packedLight, packedOverlay);
            spiderLeg5.render(matrixStack, buffer, packedLight, packedOverlay);
            spiderLeg6.render(matrixStack, buffer, packedLight, packedOverlay);
            spiderLeg7.render(matrixStack, buffer, packedLight, packedOverlay);
            spiderLeg8.render(matrixStack, buffer, packedLight, packedOverlay);
        }

        public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
            modelRenderer.rotateAngleX = x;
            modelRenderer.rotateAngleY = y;
            modelRenderer.rotateAngleZ = z;
        }

    /**
     * Sets this entity's model rotation angles
     */
    public void setRotationAngles(T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.spiderHead.rotateAngleY = netHeadYaw * ((float)Math.PI / 180F);
        this.spiderHead.rotateAngleX = headPitch * ((float)Math.PI / 180F);
        float f = ((float)Math.PI / 4F);
        this.spiderLeg1.rotateAngleZ = (-(float)Math.PI / 4F);
        this.spiderLeg2.rotateAngleZ = ((float)Math.PI / 4F);
        this.spiderLeg3.rotateAngleZ = -0.58119464F;
        this.spiderLeg4.rotateAngleZ = 0.58119464F;
        this.spiderLeg5.rotateAngleZ = -0.58119464F;
        this.spiderLeg6.rotateAngleZ = 0.58119464F;
        this.spiderLeg7.rotateAngleZ = (-(float)Math.PI / 4F);
        this.spiderLeg8.rotateAngleZ = ((float)Math.PI / 4F);
        float f1 = -0.0F;
        float f2 = ((float)Math.PI / 8F);
        this.spiderLeg1.rotateAngleY = ((float)Math.PI / 4F);
        this.spiderLeg2.rotateAngleY = (-(float)Math.PI / 4F);
        this.spiderLeg3.rotateAngleY = ((float)Math.PI / 8F);
        this.spiderLeg4.rotateAngleY = (-(float)Math.PI / 8F);
        this.spiderLeg5.rotateAngleY = (-(float)Math.PI / 8F);
        this.spiderLeg6.rotateAngleY = ((float)Math.PI / 8F);
        this.spiderLeg7.rotateAngleY = (-(float)Math.PI / 4F);
        this.spiderLeg8.rotateAngleY = ((float)Math.PI / 4F);
        float f3 = -(MathHelper.cos(limbSwing * 0.6662F * 2.0F + 0.0F) * 0.4F) * limbSwingAmount;
        float f4 = -(MathHelper.cos(limbSwing * 0.6662F * 2.0F + (float)Math.PI) * 0.4F) * limbSwingAmount;
        float f5 = -(MathHelper.cos(limbSwing * 0.6662F * 2.0F + ((float)Math.PI / 2F)) * 0.4F) * limbSwingAmount;
        float f6 = -(MathHelper.cos(limbSwing * 0.6662F * 2.0F + ((float)Math.PI * 1.5F)) * 0.4F) * limbSwingAmount;
        float f7 = Math.abs(MathHelper.sin(limbSwing * 0.6662F + 0.0F) * 0.4F) * limbSwingAmount;
        float f8 = Math.abs(MathHelper.sin(limbSwing * 0.6662F + (float)Math.PI) * 0.4F) * limbSwingAmount;
        float f9 = Math.abs(MathHelper.sin(limbSwing * 0.6662F + ((float)Math.PI / 2F)) * 0.4F) * limbSwingAmount;
        float f10 = Math.abs(MathHelper.sin(limbSwing * 0.6662F + ((float)Math.PI * 1.5F)) * 0.4F) * limbSwingAmount;
        this.spiderLeg1.rotateAngleY += f3;
        this.spiderLeg2.rotateAngleY += -f3;
        this.spiderLeg3.rotateAngleY += f4;
        this.spiderLeg4.rotateAngleY += -f4;
        this.spiderLeg5.rotateAngleY += f5;
        this.spiderLeg6.rotateAngleY += -f5;
        this.spiderLeg7.rotateAngleY += f6;
        this.spiderLeg8.rotateAngleY += -f6;
        this.spiderLeg1.rotateAngleZ += f7;
        this.spiderLeg2.rotateAngleZ += -f7;
        this.spiderLeg3.rotateAngleZ += f8;
        this.spiderLeg4.rotateAngleZ += -f8;
        this.spiderLeg5.rotateAngleZ += f9;
        this.spiderLeg6.rotateAngleZ += -f9;
        this.spiderLeg7.rotateAngleZ += f10;
        this.spiderLeg8.rotateAngleZ += -f10;
    }

    }
