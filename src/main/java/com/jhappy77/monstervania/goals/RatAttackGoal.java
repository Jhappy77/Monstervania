package com.jhappy77.monstervania.goals;

import com.jhappy77.monstervania.Monstervania;
import com.jhappy77.monstervania.entities.RatEntity;
import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.ai.goal.MeleeAttackGoal;

public class RatAttackGoal extends MeleeAttackGoal {
    public RatAttackGoal(RatEntity creature, double speedIn, boolean useLongMemory) {
        super(creature, speedIn, useLongMemory);
        ratEntity = creature;
    }

    private RatEntity ratEntity;
    private int animationTicks = 11;
    private void resetAnimationTicks(){
        animationTicks = 11;
    }


    @Override
    public void tick() {
        super.tick();
        // Attack Timer
        if(super.func_234041_j_() > 10){
            Monstervania.LOGGER.debug("Rats attack timer was active!");
            ratEntity.setAttacking(true);
            // Start counting down til animation is done.
            resetAnimationTicks();
        }
        // If animation is still ongoing, decrement time left
        if(animationTicks > 0){
            animationTicks--;
        } else{
            // Animation has stopped. Finish animation.
            ratEntity.setAttacking(false);
        }
    }
}
