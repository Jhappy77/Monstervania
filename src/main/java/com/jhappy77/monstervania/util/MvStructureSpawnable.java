package com.jhappy77.monstervania.util;

import java.util.List;

public interface MvStructureSpawnable {
    public List<MvSpawnCondition<MvStructureSpawnInfo>> getSpawnConditions();
}
