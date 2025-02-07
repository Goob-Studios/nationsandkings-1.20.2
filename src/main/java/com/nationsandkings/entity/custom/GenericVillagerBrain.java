package com.nationsandkings.entity.custom;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.mojang.datafixers.util.Pair;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.brain.*;
import net.minecraft.entity.ai.brain.task.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.intprovider.UniformIntProvider;

import java.util.Optional;


public class GenericVillagerBrain  {


    protected GenericVillagerBrain(){
    }

    protected static Brain<?> create(Brain<GenericVillagerEntity> brain) {

//        addCoreActivities(brain);
//        addIdleActivities(brain);
//        brain.setDefaultActivity(Activity.CORE);
//        brain.resetPossibleActivities();
////        brain.setCoreActivities(ImmutableSet.of(Activity.CORE));
//        NationsAndKings.LOGGER.info("Created the brain");
//        return brain;
        addCoreActivities(brain);
        addIdleActivities(brain);
        brain.setCoreActivities(ImmutableSet.of(Activity.CORE));
        brain.setDefaultActivity(Activity.IDLE);
        brain.resetPossibleActivities();
        return brain;
    }

    private static void addCoreActivities(Brain<GenericVillagerEntity> brain) {
        brain.setTaskList(Activity.CORE, 0, ImmutableList.of(
                new StayAboveWaterTask<>(0.8F),
                new UpdateLookControlTask(45, 90),
                new MoveToTargetTask(),
                new LookAroundTask(UniformIntProvider.create(0, 20), 1.0F, 1.0F, 1.0F)
        ));


        // new UpdateLookControlTask(45, 90), new MoveToTargetTask())
        //StrollTask.create(0.5f, 5, 5)
    }

    private static void addIdleActivities(Brain<GenericVillagerEntity> brain) {
//        brain.setTaskList(Activity.IDLE, 0, ImmutableList.of(StrollTask.create(0.3f)));
        brain.setTaskList(Activity.IDLE, ImmutableList.of(Pair.of(0, LookAtMobWithIntervalTask.follow(EntityType.PLAYER, 6.0F, UniformIntProvider.create(30, 60)))));
    }





        static void updateActivities(GenericVillagerEntity villager) {
//        NationsAndKings.LOGGER.info("Attempting to update the activities.");
        villager.getBrain().resetPossibleActivities(ImmutableList.of(Activity.IDLE));
    }

    public static Optional<LookTarget> getPlayerLookTarget(LivingEntity entity) {
        Optional<PlayerEntity> optional = entity.getBrain().getOptionalMemory(MemoryModuleType.NEAREST_VISIBLE_PLAYER);
        return optional.map(player -> new EntityLookTarget(player, true));
    }



}
