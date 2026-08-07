package com.lndydx.beyondnetherite.loot;

import net.fabricmc.fabric.api.loot.v3.LootTableEvents;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceCondition;

import com.lndydx.beyondnetherite.item.ModItems;

public class ModLoot {
    public static void initialize() {
        LootTableEvents.MODIFY.register((key, table, context, registries) -> {
            // Nether Fortress - 15%
            if (key.equals(BuiltInLootTables.NETHER_BRIDGE)) {
                table.withPool(LootPool.lootPool()
                        .add(LootItem.lootTableItem(ModItems.OBSIDIAN_SMITHING_TEMPLATE)
                                .when(LootItemRandomChanceCondition.randomChance(0.15F))));
            }

            // Bastion - 20%
            if (key.equals(BuiltInLootTables.BASTION_BRIDGE)
                    || key.equals(BuiltInLootTables.BASTION_OTHER)
                    || key.equals(BuiltInLootTables.BASTION_TREASURE)
                    || key.equals(BuiltInLootTables.BASTION_HOGLIN_STABLE)) {
                table.withPool(LootPool.lootPool()
                        .add(LootItem.lootTableItem(ModItems.OBSIDIAN_SMITHING_TEMPLATE)
                                .when(LootItemRandomChanceCondition.randomChance(0.20F))));
            }
        });
    }
}