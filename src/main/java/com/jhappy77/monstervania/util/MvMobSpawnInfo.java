package com.jhappy77.monstervania.util;

public class MvMobSpawnInfo{
    private int weight;
    private int mincount;
    private int maxcount;
    public MvMobSpawnInfo(int weight, int mincount, int maxcount) {
        this.weight = weight;
        this.mincount = mincount;
        this.maxcount = maxcount;
    }

    public int getWeight(){
        return weight;
    }

    public int getMincount() {
        return mincount;
    }

    public int getMaxcount(){
        return maxcount;
    }
}
