package com.jhappy77.monstervania.util;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.event.world.BiomeLoadingEvent;

import java.util.ArrayList;
import java.util.List;

public class MvSpawnCondition <T> {

    public MvSpawnCondition(T spawnInfoType){
        this.theSpawnInfo = spawnInfoType;
        biomeSpawnClauses = new ArrayList<>();
        worldSpawnClauses = new ArrayList<>();
    }

    //theSpawnInfo will vary based on what you are spawning.
    //For mobs, it will be MvMobSpawnInfo (min, max, weight) - info that shows how often something spawns.
    private T theSpawnInfo;

    /**
     * Returns the information of what will be spawned.
     * For mobs, it will be MvMobSpawnInfo (min, max, weight) - info that allows you to
     * @return
     */
    public T getSpawnInformation(){
        return theSpawnInfo;
    }

    // The clauses which depend on biomes to spawn
    private List<BiomeSpawnClause> biomeSpawnClauses;
    // The clauses which depend on a specific location/time in the world to spawn
    private List<WorldSpawnClause> worldSpawnClauses;

    /**
     * Adds a biome spawn clause, returns the object to allow chaining
     */
    public MvSpawnCondition addBiomeSpawnClause(BiomeSpawnClause bsc){
        biomeSpawnClauses.add(bsc);
        return this;
    }

    /**
     * Adds a world spawn clause, returns the object to allow chaining
     */
    public MvSpawnCondition addWorldSpawnClause(WorldSpawnClause wsc){
        worldSpawnClauses.add(wsc);
        return this;
    }

    /**
     * Checks all the biome spawn clauses to determine if the spawned thing should be added to this BiomeLoadingEvent's spawns
     * @param bEvent Event which contains the info about the biome we are checking
     * @return True if we want it to spawn in this biome, false otherwise
     */
    public boolean evaluateBiomeSpawnClauses(BiomeLoadingEvent bEvent){
        boolean canSpawn = true;
        for (BiomeSpawnClause bClause : biomeSpawnClauses){
            if(!bClause.evaluateClause(bEvent)){
                canSpawn = false;
                break;
            }
        }
        return canSpawn;
    }

    /**
     * Checks all the world spawn clauses to determine if the spawnable should be allowed to spawn in this position
     * @return True if it can spawn in this position in the given world, false otherwise
     */
    public boolean evaluateWorldClauses(IWorld worldIn, BlockPos pos){
        boolean canSpawn = true;
        WorldClauseInfo winfo = new WorldClauseInfo(worldIn, pos);
        for (WorldSpawnClause wClause : worldSpawnClauses){
            if(!wClause.evaluateClause(winfo)){
                canSpawn = false;
                break;
            }
        }
        return canSpawn;
    }


    // Clauses which determine if the spawnable is added to a particular biome's spawn list
    private interface BiomeSpawnClause{
        public boolean evaluateClause(BiomeLoadingEvent b);
    }

    // Clauses which rely on the information of the position and the world where the spawnable will be spawned
    private interface WorldSpawnClause{
        public boolean evaluateClause(WorldClauseInfo winfo);
    }

    /**
     * A clause which checks if the category of the current biome is a match for any of the added Biome Categories
     */
    public static class BiomeCategorySpawnClause implements BiomeSpawnClause{

        // A list of biome categories which would satisfy the clause
        private List<Biome.Category> possibleBiomes;

        public BiomeCategorySpawnClause(){
            possibleBiomes = new ArrayList<>();
        }
        /**
         * Adds a biome category to the list of approved biome categories for the spawn
         * @return This object, to allow chaining
         */
        public BiomeCategorySpawnClause addCategory(Biome.Category c){
            possibleBiomes.add(c);
            return this;
        }
        @Override
        public boolean evaluateClause(BiomeLoadingEvent b){

            for(Biome.Category bc : possibleBiomes){
                if(bc == b.getCategory())
                    return true;
            }
            return false;
        }
    }

    /**
     * A clause which returns the opposite of a biome spawn clause
     */
    public static class notBiomeSpawnClause implements BiomeSpawnClause{
        private BiomeSpawnClause theClause;
        public notBiomeSpawnClause(BiomeSpawnClause c){
            theClause = c;
        }
        @Override
        public boolean evaluateClause(BiomeLoadingEvent b){
            return !theClause.evaluateClause(b);
        }
    }

    /**
     * A clause which returns the opposite of a biome spawn clause
     */
    public static class notWorldSpawnClause implements WorldSpawnClause{
        private WorldSpawnClause theClause;
        public notWorldSpawnClause(WorldSpawnClause c){
            theClause = c;
        }
        @Override
        public boolean evaluateClause(WorldClauseInfo info){
            return !theClause.evaluateClause(info);
        }
    }

    /**
     * Restrict biome by temperature
     */
    public static class BiomeTemperatureClause implements BiomeSpawnClause{
    // Vanilla biomes have temperatures between -0.5 and 2.0
    // The required temperature values are less than 0.15 for snow, and above 0.15 for rain
        private float minTemp;
        private float maxTemp;
        public BiomeTemperatureClause(){
            minTemp = -100;
            maxTemp = 100;
        }
        public BiomeTemperatureClause setMinTemp(float newMin){
            minTemp = newMin;
            return this;
        }
        public BiomeTemperatureClause setMaxTemp(float newMax){
            maxTemp = newMax;
            return this;
        }


        @Override
        public boolean evaluateClause(BiomeLoadingEvent b) {
            Biome.Climate c = b.getClimate();
            if(c.temperature > minTemp && c.temperature < maxTemp){
                return true;
            }
            return false;
        }
    }

    /**
     * A clause which restricts the altitudes at which the mob may spawn
     */
    public static class PositionAltitudeClause implements WorldSpawnClause{

        @Override
        public boolean evaluateClause(WorldClauseInfo winfo) {
            float y = winfo.getPos().getY();
            if(y>minY && y<maxY){
                return true;
            }
            return false;
        }

        private float minY;
        private float maxY;
        public PositionAltitudeClause(){
            minY = -10;
            maxY = 1000;
        }
        public PositionAltitudeClause setMinY(float newMin){
            minY = newMin;
            return this;
        }
        public PositionAltitudeClause setMaxY(float newMax){
            maxY = newMax;
            return this;
        }

    }

    // https://minecraft.gamepedia.com/Daylight_cycle
    public static class TimeClause implements WorldSpawnClause{
        @Override
        public boolean evaluateClause(WorldClauseInfo winfo) {
            long time = winfo.getWorldIn().getWorldInfo().getDayTime();
            if(time>minTime && time<maxTime){
                return true;
            }
            return false;
        }

        private long minTime;
        private long maxTime;
        public TimeClause(){
            minTime = -1;
            maxTime = 24001;
        }
        public TimeClause setMinTime(long newMin){
            minTime = newMin;
            return this;
        }
        public TimeClause setMaxTime(long newMax){
            maxTime = newMax;
            return this;
        }
    }

    /**
     * A shortcut to restrict possible biome spawns to overworld
     */
    public MvSpawnCondition restrictToOverworld(){
        addBiomeSpawnClause(
               new notBiomeSpawnClause(new BiomeCategorySpawnClause().addCategory(Biome.Category.NETHER).addCategory(Biome.Category.THEEND))
        );
        return this;
    }

    /**
     * A shortcut to restrict possible biome spawns to land biomes (exclude river and ocean)
     */
    public MvSpawnCondition restrictToLand(){
        addBiomeSpawnClause(
                new notBiomeSpawnClause(new BiomeCategorySpawnClause().addCategory(Biome.Category.RIVER).addCategory(Biome.Category.OCEAN))
        );
        return this;
    }

    /**
     * Shortcut to restrict spawning to the typical monster spawn times (13188 and 22812)
     */
    public MvSpawnCondition monsterSpawnTime(){
        addWorldSpawnClause(
                new TimeClause().setMaxTime(22812).setMinTime(13188)
        );
        return this;
    }

    /**
     * A utility class that is passed to WorldSpawnClauses so they can perform their checks
     */
    private static class WorldClauseInfo{
        public WorldClauseInfo(IWorld worldIn, BlockPos pos){
            this.worldIn = worldIn;
            this.pos = pos;
        }
        private IWorld worldIn;
        private BlockPos pos;

        public IWorld getWorldIn(){
            return this.worldIn;
        }
        public BlockPos getPos(){
            return this.pos;
        }
    }

}
