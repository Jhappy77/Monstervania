package com.jhappy77.monstervania.blocks;

import com.jhappy77.monstervania.Monstervania;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;

public class BlockItemBase extends BlockItem {

    public BlockItemBase(Block block){
        super(block, new Item.Properties().group(Monstervania.TAB));
    }
}
