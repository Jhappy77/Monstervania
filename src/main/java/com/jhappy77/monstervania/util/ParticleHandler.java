package com.jhappy77.monstervania.util;

import com.jhappy77.monstervania.Monstervania;
import com.jhappy77.monstervania.lists.ParticleList;
import com.jhappy77.monstervania.particles.ElectricParticle;
import net.minecraft.client.Minecraft;
import net.minecraftforge.client.event.ParticleFactoryRegisterEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Monstervania.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ParticleHandler {
    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void registerParticles(ParticleFactoryRegisterEvent event){
        Minecraft.getInstance().particles.registerFactory(ParticleList.ELECTRIC_PARTICLE.get(), ElectricParticle.Factory::new);
    }
}
