package com.lndydx.beyondnetherite.item;

import it.unimi.dsi.fastutil.objects.Object2IntMap;

import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.CraftingBookCategory;
import net.minecraft.world.item.crafting.CraftingInput;
import net.minecraft.world.item.crafting.CustomRecipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.ItemEnchantments;
import net.minecraft.world.level.Level;

public class WingedChestplateRecipe extends CustomRecipe {

    public WingedChestplateRecipe() {
        super();
    }

    @Override
    public CraftingBookCategory category() {
        return CraftingBookCategory.MISC;
    }

    @Override
    public boolean matches(CraftingInput input, Level level) {
        if (input.items().size() != 3) {
            return false;
        }

        ItemStack elytra = null;
        ItemStack chest = null;
        ItemStack additive = null;

        for (ItemStack stack : input.items()) {
            if (stack.is(Items.ELYTRA)) {
                if (elytra != null) return false;
                elytra = stack;
            } else if (stack.is(Items.NETHERITE_CHESTPLATE) || stack.is(ModItems.OBSIDIAN_CHESTPLATE)) {
                if (chest != null) return false;
                chest = stack;
            } else if (stack.is(Items.NETHERITE_INGOT) || stack.is(ModItems.OBSIDIAN_ALLOY)) {
                if (additive != null) return false;
                additive = stack;
            } else {
                return false;
            }
        }

        if (elytra == null || chest == null || additive == null) {
            return false;
        }

        if (elytra.isDamaged() || chest.isDamaged()) {
            return false;
        }

        boolean netherite = chest.is(Items.NETHERITE_CHESTPLATE);
        return netherite ? additive.is(Items.NETHERITE_INGOT) : additive.is(ModItems.OBSIDIAN_ALLOY);
    }

    @Override
    public ItemStack assemble(CraftingInput input) {
        ItemStack chest = null;
        ItemStack elytra = null;

        for (ItemStack stack : input.items()) {
            if (stack.is(Items.ELYTRA)) {
                elytra = stack;
            } else if (stack.is(Items.NETHERITE_CHESTPLATE) || stack.is(ModItems.OBSIDIAN_CHESTPLATE)) {
                chest = stack;
            }
        }

        boolean netherite = chest != null && chest.is(Items.NETHERITE_CHESTPLATE);
        ItemStack result = new ItemStack(netherite ? ModItems.WINGED_NETHERITE_CHESTPLATE : ModItems.WINGED_OBSIDIAN_CHESTPLATE);

        if (chest != null) {
            mergeEnchantments(chest, result);
        }
        if (elytra != null) {
            mergeEnchantments(elytra, result);
        }

        return result;
    }

    private static void mergeEnchantments(ItemStack source, ItemStack result) {
        ItemEnchantments sourceEnchants = source.getOrDefault(DataComponents.ENCHANTMENTS, ItemEnchantments.EMPTY);
        for (Object2IntMap.Entry<Holder<Enchantment>> entry : sourceEnchants.entrySet()) {
            Holder<Enchantment> enchantment = entry.getKey();
            int level = entry.getIntValue();
            ItemEnchantments current = result.getOrDefault(DataComponents.ENCHANTMENTS, ItemEnchantments.EMPTY);
            if (level > current.getLevel(enchantment)) {
                result.enchant(enchantment, level);
            }
        }
    }

    @Override
    public RecipeSerializer<? extends CustomRecipe> getSerializer() {
        return ModRecipes.WINGED_CHESTPLATE;
    }
}