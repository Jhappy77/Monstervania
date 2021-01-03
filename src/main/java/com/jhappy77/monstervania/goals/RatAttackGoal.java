package com.jhappy77.monstervania.goals;

import com.jhappy77.monstervania.Monstervania;
import com.jhappy77.monstervania.entities.RatEntity;
import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.MeleeAttackGoal;

public class RatAttackGoal extends MeleeAttackGoal {
    public RatAttackGoal(RatEntity creature, double speedIn, boolean useLongMemory) {
        super(creature, speedIn, useLongMemory);
        ratEntity = creature;
    }

    private RatEntity ratEntity;
    private int animationTicks = 15;
    private void resetAnimationTicks(){
        animationTicks = 15;
    }

    private boolean isCloseToReach() {
        LivingEntity target = this.attacker.getAttackTarget();
        if(target != null) {
            // If the sq distance between target and attacker is less than the attack reach plus 0.5 (buffer) return true
            return ((this.getAttackReachSqr(target) + 0.6) >= this.attacker.getDistanceSq(this.attacker.getAttackTarget()));
        }
        return false;
    }

    @Override
    public void tick() {
        super.tick();
        // Attack Timer
        //if(super.func_234041_j_() > 10){
        // Attack timer is less than or equal zero (fully reset)
        if(!super.func_234040_h_() || isCloseToReach()){
            //Monstervania.LOGGER.debug("Rats attack timer was active!");
            // Start attack animation
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
