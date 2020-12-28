package com.jhappy77.monstervania.util;

import com.jhappy77.monstervania.Monstervania;
import com.jhappy77.monstervania.blocks.BlockItemBase;
import com.jhappy77.monstervania.blocks.DarkStoneBrickStairs;
import com.jhappy77.monstervania.blocks.DarkStoneBricksBlock;
import com.jhappy77.monstervania.blocks.DarkStoneBricksSlab;
import com.jhappy77.monstervania.init.ModEntityTypes;
import com.jhappy77.monstervania.items.*;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class RegistryHandler {

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Monstervania.MOD_ID);
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, Monstervania.MOD_ID);

    public static RegistryObject<ModSpawnEggItem> registerSpawnEgg(String name, RegistryObject type, int primaryHex, int secondaryHex){
        return ITEMS.register(name, ()-> new ModSpawnEggItem(type, primaryHex, secondaryHex, new Item.Properties().group(Monstervania.TAB)));
    }

    public static void init(){
        ITEMS.register(FMLJavaModLoadingContext.get().getModEventBus());
        BLOCKS.register(FMLJavaModLoadingContext.get().getModEventBus());
    }

    // Items
    public static final RegistryObject<Item> VAMPIRE_FANG = ITEMS.register("vampire_fang", ItemBase::new);
    public static final RegistryObject<Item> WRAPPINGS = ITEMS.register("wrappings", ItemBase::new);
    public static final RegistryObject<BandagesItem> BANDAGES = ITEMS.register("bandages", BandagesItem::new);
    public static final RegistryObject<Item> LIGHTNING_WAND = ITEMS.register("lightning_wand", LightningWandItem::new);
    public static final RegistryObject<Item> VOLT_DUST = ITEMS.register("volt_dust", ItemBase::new);
    public static final RegistryObject<Item> VAMPIRE_DUST = ITEMS.register("vampire_dust", ItemBase::new);
    public static final RegistryObject<Item> RAT_TAIL = ITEMS.register("rat_tail", RatTailItem::new);

    public static final RegistryObject<ModSpawnEggItem> VAMPIRE_VOODOO_DOLL = registerSpawnEgg("vampire_voodoo_doll", ModEntityTypes.VAMPIRE, 0x478E88, 0x303030);
    public static final RegistryObject<ModSpawnEggItem> SUMMON_FRANKENGOLEM = registerSpawnEgg("summon_frankengolem",ModEntityTypes.FRANKENGOLEM, 0x626D39, 0x0A1500);
    public static final RegistryObject<ModSpawnEggItem> SUMMON_FROST_SPIDER = registerSpawnEgg("summon_frost_spider", ModEntityTypes.FROST_SPIDER, 0xffffff, 0x00C6B9);
    public static final RegistryObject<ModSpawnEggItem> SUMMON_MUMMIFIED_CREEPER = registerSpawnEgg("summon_mummified_creeper", ModEntityTypes.MUMMIFIED_CREEPER, 0xE5E1D0, 0x514E42);
    public static final RegistryObject<ModSpawnEggItem> SUMMON_RAT = registerSpawnEgg("summon_rat", ModEntityTypes.RAT, 0x2C3039, 0xC39990);


    // Blocks
    public static final RegistryObject<Block> DARK_STONE_BRICKS_BLOCK = BLOCKS.register("dark_stone_bricks_block", DarkStoneBricksBlock::new);
    public static final RegistryObject<Block> DARK_STONE_BRICKS_SLAB = BLOCKS.register("dark_stone_bricks_slab", DarkStoneBricksSlab::new);
    public static final RegistryObject<Block> DARK_STONE_BRICK_STAIRS = BLOCKS.register("dark_stone_brick_stairs", DarkStoneBrickStairs::new);

    // Block Items
    public static final RegistryObject<Item> DARK_STONE_BRICKS_BLOCK_ITEM = ITEMS.register("dark_stone_bricks_block", () -> new BlockItemBase(DARK_STONE_BRICKS_BLOCK.get()));
    public static final RegistryObject<Item> DARK_STONE_BRICKS_SLAB_ITEM = ITEMS.register("dark_stone_bricks_slab", () -> new BlockItemBase(DARK_STONE_BRICKS_SLAB.get()));
    public static final RegistryObject<Item> DARK_STONE_BRICK_STAIRS_ITEM = ITEMS.register("dark_stone_brick_stairs", () -> new BlockItemBase(DARK_STONE_BRICK_STAIRS.get()));


}
