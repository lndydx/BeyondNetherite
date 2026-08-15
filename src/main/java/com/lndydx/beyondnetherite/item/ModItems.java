package com.lndydx.beyondnetherite.item;

import net.fabricmc.fabric.api.creativetab.v1.CreativeModeTabEvents;
import net.minecraft.core.Registry;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.HoeItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.minecraft.world.item.equipment.ArmorType;
import net.minecraft.util.Unit;

import com.lndydx.beyondnetherite.BeyondNetherite;

public class ModItems {
    public static Item OBSIDIAN_SHARD;
    public static Item OBSIDIAN_ALLOY;
    public static Item OBSIDIAN_SMITHING_TEMPLATE;
    public static Item OBSIDIAN_SWORD;
    public static Item OBSIDIAN_AXE;
    public static Item OBSIDIAN_PICKAXE;
    public static Item OBSIDIAN_SHOVEL;
    public static Item OBSIDIAN_HOE;
    public static Item OBSIDIAN_SPEAR;
    public static Item OBSIDIAN_HELMET;
    public static Item OBSIDIAN_CHESTPLATE;
    public static Item OBSIDIAN_LEGGINGS;
    public static Item OBSIDIAN_BOOTS;
    public static Item OBSIDIAN_ARROW;
    public static Item WINGED_NETHERITE_CHESTPLATE;
    public static Item WINGED_OBSIDIAN_CHESTPLATE;

    private static final Identifier BASE_ATTACK_DAMAGE_ID = Identifier.withDefaultNamespace("base_attack_damage");
    private static final Identifier BASE_ATTACK_SPEED_ID = Identifier.withDefaultNamespace("base_attack_speed");
    private static final Identifier OBSIDIAN_HASTE_ID = Identifier.fromNamespaceAndPath(BeyondNetherite.MOD_ID, "obsidian_haste");

    private static Item register(ResourceKey<Item> key, Item item) {
        return Registry.register(BuiltInRegistries.ITEM, key, item);
    }

    private static Item.Properties toolProperties(float attackDamage, float attackSpeed, net.minecraft.tags.TagKey<net.minecraft.world.level.block.Block> mineableTag, boolean hasHaste, ResourceKey<Item> key) {
        Item.Properties props = new Item.Properties().setId(key).stacksTo(1).fireResistant();
        props = ObsidianToolMaterial.INSTANCE.applyToolProperties(props, mineableTag, attackDamage, attackSpeed, 0.0F);

        if (hasHaste) {
            ItemAttributeModifiers mods = ItemAttributeModifiers.builder()
                    .add(Attributes.ATTACK_DAMAGE, new AttributeModifier(BASE_ATTACK_DAMAGE_ID, attackDamage + ObsidianToolMaterial.INSTANCE.attackDamageBonus(), AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND)
                    .add(Attributes.ATTACK_SPEED, new AttributeModifier(BASE_ATTACK_SPEED_ID, attackSpeed, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND)
                    .add(Attributes.BLOCK_BREAK_SPEED, new AttributeModifier(OBSIDIAN_HASTE_ID, 0.25D, AttributeModifier.Operation.ADD_MULTIPLIED_BASE), EquipmentSlotGroup.MAINHAND)
                    .build();
            props = props.component(DataComponents.ATTRIBUTE_MODIFIERS, mods);
        }

        return props;
    }

    private static Item.Properties swordProperties(float attackDamage, float attackSpeed, ResourceKey<Item> key) {
        Item.Properties props = new Item.Properties().setId(key).stacksTo(1).fireResistant();
        props = ObsidianToolMaterial.INSTANCE.applySwordProperties(props, attackDamage, attackSpeed);
        return props;
    }

    private static Item.Properties hoeProperties(ResourceKey<Item> key) {
        return new Item.Properties().setId(key).stacksTo(1).fireResistant();
    }

    private static Item.Properties spearProperties(ResourceKey<Item> key) {
        Item.Properties props = new Item.Properties().setId(key).stacksTo(1).fireResistant();
        props = props.spear(
                ObsidianToolMaterial.INSTANCE, 1.0F, 1.35F, 0.0F, 10.0F, 0.2F, 10.0F, 0.2F, 10.0F, 0.0F
        );
        return props;
    }

    private static Item.Properties basicProperties(ResourceKey<Item> key) {
        return new Item.Properties().setId(key).fireResistant();
    }

    private static Item.Properties armorProperties(ResourceKey<Item> key, int durability, ArmorType type) {
        return new Item.Properties()
                .setId(key)
                .stacksTo(1)
                .humanoidArmor(ModArmorMaterials.OBSIDIAN, type)
                .durability(durability)
                .fireResistant()
                .enchantable(15);
    }

    private static Item.Properties wingedProperties(ResourceKey<Item> key, int durability) {
        return new Item.Properties()
                .setId(key)
                .stacksTo(1)
                .humanoidArmor(ModArmorMaterials.OBSIDIAN, ArmorType.CHESTPLATE)
                .component(DataComponents.GLIDER, Unit.INSTANCE)
                .durability(durability)
                .fireResistant()
                .enchantable(15);
    }

    public static void initialize() {
        OBSIDIAN_SHARD = register(ModItemIds.OBSIDIAN_SHARD, new Item(basicProperties(ModItemIds.OBSIDIAN_SHARD)));
        OBSIDIAN_ALLOY = register(ModItemIds.OBSIDIAN_ALLOY, new Item(basicProperties(ModItemIds.OBSIDIAN_ALLOY)));
        OBSIDIAN_SMITHING_TEMPLATE = register(ModItemIds.OBSIDIAN_SMITHING_TEMPLATE, new Item(basicProperties(ModItemIds.OBSIDIAN_SMITHING_TEMPLATE)));

        OBSIDIAN_SWORD = register(ModItemIds.OBSIDIAN_SWORD, new Item(swordProperties(3.0F, -2.4F, ModItemIds.OBSIDIAN_SWORD)));
        OBSIDIAN_AXE = register(ModItemIds.OBSIDIAN_AXE, new Item(toolProperties(5.0F, -3.0F, BlockTags.MINEABLE_WITH_AXE, true, ModItemIds.OBSIDIAN_AXE)));
        OBSIDIAN_PICKAXE = register(ModItemIds.OBSIDIAN_PICKAXE, new Item(toolProperties(1.0F, -2.8F, BlockTags.MINEABLE_WITH_PICKAXE, true, ModItemIds.OBSIDIAN_PICKAXE)));
        OBSIDIAN_SHOVEL = register(ModItemIds.OBSIDIAN_SHOVEL, new Item(toolProperties(1.0F, -3.0F, BlockTags.MINEABLE_WITH_SHOVEL, true, ModItemIds.OBSIDIAN_SHOVEL)));
        OBSIDIAN_HOE = register(ModItemIds.OBSIDIAN_HOE, new HoeItem(ObsidianToolMaterial.INSTANCE, -5.0F, 0.0F, hoeProperties(ModItemIds.OBSIDIAN_HOE)));
        OBSIDIAN_SPEAR = register(ModItemIds.OBSIDIAN_SPEAR, new Item(new Item.Properties().setId(ModItemIds.OBSIDIAN_SPEAR).stacksTo(1).spear(ObsidianToolMaterial.INSTANCE, 1.25F, 1.35F, 0.75F, 3.25F, 14.0F, 3.0F, 5.1F, 10.0F, 4.6F).fireResistant()));

        OBSIDIAN_HELMET = register(ModItemIds.OBSIDIAN_HELMET, new Item(armorProperties(ModItemIds.OBSIDIAN_HELMET, 509, ArmorType.HELMET)));
        OBSIDIAN_CHESTPLATE = register(ModItemIds.OBSIDIAN_CHESTPLATE, new Item(armorProperties(ModItemIds.OBSIDIAN_CHESTPLATE, 740, ArmorType.CHESTPLATE)));
        OBSIDIAN_LEGGINGS = register(ModItemIds.OBSIDIAN_LEGGINGS, new Item(armorProperties(ModItemIds.OBSIDIAN_LEGGINGS, 694, ArmorType.LEGGINGS)));
        OBSIDIAN_BOOTS = register(ModItemIds.OBSIDIAN_BOOTS, new Item(armorProperties(ModItemIds.OBSIDIAN_BOOTS, 601, ArmorType.BOOTS)));
        OBSIDIAN_ARROW = register(ModItemIds.OBSIDIAN_ARROW, new ObsidianArrowItem(new Item.Properties().setId(ModItemIds.OBSIDIAN_ARROW).fireResistant()));
        WINGED_NETHERITE_CHESTPLATE = register(ModItemIds.WINGED_NETHERITE_CHESTPLATE, new Item(wingedProperties(ModItemIds.WINGED_NETHERITE_CHESTPLATE, 548)));
        WINGED_OBSIDIAN_CHESTPLATE = register(ModItemIds.WINGED_OBSIDIAN_CHESTPLATE, new Item(wingedProperties(ModItemIds.WINGED_OBSIDIAN_CHESTPLATE, 652)));

        CreativeModeTabEvents.modifyOutputEvent(CreativeModeTabs.INGREDIENTS)
                .register((entries) -> {
                    entries.accept(OBSIDIAN_SHARD);
                    entries.accept(OBSIDIAN_ALLOY);
                    entries.accept(OBSIDIAN_SMITHING_TEMPLATE);
                });

        CreativeModeTabEvents.modifyOutputEvent(CreativeModeTabs.TOOLS_AND_UTILITIES)
                .register((entries) -> {
                    entries.accept(OBSIDIAN_PICKAXE);
                    entries.accept(OBSIDIAN_SHOVEL);
                    entries.accept(OBSIDIAN_HOE);
                });

        CreativeModeTabEvents.modifyOutputEvent(CreativeModeTabs.COMBAT)
                .register((entries) -> {
                    entries.accept(OBSIDIAN_SWORD);
                    entries.accept(OBSIDIAN_AXE);
                    entries.accept(OBSIDIAN_SPEAR);
                    entries.accept(OBSIDIAN_ARROW);
                    entries.accept(OBSIDIAN_HELMET);
                    entries.accept(OBSIDIAN_CHESTPLATE);
                    entries.accept(OBSIDIAN_LEGGINGS);
                    entries.accept(OBSIDIAN_BOOTS);
                    entries.accept(WINGED_NETHERITE_CHESTPLATE);
                    entries.accept(WINGED_OBSIDIAN_CHESTPLATE);
                });
    }
}