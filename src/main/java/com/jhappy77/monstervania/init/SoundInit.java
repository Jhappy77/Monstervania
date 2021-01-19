package com.jhappy77.monstervania.init;

import com.jhappy77.monstervania.Monstervania;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class SoundInit {
    public static final DeferredRegister<SoundEvent> SOUNDS = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, Monstervania.MOD_ID);



    public static final RegistryObject<SoundEvent> ENTITY_FRANKENGOLEM_DEATH = registerSound("entity.frankengolem_entity.death");
    public static final RegistryObject<SoundEvent> ENTITY_FRANKENGOLEM_HURT = registerSound("entity.frankengolem_entity.hurt");


    public static final RegistryObject<SoundEvent> ENTITY_MUMMIFIED_CREEPER_FUSE = registerSound("entity.mummified_creeper_entity.fuse");
    public static final RegistryObject<SoundEvent> ENTITY_MUMMIFIED_CREEPER_HURT = registerSound("entity.mummified_creeper_entity.hurt");

    public static final RegistryObject<SoundEvent> ENTITY_RAT_AMBIENT = registerSound("entity.rat_entity.ambient");
    public static final RegistryObject<SoundEvent> ENTITY_RAT_DEATH = registerSound("entity.rat_entity.death");
    public static final RegistryObject<SoundEvent> ENTITY_RAT_HURT = registerSound("entity.rat_entity.hurt");



    public static final RegistryObject<SoundEvent> VAMPIRE_AMBIENT = SOUNDS.register("entity.vampire_entity.ambient",
            ()-> new SoundEvent(new ResourceLocation(Monstervania.MOD_ID, "entity.vampire_entity.ambient")));

    public static final RegistryObject<SoundEvent> ENTITY_VAMPIRE_HURT = SOUNDS.register("entity.vampire_entity.hurt",
            ()-> new SoundEvent(new ResourceLocation(Monstervania.MOD_ID, "entity.vampire_entity.hurt")));

    public static final RegistryObject<SoundEvent> ENTITY_VAMPIRE_DEATH = registerSound("entity.vampire_entity.death");


    public static final RegistryObject<SoundEvent> ENVIRONMENT_ELECTRICITY = registerSound("environment.electricity.electric");

    public static RegistryObject<SoundEvent> registerSound(String path){
        return SOUNDS.register(path, ()-> new SoundEvent(new ResourceLocation(Monstervania.MOD_ID, path)));
    }



}
