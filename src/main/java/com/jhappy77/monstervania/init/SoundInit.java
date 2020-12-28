package com.jhappy77.monstervania.init;

import com.jhappy77.monstervania.Monstervania;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class SoundInit {
    public static final DeferredRegister<SoundEvent> SOUNDS = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, Monstervania.MOD_ID);

    public static final RegistryObject<SoundEvent> VAMPIRE_AMBIENT = SOUNDS.register("entity.vampire_entity.ambient",
            ()-> new SoundEvent(new ResourceLocation(Monstervania.MOD_ID, "entity.vampire_entity.ambient")));

    public static final RegistryObject<SoundEvent> ENTITY_VAMPIRE_HURT = SOUNDS.register("entity.vampire_entity.hurt",
            ()-> new SoundEvent(new ResourceLocation(Monstervania.MOD_ID, "entity.vampire_entity.hurt")));

    public static final RegistryObject<SoundEvent> ENTITY_VAMPIRE_DEATH = registerSound("entity.vampire_entity.death");


    public static RegistryObject<SoundEvent> registerSound(String path){
        return SOUNDS.register(path, ()-> new SoundEvent(new ResourceLocation(Monstervania.MOD_ID, path)));
    }



}
