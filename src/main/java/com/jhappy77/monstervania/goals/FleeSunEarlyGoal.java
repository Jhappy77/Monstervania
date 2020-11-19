package com.jhappy77.monstervania.goals;

import com.jhappy77.monstervania.Monstervania;
import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.ai.goal.FleeSunGoal;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.world.World;

import java.util.EnumSet;

public class FleeSunEarlyGoal extends FleeSunGoal {

    private final World world;

    //Inherits from fleesun goal, but tells the creature to flee earlier than normal
    public FleeSunEarlyGoal(CreatureEntity theCreatureIn, double movementSpeedIn) {
        super(theCreatureIn, movementSpeedIn);
        this.world = theCreatureIn.world;
    }

    // Decides if the goal should execute, based on the time of day and if the vampire is out in the open
    public boolean shouldExecute() {
        if (!(this.world.getDayTime() > 23000 || this.world.getDayTime() < 13000 )) {
            return false;
        } else if (!this.world.canSeeSky(this.creature.getPosition())) {
            return false;
        } else {
            boolean f1 = this.isPossibleShelter();
            for(int i=0; i<10;i++){
                boolean f2 = isPossibleShelter();
                if(f2)
                    return true;
            }
            //Monstervania.LOGGER.debug("Found no shelter " + f1);
            return !this.creature.getItemStackFromSlot(EquipmentSlotType.HEAD).isEmpty() ? false : this.isPossibleShelter();
        }
    }

}