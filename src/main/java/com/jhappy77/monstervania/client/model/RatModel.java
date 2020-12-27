// Made with Blockbench 3.6.6
// Exported for Minecraft version 1.12.2 or 1.15.2 (same format for both) for entity models animated with GeckoLib
// Paste this class into your mod and follow the documentation for GeckoLib to use animations. You can find the documentation here: https://github.com/bernie-g/geckolib
// Blockbench plugin created by Gecko
package com.jhappy77.monstervania.client.model;

import com.jhappy77.monstervania.Monstervania;
import com.jhappy77.monstervania.entities.RatEntity;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class RatModel extends AnimatedGeoModel<RatEntity> {

	@Override
	public ResourceLocation getModelLocation(RatEntity object)
	{
		return new ResourceLocation(Monstervania.MOD_ID, "geo/ratmodel.geo.json");
	}

	@Override
	public ResourceLocation getTextureLocation(RatEntity object)
	{
		return new ResourceLocation(Monstervania.MOD_ID, "textures/entity/rat.png");
	}

	@Override
	public ResourceLocation getAnimationFileLocation(RatEntity object)
	{
		return new ResourceLocation(Monstervania.MOD_ID, "animations/ratmodel.animation.json");
		// "animations/rat_entity.json"
	}

}