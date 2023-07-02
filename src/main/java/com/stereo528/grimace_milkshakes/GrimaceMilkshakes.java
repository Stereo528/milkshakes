package com.stereo528.grimace_milkshakes;

import com.stereo528.grimace_milkshakes.Blocks.Entities.MixerBlockEntity;
import com.stereo528.grimace_milkshakes.Recipe.MixerRecipe;
import com.stereo528.grimace_milkshakes.Screens.MixerScreen;
import com.stereo528.grimace_milkshakes.Screens.MixerScreenHandler;
import com.stereo528.grimace_milkshakes.Util.Registar;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.screenhandler.v1.ScreenHandlerRegistry;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import org.quiltmc.loader.api.ModContainer;
import org.quiltmc.qsl.base.api.entrypoint.ModInitializer;
import org.quiltmc.qsl.block.entity.api.QuiltBlockEntityTypeBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.stereo528.grimace_milkshakes.Util.Registar.*;

public class GrimaceMilkshakes implements ModInitializer {
	public static final String MODID = "grimace_milkshakes";
	public static final Logger LOGGER = LoggerFactory.getLogger("Example");

	public static final ResourceKey<CreativeModeTab> ITEM_GROUP = ResourceKey.create(Registries.CREATIVE_MODE_TAB,  new ResourceLocation(MODID, "milkshakes.tab"));
	public static final CreativeModeTab MILKSHAKES = FabricItemGroup.builder()
		.icon(() -> new ItemStack(GRIMACE_SHAKE))
		.title(Component.translatable("itemGroup.grimace_milkshakes.milkshakes_tab"))
		.build();



	@Override
	public void onInitialize(ModContainer mod) {
		// Most of the stuff to be registered is here:
		Registar.init();

		//What can't be easily registered there is here:
			//block entity
		MIXER_BLOCK_ENTITY = Registry.register(BuiltInRegistries.BLOCK_ENTITY_TYPE,
			new ResourceLocation(MODID, "mixer_block"),
			QuiltBlockEntityTypeBuilder.create(MixerBlockEntity::new, MIXER_BLOCK).build(null));

			//item group
		Registry.register(BuiltInRegistries.CREATIVE_MODE_TAB, ITEM_GROUP, MILKSHAKES);

			//recipe
		Registry.register(BuiltInRegistries.RECIPE_SERIALIZER, new ResourceLocation(MODID, MixerRecipe.Serializer.ID), MixerRecipe.Serializer.INSTANCE);
		Registry.register(BuiltInRegistries.RECIPE_TYPE, new ResourceLocation(MODID, MixerRecipe.Type.ID), MixerRecipe.Type.INSTANCE);

			// screen
		MenuScreens.register(MIXER_SCREEN_HANDLER, MixerScreen::new);


	}
}
