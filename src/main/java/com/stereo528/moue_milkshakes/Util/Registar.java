package com.stereo528.moue_milkshakes.Util;

import com.stereo528.moue_milkshakes.Effects.MoueEffect;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import org.quiltmc.qsl.item.setting.api.QuiltItemSettings;


import static com.stereo528.moue_milkshakes.MoueMilkshakes.*;

public class Registar {

	public static final MobEffect MOUE = registerEffect("grimace", new MoueEffect(MobEffectCategory.HARMFUL, 0x543e62));
	public static final Item MOUE_SHAKE = registerItem("grimace_shake", new Item(new QuiltItemSettings().stacksTo(1).food(
		new FoodProperties.Builder().nutrition(2).saturationMod(1).alwaysEat().effect(new MobEffectInstance(MOUE, 100, 0), 1.0F).build()
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
}

