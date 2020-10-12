package com.jhappy77.monstervania.init;

import com.jhappy77.monstervania.Monstervania;
import com.jhappy77.monstervania.entities.*;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.function.Supplier;

public class ModEntityTypes {
    public static DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.ENTITIES, Monstervania.MOD_ID);

//    private static class RegisterEntityBuilder <T extends Entity & EntityType.IFactory<T>> {
//
//        private Supplier<T> supplier;
//        private Class<T> argumentClass;
//
//        public RegisterEntityBuilder(Class<T> argumentClass){
//
//            this.argumentClass = argumentClass;
//            this.width = 1.0f;
//            this.height = 1.0f;
//            this.classification = EntityClassification.MONSTER;
//        }
//        private float width;
//        private float height;
//        private EntityClassification classification;
//
//        public void setWidth(float w){
//            this.width = w;
//        }
//        public void setHeight(float h){
//            this.height = h;
//        }
//        public void setClassification(EntityClassification c){
//            this.classification = c;
//        }
//
//        public RegistryObject buildRegister(String name){
//            return registerEntity(name, width, height, classification);
//        }
//
//
//        public RegistryObject<EntityType<T>> registerEntity(String name, float width, float height, EntityClassification classification){
//
//            RegistryObject<EntityType<T>> register = ENTITY_TYPES.register(name, () -> {
//                try {
//                    return EntityType.Builder.create(argumentClass.newInstance(), classification)
//                            .size(width, height)
//                            .build(new ResourceLocation(Monstervania.MOD_ID, name).toString());
//                } catch (InstantiationException e) {
//                    e.printStackTrace();
//                } catch (IllegalAccessException e) {
//                    e.printStackTrace();
//                }
//            });
//            return register;
//        }
//    }
//
//    public static final RegistryObject<EntityType<VampireEntity>> VAMPIRE = new RegisterEntityBuilder<VampireEntity>(VampireEntity.class)
//            .buildRegister("vampire");

    // F4 to see:
    // EntityType
    //Entity types
    public static final RegistryObject<EntityType<VampireEntity>> VAMPIRE = ENTITY_TYPES.register("vampire",
            ()-> EntityType.Builder.create(VampireEntity::new, EntityClassification.MONSTER)
    .size(0.6f, 1.95f)
    .build(new ResourceLocation(Monstervania.MOD_ID, "vampire").toString()));

    public static final RegistryObject<EntityType<FrankengolemEntity>> FRANKENGOLEM = ENTITY_TYPES.register("frankengolem",
            ()-> EntityType.Builder.create(FrankengolemEntity::new, EntityClassification.MONSTER)
                    .size(1.4f, 2.7f)
                    .build(new ResourceLocation(Monstervania.MOD_ID, "frankengolem").toString()));

    public static final RegistryObject<EntityType<FrostSpiderEntity>> FROST_SPIDER = ENTITY_TYPES.register("frost_spider",
            ()-> EntityType.Builder.create(FrostSpiderEntity::new, EntityClassification.MONSTER)
                    .size(1.4f, 0.9f)
                    .build(new ResourceLocation(Monstervania.MOD_ID, "frost_spider").toString()));

    public static final RegistryObject<EntityType<MummifiedCreeperEntity>> MUMMIFIED_CREEPER = ENTITY_TYPES.register("mummified_creeper",
            ()-> EntityType.Builder.create(MummifiedCreeperEntity::new, EntityClassification.MONSTER)
                    .size(0.6f, 1.7f)
                    .build(new ResourceLocation(Monstervania.MOD_ID, "mummified_creeper").toString()));

    public static final RegistryObject<EntityType<RatEntity>> RAT = ENTITY_TYPES.register("rat",
            ()-> EntityType.Builder.create(RatEntity::new, EntityClassification.MONSTER)
                    .size(1.25f, 1.1f)
                    .build(new ResourceLocation(Monstervania.MOD_ID, "rat").toString()));
}
