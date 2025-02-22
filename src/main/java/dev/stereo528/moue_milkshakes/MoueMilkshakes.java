package dev.stereo528.moue_milkshakes;

//import io.github.tropheusj.milk.Milk; //disabled till milk lib update
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static net.minecraft.world.item.Items.*;

public class MoueMilkshakes implements ModInitializer {
	public static final String MODID = "moue_milkshakes";
	public static final Logger LOGGER = LoggerFactory.getLogger("Milkshakes");

	public static final ResourceKey<CreativeModeTab> ITEM_GROUP = ResourceKey.create(Registries.CREATIVE_MODE_TAB,  ResourceLocation.tryBuild(MODID, "milkshakes.tab"));
	public static final CreativeModeTab MILKSHAKES = FabricItemGroup.builder()
		.icon(() -> new ItemStack(DIAMOND))
		.title(Component.translatable("itemGroup.moue_milkshakes.milkshakes_tab"))
		.build();

	@Override
	public void onInitialize() {
		Registry.register(BuiltInRegistries.CREATIVE_MODE_TAB, ITEM_GROUP, MILKSHAKES);

	}
}
