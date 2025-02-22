package dev.stereo528.moue_milkshakes.Util;

import dev.stereo528.moue_milkshakes.Blocks.StrawberryCropBlock;
import dev.stereo528.moue_milkshakes.Effects.MoueEffect;
import net.fabricmc.fabric.api.item.v1.FabricItem;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.ColorRGBA;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemNameBlockItem;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.ColoredFallingBlock;
import net.minecraft.world.level.block.FallingBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;


import static dev.stereo528.moue_milkshakes.MoueMilkshakes.*;
import static net.minecraft.world.effect.MobEffects.NIGHT_VISION;
import static net.minecraft.world.level.block.Blocks.SAND;

public class Registar {


	public static MobEffect MOUE_EFFECT = new MoueEffect(MobEffectCategory.HARMFUL, 0x543e62);

	public static final Item MOUE_SHAKE = registerItem("moue_shake", new Item(new Item.Properties()
		.stacksTo(1).food(new FoodProperties.Builder().nutrition(2).saturationModifier(1).alwaysEdible()
			.effect(new MobEffectInstance((Holder<MobEffect>) MOUE_EFFECT, 100, 0), 1.0F).build()
	)));

	public static final Item VANILLA_SHAKE = registerItem("vanilla_shake", new Item(new Item.Properties()
		.stacksTo(1).food(new FoodProperties.Builder().nutrition(3).saturationModifier(2).build()
	)));
	public static final Item CHOCOLATE_SHAKE = registerItem("chocolate_shake", new Item(new Item.Properties()
		.stacksTo(1).food(new FoodProperties.Builder().nutrition(3).saturationModifier(2).build()
	)));
	public static final Item STRAWBERRY_SHAKE = registerItem("strawberry_shake", new Item(new Item.Properties()
		.stacksTo(1).food(new FoodProperties.Builder().nutrition(3).saturationModifier(2).build()
	)));

	public static final Item STRAWBERRY_JUICE = registerItem("strawberry_juice", new Item(new Item.Properties()
		.stacksTo(1).food(new FoodProperties.Builder().nutrition(2).saturationModifier(2).build()
	)));

	public static final Item CARROT_SMOOTHIE = registerItem("carrot_smoothie", new Item(new Item.Properties()
		.stacksTo(1).food(new FoodProperties.Builder().nutrition(3).saturationModifier(1.5f).alwaysEdible()
			.effect(new MobEffectInstance(NIGHT_VISION, 300, 0), 1.0F).build()
	)));

	public static final Item GOLDEN_CARROT_SMOOTHIE = registerItem("golden_carrot_smoothie", new Item(new Item.Properties()
		.stacksTo(1).food(new FoodProperties.Builder().nutrition(4).saturationModifier(6).alwaysEdible()
			.effect(new MobEffectInstance(NIGHT_VISION, 600, 0), 1.0F).build()
	)));

	public static final Item NETHER_STAR_SHAKE = registerItem("nether_star_shake", new Item(new Item.Properties()
		.stacksTo(1).food(new FoodProperties.Builder().nutrition(4).saturationModifier(1.5F)
			.effect(new MobEffectInstance(MobEffects.REGENERATION, 300, 2), 1.0F)
			.effect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 2100, 0), 1.0F)
			.effect(new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 2100, 0), 1.0F)
			.effect(new MobEffectInstance(MobEffects.ABSORPTION, 1200, 2), 1.0F).alwaysEdible().build()
		)));

	public static final Item SHAKE_MIX_SHAKE_CUP = registerItem("partially_filled_shake_cup", new Item(new Item.Properties().stacksTo(1)));

	public static final Item SHAKE_MIX = registerItem("shake_mix", new Item(new Item.Properties()));

	public static final Block SHAKE_MIX_BLOCK = registerBlock("shake_mix_block", new ColoredFallingBlock(new ColorRGBA(14406560), BlockBehaviour.Properties.ofFullCopy(SAND)));

	public static final Block SPEED_BLOCK = registerBlock("sugar_block", new ColoredFallingBlock(new ColorRGBA(14406560), BlockBehaviour.Properties.ofFullCopy(SAND)));

	public static final Block SHAKE_MIX_BLOCK_NO_GRAVITY = registerBlock("shake_mix_block_no_gravity", new Block(BlockBehaviour.Properties.ofFullCopy(SAND)));

	public static final Block SPEED_BLOCK_NO_GRAVITY = registerBlock("sugar_block_no_gravity", new Block(BlockBehaviour.Properties.ofFullCopy(SAND)));

	public static final Item STRAWBERRY = registerItem("strawberry", new Item(new Item.Properties().food(new
		FoodProperties.Builder().nutrition(1).saturationModifier(1).build()
	)));
	public static final Item SUGAR_STRAWBERRY = registerItem("sugared_strawberry", new Item(new Item.Properties().food(new
		FoodProperties.Builder().nutrition(2).saturationModifier(1).build()
	)));

	public static final Block STRAWBERRY_CROP = registerBlockNoItem("strawberry_crop", new StrawberryCropBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.WHEAT)));

	public static final Item STRAWBERRY_SEEDS = registerItem("strawberry_seeds", new ItemNameBlockItem(STRAWBERRY_CROP, new Item.Properties()));




	public static void init() {
	}

	public static Item registerItem(String name, Item item) {
		ItemGroupEvents.modifyEntriesEvent(ITEM_GROUP).register(content -> {
			content.accept(item);
		});
		return Registry.register(BuiltInRegistries.ITEM, ResourceLocation.tryBuild(MODID, name), item);
	}
	public static Block registerBlock(String name, Block block) {
		registerItem(name, new BlockItem(block, new Item.Properties()));
		return Registry.register(BuiltInRegistries.BLOCK, ResourceLocation.tryBuild(MODID, name), block);
	}

	public static Block registerBlockNoItem(String name, Block block) {
		return Registry.register(BuiltInRegistries.BLOCK, ResourceLocation.tryBuild(MODID, name), block);
	}
}

