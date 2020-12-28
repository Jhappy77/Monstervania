package com.jhappy77.monstervania.util;

import java.util.List;

public interface MvEntitySpawnable {
    public List<MvSpawnCondition<MvMobSpawnInfo>> getSpawnConditions();
}
