package com.jhappy77.monstervania.client.model;

import com.jhappy77.monstervania.Monstervania;
import com.jhappy77.monstervania.entities.VampireEntity;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.entity.model.ZombieModel;
import net.minecraft.client.renderer.model.ModelHelper;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.HandSide;
import net.minecraft.util.math.MathHelper;

public class VampireModel<T extends VampireEntity> extends EntityModel<T> {

        private final ModelRenderer head;
        private final ModelRenderer body;
        private final ModelRenderer left_arm;
        private final ModelRenderer right_arm;
        private final ModelRenderer left_leg;
        private final ModelRenderer right_leg;


        public VampireModel() {
            textureWidth = 64;
            textureHeight = 64;

            head = new ModelRenderer(this);
                head.setRotationPoint(0.0F, 0.0F, 0.0F);
                setRotationAngle(head, 0.0F, 3.1416F, 0.0F);
                head.setTextureOffset(0, 0).addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, 0.0F, true);
                head.setTextureOffset(0, 3).addBox(4.0F, -6.0F, 0.0F, 2.0F, 3.0F, 0.0F, 0.0F, true);
                head.setTextureOffset(0, 0).addBox(-6.0F, -6.0F, 0.0F, 2.0F, 3.0F, 0.0F, 0.0F, true);

                body = new ModelRenderer(this);
                body.setRotationPoint(0.0F, 0.0F, 0.0F);
                setRotationAngle(body, 0.0F, 3.1416F, 0.0F);
                body.setTextureOffset(0, 16).addBox(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F, 0.0F, false);

                left_arm = new ModelRenderer(this);
                left_arm.setRotationPoint(-5.0F, 2.0F, 0.0F);
                setRotationAngle(left_arm, -1.5708F, 0.0F, 0.0F);
                left_arm.setTextureOffset(32, 0).addBox(9.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, 0.0F, false);

                right_arm = new ModelRenderer(this);
                right_arm.setRotationPoint(5.0F, 2.0F, 0.0F);
                setRotationAngle(right_arm, -1.5708F, 0.0F, 0.0F);
                right_arm.setTextureOffset(32, 0).addBox(-13.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, 0.0F, false);

                left_leg = new ModelRenderer(this);
                left_leg.setRotationPoint(-1.9F, 12.0F, 0.0F);
                left_leg.setTextureOffset(0, 32).addBox(1.9F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, 0.0F, false);

                right_leg = new ModelRenderer(this);
                right_leg.setRotationPoint(1.9F, 12.0F, 0.0F);
                right_leg.setTextureOffset(24, 24).addBox(-5.9F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, 0.0F, false);

        }

    public void setLivingAnimations(T entityIn, float limbSwing, float limbSwingAmount, float partialTick) {
        int i = entityIn.getAttackTimer();
        if (i > 0) {
            this.right_arm.rotateAngleX = -2.0F + 1.5F * MathHelper.func_233021_e_((float)i - partialTick, 10.0F);
            this.left_arm.rotateAngleX = -2.0F + 1.5F * MathHelper.func_233021_e_((float)i - partialTick, 10.0F);
            Monstervania.LOGGER.debug("vampu attacku");
        } else {
//            this.golemRightArm.rotateAngleX = (-0.2F + 1.5F * MathHelper.func_233021_e_(limbSwing, 13.0F)) * limbSwingAmount;
//            this.golemLeftArm.rotateAngleX = (-0.2F - 1.5F * MathHelper.func_233021_e_(limbSwing, 13.0F)) * limbSwingAmount;
        }
    }


    @Override
    public void setRotationAngles(T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) { boolean flag = entityIn.getTicksElytraFlying() > 4;
        boolean flag1 = entityIn.isActualySwimming();
        this.head.rotateAngleY = netHeadYaw * ((float)Math.PI / 180F);
        if (flag) {
            this.head.rotateAngleX = (-(float)Math.PI / 4F);
        }
         else {
            this.head.rotateAngleX = headPitch * ((float)Math.PI / 180F);
        }

         /* else if (this.swimAnimation > 0.0F) {
            if (flag1) {
                this.head.rotateAngleX = this.rotLerpRad(this.swimAnimation, this.head.rotateAngleX, (-(float)Math.PI / 4F));
            } else {
                this.head.rotateAngleX = this.rotLerpRad(this.swimAnimation, this.head.rotateAngleX, headPitch * ((float)Math.PI / 180F));
            }*/

        this.body.rotateAngleY = 0.0F;
//        this.right_arm.rotationPointZ = 0.0F;
//        this.right_arm.rotationPointX = -5.0F;
//        this.left_arm.rotationPointZ = 0.0F;
//        this.left_arm.rotationPointX = 5.0F;
        float f = 1.0F;
        if (flag) {
            f = (float)entityIn.getMotion().lengthSquared();
            f = f / 0.2F;
            f = f * f * f;
        }

        if (f < 1.0F) {
            f = 1.0F;
        }

        this.right_leg.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount / f;
        this.left_leg.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + (float)Math.PI) * 1.4F * limbSwingAmount / f;
        this.right_leg.rotateAngleY = 0.0F;
        this.left_leg.rotateAngleY = 0.0F;
        this.right_leg.rotateAngleZ = 0.0F;
        this.left_leg.rotateAngleZ = 0.0F;


        this.right_arm.rotateAngleY = 0.0F;
        this.left_arm.rotateAngleY = 0.0F;
//        boolean flag2 = entityIn.getPrimaryHand() == HandSide.RIGHT;
//        boolean flag3 = flag2 ? this.leftArmPose.func_241657_a_() : this.rightArmPose.func_241657_a_();
//        if (flag2 != flag3) {
//            this.func_241655_c_(entityIn);
//            this.func_241654_b_(entityIn);
//        } else {
//            this.func_241654_b_(entityIn);
//            this.func_241655_c_(entityIn);
//        }

//        this.func_230486_a_(entityIn, ageInTicks);
//        if (this.isSneak) {
//            this.body.rotateAngleX = 0.5F;
//            this.right_arm.rotateAngleX += 0.4F;
//            this.left_arm.rotateAngleX += 0.4F;
//            this.right_leg.rotationPointZ = 4.0F;
//            this.left_leg.rotationPointZ = 4.0F;
//            this.right_leg.rotationPointY = 12.2F;
//            this.left_leg.rotationPointY = 12.2F;
//            this.head.rotationPointY = 4.2F;
//            this.body.rotationPointY = 3.2F;
//            this.left_arm.rotationPointY = 5.2F;
//            this.right_arm.rotationPointY = 5.2F;
//        } else {
//            this.body.rotateAngleX = 0.0F;
//            this.right_leg.rotationPointZ = 0.1F;
//            this.left_leg.rotationPointZ = 0.1F;
//            this.right_leg.rotationPointY = 12.0F;
//            this.left_leg.rotationPointY = 12.0F;
//            this.head.rotationPointY = 0.0F;
//            this.body.rotationPointY = 0.0F;
//            this.left_arm.rotationPointY = 2.0F;
//            this.right_arm.rotationPointY = 2.0F;
//        }

        //ModelHelper.func_239101_a_(this.right_arm, this.left_arm, ageInTicks);
//        if (this.swimAnimation > 0.0F) {
//            float f1 = limbSwing % 26.0F;
//            HandSide handside = this.getMainHand(entityIn);
//            float f2 = handside == HandSide.RIGHT && this.swingProgress > 0.0F ? 0.0F : this.swimAnimation;
//            float f3 = handside == HandSide.LEFT && this.swingProgress > 0.0F ? 0.0F : this.swimAnimation;
//            if (f1 < 14.0F) {
//                this.left_arm.rotateAngleX = this.rotLerpRad(f3, this.left_arm.rotateAngleX, 0.0F);
//                this.right_arm.rotateAngleX = MathHelper.lerp(f2, this.right_arm.rotateAngleX, 0.0F);
//                this.left_arm.rotateAngleY = this.rotLerpRad(f3, this.left_arm.rotateAngleY, (float)Math.PI);
//                this.right_arm.rotateAngleY = MathHelper.lerp(f2, this.right_arm.rotateAngleY, (float)Math.PI);
//                this.left_arm.rotateAngleZ = this.rotLerpRad(f3, this.left_arm.rotateAngleZ, (float)Math.PI + 1.8707964F * this.getArmAngleSq(f1) / this.getArmAngleSq(14.0F));
//                this.right_arm.rotateAngleZ = MathHelper.lerp(f2, this.right_arm.rotateAngleZ, (float)Math.PI - 1.8707964F * this.getArmAngleSq(f1) / this.getArmAngleSq(14.0F));
//            } else if (f1 >= 14.0F && f1 < 22.0F) {
//                float f6 = (f1 - 14.0F) / 8.0F;
//                this.left_arm.rotateAngleX = this.rotLerpRad(f3, this.left_arm.rotateAngleX, ((float)Math.PI / 2F) * f6);
//                this.right_arm.rotateAngleX = MathHelper.lerp(f2, this.right_arm.rotateAngleX, ((float)Math.PI / 2F) * f6);
//                this.left_arm.rotateAngleY = this.rotLerpRad(f3, this.left_arm.rotateAngleY, (float)Math.PI);
//                this.right_arm.rotateAngleY = MathHelper.lerp(f2, this.right_arm.rotateAngleY, (float)Math.PI);
//                this.left_arm.rotateAngleZ = this.rotLerpRad(f3, this.left_arm.rotateAngleZ, 5.012389F - 1.8707964F * f6);
//                this.right_arm.rotateAngleZ = MathHelper.lerp(f2, this.right_arm.rotateAngleZ, 1.2707963F + 1.8707964F * f6);
//            } else if (f1 >= 22.0F && f1 < 26.0F) {
//                float f4 = (f1 - 22.0F) / 4.0F;
//                this.left_arm.rotateAngleX = this.rotLerpRad(f3, this.left_arm.rotateAngleX, ((float)Math.PI / 2F) - ((float)Math.PI / 2F) * f4);
//                this.right_arm.rotateAngleX = MathHelper.lerp(f2, this.right_arm.rotateAngleX, ((float)Math.PI / 2F) - ((float)Math.PI / 2F) * f4);
//                this.left_arm.rotateAngleY = this.rotLerpRad(f3, this.left_arm.rotateAngleY, (float)Math.PI);
//                this.right_arm.rotateAngleY = MathHelper.lerp(f2, this.right_arm.rotateAngleY, (float)Math.PI);
//                this.left_arm.rotateAngleZ = this.rotLerpRad(f3, this.left_arm.rotateAngleZ, (float)Math.PI);
//                this.right_arm.rotateAngleZ = MathHelper.lerp(f2, this.right_arm.rotateAngleZ, (float)Math.PI);
//            }

//            float f7 = 0.3F;
//            float f5 = 0.33333334F;
//            this.left_leg.rotateAngleX = MathHelper.lerp(this.swimAnimation, this.left_leg.rotateAngleX, 0.3F * MathHelper.cos(limbSwing * 0.33333334F + (float)Math.PI));
//            this.right_leg.rotateAngleX = MathHelper.lerp(this.swimAnimation, this.right_leg.rotateAngleX, 0.3F * MathHelper.cos(limbSwing * 0.33333334F));

        //this.headwear.copyModelAngles(this.head);

    }



    @Override
        public void render(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha){
            head.render(matrixStack, buffer, packedLight, packedOverlay);
            body.render(matrixStack, buffer, packedLight, packedOverlay);
            left_arm.render(matrixStack, buffer, packedLight, packedOverlay);
            right_arm.render(matrixStack, buffer, packedLight, packedOverlay);
            left_leg.render(matrixStack, buffer, packedLight, packedOverlay);
            right_leg.render(matrixStack, buffer, packedLight, packedOverlay);
        }

        public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
            modelRenderer.rotateAngleX = x;
            modelRenderer.rotateAngleY = y;
            modelRenderer.rotateAngleZ = z;
        }
}
