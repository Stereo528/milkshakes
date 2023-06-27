package com.stereo528.grimace_milkshakes.Util;

import com.stereo528.grimace_milkshakes.Effects.GrimaceEffect;
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

import static com.stereo528.grimace_milkshakes.GrimaceMilkshakes.MILKSHAKES;
import static com.stereo528.grimace_milkshakes.GrimaceMilkshakes.MODID;

public class Registar {

	public static final MobEffect GRIMACE = registerEffect("grimace", new GrimaceEffect(MobEffectCategory.HARMFUL, 0x543e62));
	public static final Item GRIMACE_SHAKE = registerItem("grimace_shake", new Item(new QuiltItemSettings().food(
		new FoodProperties.Builder().nutrition(4).saturationMod(1).fast().alwaysEat().effect(new MobEffectInstance(GRIMACE, 30, 0), 1.0F).build())));

	public static void init() {
	}

	public static MobEffect registerEffect(String name, MobEffect mobEffect) {
		return Registry.register(BuiltInRegistries.MOB_EFFECT, new ResourceLocation(MODID, name), mobEffect);
	}

	public static Item registerItem(String name, Item item) {
		ItemGroupEvents.modifyEntriesEvent(MILKSHAKES).register(content -> {
			content.accept(item);
		});
		return Registry.register(BuiltInRegistries.ITEM, new ResourceLocation(MODID, name), item);
	}
}
