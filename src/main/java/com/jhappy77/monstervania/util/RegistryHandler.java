package com.jhappy77.monstervania.util;

import com.jhappy77.monstervania.Monstervania;
import com.jhappy77.monstervania.blocks.BlockItemBase;
import com.jhappy77.monstervania.blocks.DarkStoneBricksBlock;
import com.jhappy77.monstervania.blocks.DarkStoneBricksSlab;
import com.jhappy77.monstervania.items.Bandages;
import com.jhappy77.monstervania.items.ItemBase;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class RegistryHandler {

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Monstervania.MOD_ID);
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, Monstervania.MOD_ID);


    public static void init(){
        ITEMS.register(FMLJavaModLoadingContext.get().getModEventBus());
        BLOCKS.register(FMLJavaModLoadingContext.get().getModEventBus());
    }

    // Items
    public static final RegistryObject<Item> VAMPIRE_FANG = ITEMS.register("vampire_fang", ItemBase::new);
    public static final RegistryObject<Item> WRAPPINGS = ITEMS.register("wrappings", ItemBase::new);
    public static final RegistryObject<Bandages> BANDAGES = ITEMS.register("bandages", Bandages::new);

    // Blocks
    public static final RegistryObject<Block> DARK_STONE_BRICKS_BLOCK = BLOCKS.register("dark_stone_bricks_block", DarkStoneBricksBlock::new);
    public static final RegistryObject<Block> DARK_STONE_BRICKS_SLAB = BLOCKS.register("dark_stone_bricks_slab", DarkStoneBricksSlab::new);

    // Block Items
    public static final RegistryObject<Item> DARK_STONE_BRICKS_BLOCK_ITEM = ITEMS.register("dark_stone_bricks_block", () -> new BlockItemBase(DARK_STONE_BRICKS_BLOCK.get()));
    public static final RegistryObject<Item> DARK_STONE_BRICKS_SLAB_ITEM = ITEMS.register("dark_stone_bricks_slab", () -> new BlockItemBase(DARK_STONE_BRICKS_SLAB.get()));



}
