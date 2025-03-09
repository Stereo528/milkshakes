package dev.stereo528.moue_milkshakes;

//import io.github.tropheusj.milk.Milk; //disabled till milk lib update
import dev.stereo528.moue_milkshakes.Effects.MoueEffect;
import dev.stereo528.moue_milkshakes.Blocks.Mixer.MixerMixing;
import dev.stereo528.moue_milkshakes.Util.Registar;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.loot.v3.LootTableEvents;
import net.minecraft.client.renderer.FogRenderer;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.*;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSet;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceCondition;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.NumberProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;

import static dev.stereo528.moue_milkshakes.Util.Registar.*;

public class MoueMilkshakes implements ModInitializer {
	public static final String MODID = "moue_milkshakes";
	public static final Logger LOGGER = LoggerFactory.getLogger("Milkshakes");



	public static final ResourceKey<CreativeModeTab> ITEM_GROUP = ResourceKey.create(Registries.CREATIVE_MODE_TAB,  ResourceLocation.tryBuild(MODID, "milkshakes.tab"));
	public static final CreativeModeTab MILKSHAKES = FabricItemGroup.builder()
		.icon(() -> new ItemStack(MOUE_SHAKE))
		.title(Component.translatable("itemGroup.moue_milkshakes.milkshakes_tab"))
		.build();

	@Override
	public void onInitialize() {
		Registry.register(BuiltInRegistries.CREATIVE_MODE_TAB, ITEM_GROUP, MILKSHAKES);
		FogRenderer.MOB_EFFECT_FOG.add(new MoueEffect.GrimaceFogFunction());
		MixerMixing.init();

		LootTableEvents.MODIFY.register(((resourceKey, builder, lootTableSource, provider) -> {
			if (lootTableSource.isBuiltin() && Blocks.SHORT_GRASS.getLootTable().equals(resourceKey)) {
				LootPool.Builder pool = LootPool.lootPool()
						.setRolls(ConstantValue.exactly(1))
						.conditionally(LootItemRandomChanceCondition.randomChance(0.125f).build())
						.with(LootItem.lootTableItem(STRAWBERRY_SEEDS).build())
						.apply(SetItemCountFunction.setCount(ConstantValue.exactly(1)));

				builder.pool(pool.build());
			}
		}));
	}
}
