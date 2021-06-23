package com.sunfished.poggmod.core.init;

import com.sunfished.poggmod.PoggMod;
import com.sunfished.poggmod.common.entities.PoggEntity;

import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntitySpawnPlacementRegistry;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.gen.Heightmap;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import com.google.common.base.Predicates;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.attributes.GlobalEntityTypeAttributes;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.tags.ITag;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.gen.Heightmap;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.lang.reflect.Field;
import java.util.Random;
import java.util.function.Predicate;

@Mod.EventBusSubscriber(modid = PoggMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class EntityTypeInit {

	@SuppressWarnings("unchecked")
	public static final EntityType<PoggEntity> POGG_ENTITY = registerEntity(
			EntityType.Builder.of(PoggEntity::new, EntityClassification.CREATURE).sized(0.5F, 0.5F), "pogg_entity");

	@SuppressWarnings("rawtypes")
	private static final EntityType registerEntity(EntityType.Builder builder, String entityName) {
		ResourceLocation nameLoc = new ResourceLocation(PoggMod.MOD_ID, entityName);
		return (EntityType) builder.build(entityName).setRegistryName(nameLoc);
	}

	@SuppressWarnings("rawtypes")
	@SubscribeEvent
	public static void registerEntities(final RegistryEvent.Register<EntityType<?>> event) {
		try {
			for (Field f : EntityTypeInit.class.getDeclaredFields()) {
				Object obj = f.get(null);
				if (obj instanceof EntityType) {
					event.getRegistry().register((EntityType) obj);
				} else if (obj instanceof EntityType[]) {
					for (EntityType type : (EntityType[]) obj) {
						event.getRegistry().register(type);
					}
				}
			}
		} catch (IllegalAccessException e) {
			throw new RuntimeException(e);
		}
		initializeAttributes();
	}

	@SuppressWarnings("deprecation")
	private static void initializeAttributes() {
		GlobalEntityTypeAttributes.put(POGG_ENTITY, PoggEntity.registerAttributes().build());
	}
}
