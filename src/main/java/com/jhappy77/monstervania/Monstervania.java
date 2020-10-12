package com.jhappy77.monstervania;

import com.jhappy77.monstervania.entities.FrankengolemEntity;
import com.jhappy77.monstervania.entities.FrostSpiderEntity;
import com.jhappy77.monstervania.entities.MummifiedCreeperEntity;
import com.jhappy77.monstervania.entities.VampireEntity;
import com.jhappy77.monstervania.init.ModEntityTypes;
import com.jhappy77.monstervania.lists.ParticleList;
import com.jhappy77.monstervania.util.RegistryHandler;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.entity.ai.attributes.GlobalEntityTypeAttributes;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.DeferredWorkQueue;
import net.minecraftforge.fml.InterModComms;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.event.lifecycle.InterModProcessEvent;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.stream.Collectors;

// The value here should match an entry in the META-INF/mods.toml file
@Mod("monstervania")
public class Monstervania
{
    // Directly reference a log4j logger.
    public static final Logger LOGGER = LogManager.getLogger();
    public static final String MOD_ID = "monstervania";
    public Monstervania() {

        final IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        modEventBus.addListener(this::setup);
        modEventBus.addListener(this::doClientStuff);

        RegistryHandler.init();

        ParticleList.PARTICLES.register(modEventBus);
        ModEntityTypes.ENTITY_TYPES.register(modEventBus);
        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);
    }

    private void setup(final FMLCommonSetupEvent event)
    {
        DeferredWorkQueue.runLater(()->{
            GlobalEntityTypeAttributes.put(ModEntityTypes.VAMPIRE.get(), VampireEntity.setCustomAttributes().create());
            GlobalEntityTypeAttributes.put(ModEntityTypes.FRANKENGOLEM.get(), FrankengolemEntity.setCustomAttributes().create());
            GlobalEntityTypeAttributes.put(ModEntityTypes.FROST_SPIDER.get(), FrostSpiderEntity.setCustomAttributes().create());
            GlobalEntityTypeAttributes.put(ModEntityTypes.MUMMIFIED_CREEPER.get(), MummifiedCreeperEntity.setCustomAttributes().create());
            GlobalEntityTypeAttributes.put(ModEntityTypes.RAT.get(), MummifiedCreeperEntity.setCustomAttributes().create());
        });
    }

    private void doClientStuff(final FMLClientSetupEvent event) {
        //LOGGER.info("Got game settings {}", event.getMinecraftSupplier().get().gameSettings);
    }

    public static final ItemGroup TAB = new ItemGroup("monstervaniaTab"){
        // Sets the icon to the vampire fang

        @Override
        public ItemStack createIcon(){
            return new ItemStack(RegistryHandler.VAMPIRE_FANG.get());
        }
    };

}
