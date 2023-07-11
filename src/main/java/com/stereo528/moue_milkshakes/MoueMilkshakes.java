package com.stereo528.moue_milkshakes;

import com.stereo528.moue_milkshakes.Util.Registar;
import io.github.tropheusj.milk.Milk;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.alchemy.PotionBrewing;
import net.minecraft.world.item.crafting.Ingredient;
import org.quiltmc.loader.api.ModContainer;
import org.quiltmc.qsl.base.api.entrypoint.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.stereo528.moue_milkshakes.Util.Registar.*;
import static net.minecraft.world.item.Items.*;

public class MoueMilkshakes implements ModInitializer {
	public static final String MODID = "moue_milkshakes";
	public static final Logger LOGGER = LoggerFactory.getLogger("Milkshakes");

	public static final ResourceKey<CreativeModeTab> ITEM_GROUP = ResourceKey.create(Registries.CREATIVE_MODE_TAB,  new ResourceLocation(MODID, "milkshakes.tab"));
	public static final CreativeModeTab MILKSHAKES = FabricItemGroup.builder()
		.icon(() -> new ItemStack(MOUE_SHAKE))
		.title(Component.translatable("itemGroup.moue_milkshakes.milkshakes_tab"))
		.build();



	@Override
	public void onInitialize(ModContainer mod) {
		Milk.enableAllMilkBottles();
		// Most of the stuff to be registered is here:
		Registar.init();
		//I love using the same method as potion + gunpowder = splash to do anything that's not a potion item
		//Shoutout to Sisby Folk for letting me use their code they used in Pollinator's Paradise
		PotionBrewing.CONTAINER_MIXES.add(new PotionBrewing.Mix<>(Registar.SHAKE_MIX_SHAKE_CUP, Ingredient.of(SUGAR), Registar.VANILLA_SHAKE));
		PotionBrewing.CONTAINER_MIXES.add(new PotionBrewing.Mix<>(Registar.SHAKE_MIX_SHAKE_CUP, Ingredient.of(COCOA_BEANS), Registar.CHOCOLATE_SHAKE));
		PotionBrewing.CONTAINER_MIXES.add(new PotionBrewing.Mix<>(Registar.SHAKE_MIX_SHAKE_CUP, Ingredient.of(SWEET_BERRIES), Registar.STRAWBERRY_SHAKE));
		PotionBrewing.CONTAINER_MIXES.add(new PotionBrewing.Mix<>(Registar.SHAKE_MIX_SHAKE_CUP, Ingredient.of(GLOW_BERRIES), MOUE_SHAKE));
		PotionBrewing.CONTAINER_MIXES.add(new PotionBrewing.Mix<>(Milk.MILK_BOTTLE, Ingredient.of(SHAKE_MIX), SHAKE_MIX_SHAKE_CUP));

		//What can't be easily registered there is here:
			//item group
		Registry.register(BuiltInRegistries.CREATIVE_MODE_TAB, ITEM_GROUP, MILKSHAKES);
	}
}
