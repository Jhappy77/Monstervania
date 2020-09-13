package com.jhappy77.monstervania.init;

import com.jhappy77.monstervania.Monstervania;
import com.jhappy77.monstervania.entities.VampireEntity;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModEntityTypes {
    public static DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.ENTITIES, Monstervania.MOD_ID);

    //Entity types
    public static final RegistryObject<EntityType<VampireEntity>> VAMPIRE = ENTITY_TYPES.register("vampire",
            ()-> EntityType.Builder.create(VampireEntity::new, EntityClassification.CREATURE)
    .size(1.0f, 1.0f)
    .build(new ResourceLocation(Monstervania.MOD_ID, "vampire").toString()));
}
