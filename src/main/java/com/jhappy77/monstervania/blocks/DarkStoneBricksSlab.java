package com.jhappy77.monstervania.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.SlabBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraftforge.common.ToolType;

public class DarkStoneBricksSlab extends SlabBlock {

    public DarkStoneBricksSlab() {
        super(Block.Properties.create(Material.ROCK)
                .hardnessAndResistance(1.5f, 6.0f)
                .sound(SoundType.STONE)
                .harvestLevel(0)
                .harvestTool(ToolType.PICKAXE)
                .setRequiresTool()
        );
    }
}
