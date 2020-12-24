package com.jhappy77.monstervania;

import com.jhappy77.monstervania.entities.FrankengolemEntity;
import com.jhappy77.monstervania.entities.FrostSpiderEntity;
import com.jhappy77.monstervania.entities.MummifiedCreeperEntity;
import com.jhappy77.monstervania.entities.VampireEntity;
import com.jhappy77.monstervania.init.ConfiguredStructures;
import com.jhappy77.monstervania.init.UnconfiguredStructures;
import com.jhappy77.monstervania.init.ModEntityTypes;
import com.jhappy77.monstervania.lists.ParticleList;
import com.jhappy77.monstervania.util.RegistryHandler;
import net.minecraft.entity.ai.attributes.GlobalEntityTypeAttributes;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraft.world.gen.FlatChunkGenerator;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraft.world.gen.settings.DimensionStructuresSettings;
import net.minecraft.world.gen.settings.StructureSeparationSettings;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DeferredWorkQueue;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.IForgeRegistryEntry;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

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
        modEventBus.addGenericListener(Structure.class, this::onRegisterStructures);

        RegistryHandler.init();

        ParticleList.PARTICLES.register(modEventBus);

        ModEntityTypes.ENTITY_TYPES.register(modEventBus);
        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);
        //FeaturesInit.FEATURES.register(modEventBus);

        IEventBus forgeBus = MinecraftForge.EVENT_BUS;
        forgeBus.addListener(EventPriority.NORMAL, this::addDimensionalSpacing);

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

    /**
     * This method will be called by Forge when it is time for the mod to register features.
     */
    public void onRegisterStructures(final RegistryEvent.Register<Structure<?>> event) {
        // Registers the structures.
        // If you don't do this, bad things might happen... very bad things... Spooky...
        Monstervania.LOGGER.log(Level.INFO, "Beginning to register structures...");
        UnconfiguredStructures.registerStructures(event);
        Monstervania.LOGGER.log(Level.INFO, "Registering configured structures...");
        ConfiguredStructures.registerConfiguredStructures();
        Monstervania.LOGGER.log(Level.INFO, "structures registered.");
    }

    /**
     * Will go into the world's chunkgenerator and manually add our structure spacing.
     * If the spacing is not added, the structure doesn't spawn.
     *
     * Use this for dimension blacklists for your structure.
     * (Don't forget to attempt to remove your structure too from
     *  the map if you are blacklisting that dimension! It might have
     *  your structure in it already.)
     *
     * Basically use this to mak absolutely sure the chunkgeneration
     * can or cannot spawn your structure.
     */
    public void addDimensionalSpacing(final WorldEvent.Load event) {
        if(event.getWorld() instanceof ServerWorld){
            ServerWorld serverWorld = (ServerWorld)event.getWorld();

            // Prevent spawning structures in Vanilla's superflat worlds
            if(serverWorld.getChunkProvider().getChunkGenerator() instanceof FlatChunkGenerator &&
                    serverWorld.getDimensionKey().equals(World.OVERWORLD)){
                return;
            }

            Map<Structure<?>, StructureSeparationSettings> tempMap = new HashMap<>(serverWorld.getChunkProvider().generator.func_235957_b_().func_236195_a_());
            tempMap.put(UnconfiguredStructures.VAMPIRE_TOWER, DimensionStructuresSettings.field_236191_b_.get(UnconfiguredStructures.VAMPIRE_TOWER));
            tempMap.put(UnconfiguredStructures.FROST_SPIDER_PIT, DimensionStructuresSettings.field_236191_b_.get(UnconfiguredStructures.FROST_SPIDER_PIT));
            serverWorld.getChunkProvider().generator.func_235957_b_().field_236193_d_ = tempMap;
        }
    }



    /*
     * Helper method to quickly register features, blocks, items, structures, biomes, anything that can be registered.
     */
    public static <T extends IForgeRegistryEntry<T>> T register(IForgeRegistry<T> registry, T entry, String registryKey) {
        ResourceLocation theLocation = new ResourceLocation(MOD_ID, registryKey);
        LOGGER.debug("theLocation within register function is: " +theLocation.toString());
        entry.setRegistryName(theLocation);
        registry.register(entry);
        return entry;
    }
}
