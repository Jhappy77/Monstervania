package com.jhappy77.monstervania.items;

import com.jhappy77.monstervania.Monstervania;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;

public class ItemBase extends Item {

    public ItemBase(){
        super(new Item.Properties().group(Monstervania.TAB));
    }
}
