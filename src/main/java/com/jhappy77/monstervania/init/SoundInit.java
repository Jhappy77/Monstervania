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



}
