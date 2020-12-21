package com.jhappy77.monstervania.items;

import com.jhappy77.monstervania.Monstervania;
import net.minecraft.item.Food;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.UseAction;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;

public class Bandages extends Item {

    public Bandages(){
        super(new Item.Properties()
        .group(Monstervania.TAB)
                .food(new Food.Builder()
                .hunger(0)
                .saturation(0)
                .effect(() -> new EffectInstance(Effects.INSTANT_HEALTH, 1, 1), 1)
                        .setAlwaysEdible()
                        .fastToEat()
                        .build())
                );
    }

    // Changes animation to not be eating animation
    @Override
    public UseAction getUseAction(ItemStack stack){
        return UseAction.DRINK;
    }

    // Makes it so it takes 24 seconds to use bandages
    @Override
    public int getUseDuration(ItemStack stack){
        return 24;
    }

    // Makes it play the shearing noise upon applying bandages
    @Override
    public SoundEvent getEatSound() {
        return SoundEvents.BLOCK_WOOL_HIT;
    }
}
