package com.jhappy77.monstervania.items;

import com.jhappy77.monstervania.Monstervania;
import net.minecraft.item.Food;
import net.minecraft.item.Item;
import net.minecraft.item.UseAction;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;

public class Bandages extends Item {

    public Bandages(){



        super(new Item.Properties()
        .group(Monstervania.TAB)
                .food(new Food.Builder()
                .hunger(0)
                .saturation(0)
                .effect(() -> new EffectInstance(Effects.INSTANT_HEALTH, 10, -1), 1)
                        .setAlwaysEdible()
                        .fastToEat()
                        .build())
                );


    }

}
