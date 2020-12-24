package com.jhappy77.monstervania.init;

import com.jhappy77.monstervania.Monstervania;
import com.jhappy77.monstervania.entities.*;
import com.jhappy77.monstervania.util.MvMobSpawnInfo;
import com.jhappy77.monstervania.util.MvSpawnCondition;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.ArrayList;
import java.util.HashMap;
import java.lang.Class;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

public class ModEntityTypes {

    public static DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.ENTITIES, Monstervania.MOD_ID);

    public static Map<String, List<MvSpawnCondition<MvMobSpawnInfo>>> spawnables = new HashMap<>();
    public static Map<String, RegistryObject> registrymap = new HashMap<String, RegistryObject>();

    public static final RegistryObject<EntityType<VampireEntity>> VAMPIRE = new
            EntityRegistrar("vampire").setBuilder(
                    EntityType.Builder.create(VampireEntity::new, EntityClassification.MONSTER).size(0.6f, 1.95f)
            ).addSpawnConditions(VampireEntity.spawnConditions()).register();

    public static final RegistryObject<EntityType<FrankengolemEntity>> FRANKENGOLEM = new
            EntityRegistrar("frankengolem").setBuilder(
                EntityType.Builder.create(FrankengolemEntity::new, EntityClassification.MONSTER).size(1.4f, 2.7f)
            ).addSpawnConditions(FrankengolemEntity.spawnConditions()).register();
    public static final RegistryObject<EntityType<FrostSpiderEntity>> FROST_SPIDER = new
            EntityRegistrar("frost_spider").setBuilder(
                    EntityType.Builder.create(FrostSpiderEntity::new, EntityClassification.MONSTER).size(1.4f, 0.9f))
            .addSpawnConditions(FrostSpiderEntity.spawnConditions()).register();
    public static final RegistryObject<EntityType<MummifiedCreeperEntity>>  MUMMIFIED_CREEPER = new
            EntityRegistrar("mummified_creeper").setBuilder(
                EntityType.Builder.create(MummifiedCreeperEntity::new, EntityClassification.MONSTER).size(0.6f, 1.7f)
            ).addSpawnConditions(MummifiedCreeperEntity.spawnConditions()).register();
    public static final RegistryObject<EntityType<RatEntity>> RAT = new
            EntityRegistrar("rat").setBuilder(
                    EntityType.Builder.create(RatEntity::new, EntityClassification.MONSTER).size(1.25f, 1.1f)
            ).addSpawnConditions(RatEntity.spawnConditions()).register();

    static {
        fillRegistryMap();
    }

    public static void fillRegistryMap() {
        registrymap.put("vampire", VAMPIRE);
        registrymap.put("frankengolem", FRANKENGOLEM);
        registrymap.put("frost_spider", FROST_SPIDER);
        registrymap.put("mummified_creeper", MUMMIFIED_CREEPER);
        registrymap.put("rat", RAT);
    }

    // F4 to see:
    // EntityType
    //Entity types
//    public static final RegistryObject<EntityType<VampireEntity>> VAMPIRE = ENTITY_TYPES.register("vampire",
//            ()-> EntityType.Builder.create(VampireEntity::new, EntityClassification.MONSTER)
//    .size(0.6f, 1.95f)
//    .build(new ResourceLocation(Monstervania.MOD_ID, "vampire").toString()));
//
//    public static final RegistryObject<EntityType<FrankengolemEntity>> FRANKENGOLEM = ENTITY_TYPES.register("frankengolem",
//            ()-> EntityType.Builder.create(FrankengolemEntity::new, EntityClassification.MONSTER)
//                    .size(1.4f, 2.7f)
//                    .build(new ResourceLocation(Monstervania.MOD_ID, "frankengolem").toString()));
//
//    public static final RegistryObject<EntityType<FrostSpiderEntity>> FROST_SPIDER = ENTITY_TYPES.register("frost_spider",
//            ()-> EntityType.Builder.create(FrostSpiderEntity::new, EntityClassification.MONSTER)
//                    .size(1.4f, 0.9f)
//                    .build(new ResourceLocation(Monstervania.MOD_ID, "frost_spider").toString()));
//
//    public static final RegistryObject<EntityType<MummifiedCreeperEntity>> MUMMIFIED_CREEPER = ENTITY_TYPES.register("mummified_creeper",
//            ()-> EntityType.Builder.create(MummifiedCreeperEntity::new, EntityClassification.MONSTER)
//                    .size(0.6f, 1.7f)
//                    .build(new ResourceLocation(Monstervania.MOD_ID, "mummified_creeper").toString()));
//
//    public static final RegistryObject<EntityType<RatEntity>> RAT = ENTITY_TYPES.register("rat",
//            ()-> EntityType.Builder.create(RatEntity::new, EntityClassification.MONSTER)
//                    .size(1.25f, 1.1f)
//                    .build(new ResourceLocation(Monstervania.MOD_ID, "rat").toString()));
//
//    public static final Entity[] EntityList = {, };

    public static class EntityRegistrar<T extends Entity> {
        private String name;
        private Class<T> theClass;
        private EntityType.Builder builder;
        private String locationName;

        public EntityRegistrar (String name, EntityType.IFactory<T> factoryIn, EntityClassification classification){
            this.name = name;
            this.builder = EntityType.Builder.create(factoryIn, classification);
            this.locationName = new ResourceLocation(Monstervania.MOD_ID, name).toString();
        }

        public EntityRegistrar (String name){
            this.name = name;
            this.locationName = new ResourceLocation(Monstervania.MOD_ID, name).toString();
            //this.builder = EntityType.Builder.create(factoryIn, EntityClassification.MONSTER);
        }

        // EntityType.IFactory<T> factoryIn

        public EntityRegistrar setBuilder(EntityType.Builder builder){
            this.builder = builder;
            return this;
        }

//        public EntityRegistrar useClass(Class<T> c){
//            theClass = c;
//            if(MvSpawnable.class.isAssignableFrom(c)){
//
//            }
//            return this;
//        }

//        public EntityRegistrar useSpawnPattern(MvSpawnable spawnable){
//            //spawnables.put(name, spawnable.spawnCondition());
//            return this;
//        }

        public EntityRegistrar addSpawnConditions(List<MvSpawnCondition<MvMobSpawnInfo>> spawnConditionList){
            if(spawnables.containsKey(name)){
                spawnables.get(name).addAll(spawnConditionList);
            }else{
                spawnables.put(name, spawnConditionList);
            }
            return this;
        }

        public RegistryObject<EntityType<T>> register(){

            return ENTITY_TYPES.register(name, ()->builder
                    .build(locationName));
        }


    }

}