package com.jhappy77.monstervania.util;

public class MvDamageModifier {
    private float multiplier;
    private MvDamageModifierType type;

    public MvDamageModifier(MvDamageModifierType type, float multiplier){
        this.type = type;
        this.multiplier = multiplier;
    }

    public float getMultiplier(){
        return multiplier;
    }

    public MvDamageModifierType getType(){
        return type;
    }
}
