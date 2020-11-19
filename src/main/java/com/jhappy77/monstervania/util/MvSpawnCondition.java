package com.jhappy77.monstervania.util;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.event.world.BiomeLoadingEvent;

import java.util.ArrayList;
import java.util.List;

public class MvSpawnCondition {

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


    private int weight;
    private int mincount;
    private int maxcount;
    public MvSpawnCondition(int weight, int mincount, int maxcount){
        this.weight = weight;
        this.mincount = mincount;
        this.maxcount = maxcount;
        biomeSpawnClauses = new ArrayList<>();
        worldSpawnClauses = new ArrayList<>();
    }

    public int getWeight(){
        return weight;
    }
    public int getMincount(){
        return mincount;
    }
    public int getMaxcount(){
        return maxcount;
    }

    private List<BiomeSpawnClause> biomeSpawnClauses;
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

    /**
     * A utility class which provides what WorldSpawnClauses must be promised in order to perform their checks
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
