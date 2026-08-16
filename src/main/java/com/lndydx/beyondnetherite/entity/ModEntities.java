package com.lndydx.beyondnetherite.entity;

import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;

import com.lndydx.beyondnetherite.BeyondNetherite;

public class ModEntities {
    private static final ResourceKey<EntityType<?>> OBSIDIAN_ARROW_KEY = ResourceKey.create(
            Registries.ENTITY_TYPE,
            Identifier.fromNamespaceAndPath(BeyondNetherite.MOD_ID, "obsidian_arrow"));

    private static final ResourceKey<EntityType<?>> OBSIDIAN_GOLEM_KEY = ResourceKey.create(
            Registries.ENTITY_TYPE,
            Identifier.fromNamespaceAndPath(BeyondNetherite.MOD_ID, "obsidian_golem"));

    public static final EntityType<ObsidianArrow> OBSIDIAN_ARROW = Registry.register(
            BuiltInRegistries.ENTITY_TYPE,
            OBSIDIAN_ARROW_KEY,
            EntityType.Builder.<ObsidianArrow>of(ObsidianArrow::new, MobCategory.MISC)
                    .sized(0.5F, 0.5F)
                    .clientTrackingRange(4)
                    .updateInterval(20)
                    .build(OBSIDIAN_ARROW_KEY));

    @SuppressWarnings("unchecked")
    public static final EntityType<ObsidianGolem> OBSIDIAN_GOLEM = Registry.register(
            BuiltInRegistries.ENTITY_TYPE,
            OBSIDIAN_GOLEM_KEY,
            (EntityType<ObsidianGolem>) (EntityType<?>) EntityType.Builder.of(ObsidianGolem::new, MobCategory.MISC)
                    .sized(1.4F, 2.7F)
                    .clientTrackingRange(10)
                    .fireImmune()
                    .build(OBSIDIAN_GOLEM_KEY));

    public static void initialize() {
        FabricDefaultAttributeRegistry.register(OBSIDIAN_GOLEM, ObsidianGolem.createAttributes());
    }
}