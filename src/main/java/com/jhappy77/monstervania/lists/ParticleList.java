package com.jhappy77.monstervania.lists;

import com.jhappy77.monstervania.Monstervania;
import net.minecraft.particles.BasicParticleType;
import net.minecraft.particles.ParticleType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ParticleList {
    public static final DeferredRegister<ParticleType<?>> PARTICLES = DeferredRegister.create(ForgeRegistries.PARTICLE_TYPES,Monstervania.MOD_ID);

    public static final RegistryObject<BasicParticleType> ELECTRIC_PARTICLE = PARTICLES.register("electric_particle", () -> new BasicParticleType(true));
}
