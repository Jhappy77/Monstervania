// Made with Blockbench 3.6.6
// Exported for Minecraft version 1.12.2 or 1.15.2 (same format for both) for entity models animated with GeckoLib
// Paste this class into your mod and follow the documentation for GeckoLib to use animations. You can find the documentation here: https://github.com/bernie-g/geckolib
// Blockbench plugin created by Gecko
package com.jhappy77.monstervania.client.model;

import com.jhappy77.monstervania.entities.RatEntity;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib.animation.model.AnimatedEntityModel;
import software.bernie.geckolib.animation.render.AnimatedModelRenderer;

public class RatModel<T extends RatEntity> extends AnimatedEntityModel<T> {

    private final AnimatedModelRenderer body;
	private final AnimatedModelRenderer ratBody;
	private final AnimatedModelRenderer ratHead;
	private final AnimatedModelRenderer ears;
	private final AnimatedModelRenderer ratHindLegRight;
	private final AnimatedModelRenderer ratTail;
	private final AnimatedModelRenderer tailLower;
	private final AnimatedModelRenderer ratHindLegLeft;
	private final AnimatedModelRenderer ratFrontLegLeft;
	private final AnimatedModelRenderer ratFrontLegRight;

    public RatModel()
    {
    	textureWidth = 128;
    	textureHeight = 128;
    body = new AnimatedModelRenderer(this);
		body.setRotationPoint(0.0F, 15.0F, 15.0F);
		
		body.setModelRendererName("body");
		this.registerModelRenderer(body);

		ratBody = new AnimatedModelRenderer(this);
		ratBody.setRotationPoint(0.0F, 0.0F, -15.0F);
		body.addChild(ratBody);
		ratBody.setTextureOffset(0, 27).addBox(-6.0F, -5.0F, -9.0F, 12.0F, 10.0F, 11.0F, 0.0F, false);
		ratBody.setTextureOffset(0, 0).addBox(-7.0F, -8.0F, 2.0F, 14.0F, 14.0F, 13.0F, 0.0F, false);
		ratBody.setModelRendererName("ratBody");
		this.registerModelRenderer(ratBody);

		ratHead = new AnimatedModelRenderer(this);
		ratHead.setRotationPoint(0.0F, -1.0F, -24.0F);
		body.addChild(ratHead);
		setRotationAngle(ratHead, 0.2182F, 0.0F, 0.0F);
		ratHead.setTextureOffset(34, 36).addBox(-4.0F, -3.0F, -11.0F, 8.0F, 6.0F, 12.0F, 0.0F, false);
		ratHead.setTextureOffset(0, 3).addBox(-6.0F, -5.7519F, -1.6034F, 3.0F, 3.0F, 0.0F, 0.0F, false);
		ratHead.setTextureOffset(0, 0).addBox(2.0F, -5.7519F, -1.6034F, 3.0F, 3.0F, 0.0F, 0.0F, false);
		ratHead.setTextureOffset(0, 48).addBox(-2.0F, -3.0F, -12.0F, 4.0F, 3.0F, 1.0F, 0.0F, false);
		ratHead.setTextureOffset(0, 84).addBox(-8.0F, -5.0F, -12.0F, 16.0F, 7.0F, 0.0F, 0.01F, false);
		ratHead.setModelRendererName("ratHead");
		this.registerModelRenderer(ratHead);

		ears = new AnimatedModelRenderer(this);
		ears.setRotationPoint(-4.0F, 5.0F, -1.0F);
		ratHead.addChild(ears);
		
		ears.setModelRendererName("ears");
		this.registerModelRenderer(ears);

		ratHindLegRight = new AnimatedModelRenderer(this);
		ratHindLegRight.setRotationPoint(6.0F, 1.0F, -2.0F);
		body.addChild(ratHindLegRight);
		ratHindLegRight.setTextureOffset(58, 58).addBox(1.0F, -5.0F, -6.0F, 3.0F, 11.0F, 9.0F, 0.0F, false);
		ratHindLegRight.setTextureOffset(62, 14).addBox(1.0F, 6.0F, -10.0F, 3.0F, 2.0F, 10.0F, 0.0F, false);
		ratHindLegRight.setModelRendererName("ratHindLegRight");
		this.registerModelRenderer(ratHindLegRight);

		ratTail = new AnimatedModelRenderer(this);
		ratTail.setRotationPoint(0.0F, 0.0F, 0.0F);
		body.addChild(ratTail);
		setRotationAngle(ratTail, -0.1745F, 0.0F, 0.0F);
		ratTail.setTextureOffset(0, 48).addBox(-2.0F, -3.8264F, -0.9848F, 4.0F, 4.0F, 13.0F, 0.1F, false);
		ratTail.setModelRendererName("ratTail");
		this.registerModelRenderer(ratTail);

		tailLower = new AnimatedModelRenderer(this);
		tailLower.setRotationPoint(0.0F, -1.0F, 12.0F);
		ratTail.addChild(tailLower);
		setRotationAngle(tailLower, 0.1309F, 0.0F, 0.0F);
		tailLower.setTextureOffset(40, 13).addBox(-2.0F, -2.65F, 0.0F, 4.0F, 4.0F, 14.0F, 0.0F, false);
		tailLower.setModelRendererName("tailLower");
		this.registerModelRenderer(tailLower);

		ratHindLegLeft = new AnimatedModelRenderer(this);
		ratHindLegLeft.setRotationPoint(-7.0F, 1.0F, -2.0F);
		body.addChild(ratHindLegLeft);
		ratHindLegLeft.setTextureOffset(34, 54).addBox(-3.0F, -5.0F, -6.0F, 3.0F, 11.0F, 9.0F, 0.0F, false);
		ratHindLegLeft.setTextureOffset(62, 2).addBox(-3.0F, 6.0F, -10.0F, 3.0F, 2.0F, 10.0F, 0.0F, false);
		ratHindLegLeft.setModelRendererName("ratHindLegLeft");
		this.registerModelRenderer(ratHindLegLeft);

		ratFrontLegLeft = new AnimatedModelRenderer(this);
		ratFrontLegLeft.setRotationPoint(-7.0F, 1.0F, -19.0F);
		body.addChild(ratFrontLegLeft);
		ratFrontLegLeft.setTextureOffset(17, 69).addBox(-2.0F, -2.0F, -4.0F, 3.0F, 8.0F, 7.0F, 0.0F, false);
		ratFrontLegLeft.setTextureOffset(0, 65).addBox(-2.0F, 6.0F, -7.0F, 3.0F, 2.0F, 9.0F, 0.0F, false);
		ratFrontLegLeft.setModelRendererName("ratFrontLegLeft");
		this.registerModelRenderer(ratFrontLegLeft);

		ratFrontLegRight = new AnimatedModelRenderer(this);
		ratFrontLegRight.setRotationPoint(6.0F, 1.0F, -19.0F);
		body.addChild(ratFrontLegRight);
		ratFrontLegRight.setTextureOffset(62, 31).addBox(0.0F, -2.0F, -4.0F, 3.0F, 8.0F, 7.0F, 0.0F, false);
		ratFrontLegRight.setTextureOffset(41, 0).addBox(0.0F, 6.0F, -8.0F, 3.0F, 2.0F, 10.0F, 0.0F, false);
		ratFrontLegRight.setModelRendererName("ratFrontLegRight");
		this.registerModelRenderer(ratFrontLegRight);

    this.rootBones.add(body);
  }


    @Override
    public ResourceLocation getAnimationFileLocation()
    {
        return new ResourceLocation("monstervania", "animations/rat_entity.json");
    }
}