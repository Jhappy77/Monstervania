package com.jhappy77.monstervania.util;

import com.jhappy77.monstervania.Monstervania;
import com.jhappy77.monstervania.client.render.*;
import com.jhappy77.monstervania.init.ModEntityTypes;
import com.jhappy77.monstervania.items.ModSpawnEggItem;
import net.minecraft.entity.EntityType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(modid = Monstervania.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientEventBusSubscriber {

    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event){
        RenderingRegistry.registerEntityRenderingHandler(ModEntityTypes.VAMPIRE.get(), VampireRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(ModEntityTypes.FRANKENGOLEM.get(), FrankengolemRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(ModEntityTypes.FROST_SPIDER.get(), FrostSpiderRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(ModEntityTypes.MUMMIFIED_CREEPER.get(), MummifiedCreeperRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(ModEntityTypes.RAT.get(), manager -> new RatRenderer(manager));
    }

    @SubscribeEvent
    public static void onRegisterEntities(final RegistryEvent.Register<EntityType<?>> event){
        ModSpawnEggItem.initSpawnEggs();
    }
}
