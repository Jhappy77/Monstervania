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

    @Override
    public void tick() {
        super.tick();
        // Attack Timer
        if(super.func_234041_j_() > 10){
            Monstervania.LOGGER.debug("Rats attack timer was active!");
            ratEntity.setAttacking(true);
        }else{
            ratEntity.setAttacking(false);
        }
    }
}
