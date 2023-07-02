package com.stereo528.grimace_milkshakes.Util;

import com.stereo528.grimace_milkshakes.Blocks.Entities.MixerBlockEntity;
import com.stereo528.grimace_milkshakes.Blocks.MixerBlock;
import com.stereo528.grimace_milkshakes.Effects.GrimaceEffect;
import com.stereo528.grimace_milkshakes.Screens.MixerScreenHandler;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import org.quiltmc.qsl.block.extensions.api.QuiltBlockSettings;
import org.quiltmc.qsl.item.setting.api.QuiltItemSettings;


import static com.stereo528.grimace_milkshakes.GrimaceMilkshakes.*;

public class Registar {

	public static final MobEffect GRIMACE = registerEffect("grimace", new GrimaceEffect(MobEffectCategory.HARMFUL, 0x543e62));
	public static final Item GRIMACE_SHAKE = registerItem("grimace_shake", new Item(new QuiltItemSettings().stacksTo(1).food(
		new FoodProperties.Builder().nutrition(2).saturationMod(1).alwaysEat().effect(new MobEffectInstance(GRIMACE, 100, 0), 1.0F).build()
	)));

	public static final Item VANILLA_SHAKE = registerItem("vanilla_shake", new Item(new QuiltItemSettings().stacksTo(1).food(
		new FoodProperties.Builder().nutrition(3).saturationMod(2).build()
	)));
	public static final Item CHOCOLATE_SHAKE = registerItem("chocolate_shake", new Item(new QuiltItemSettings().stacksTo(1).food(
		new FoodProperties.Builder().nutrition(3).saturationMod(2).build()
	)));
	public static final Item STRAWBERRY_SHAKE = registerItem("strawberry_shake", new Item(new QuiltItemSettings().stacksTo(1).food(
		new FoodProperties.Builder().nutrition(3).saturationMod(2).build()
	)));


	public static Block MIXER_BLOCK = registerBlock("mixer_block", new MixerBlock(QuiltBlockSettings.of().strength(4.0f).noOcclusion()));
	public static BlockEntityType<MixerBlockEntity> MIXER_BLOCK_ENTITY;

	public static MenuType<MixerScreenHandler> MIXER_SCREEN_HANDLER = new MenuType<>(MixerScreenHandler::new, FeatureFlagSet.of());

	public static void init() {
	}

	public static MobEffect registerEffect(String name, MobEffect mobEffect) {
		return Registry.register(BuiltInRegistries.MOB_EFFECT, new ResourceLocation(MODID, name), mobEffect);
	}

	public static Item registerItem(String name, Item item) {
		ItemGroupEvents.modifyEntriesEvent(ITEM_GROUP).register(content -> {
			content.accept(item);
		});
		return Registry.register(BuiltInRegistries.ITEM, new ResourceLocation(MODID, name), item);
	}

	public static Block registerBlock(String name, Block block) {
		Item item = Registry.register(BuiltInRegistries.ITEM, new ResourceLocation(MODID, name), new BlockItem(block, new QuiltItemSettings().stacksTo(4)));
		ItemGroupEvents.modifyEntriesEvent(ITEM_GROUP).register(content -> {
			content.accept(item);
		});
		return Registry.register(BuiltInRegistries.BLOCK, new ResourceLocation(MODID, name), block);
	}
}

