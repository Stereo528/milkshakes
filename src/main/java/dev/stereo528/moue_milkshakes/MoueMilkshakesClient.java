package dev.stereo528.moue_milkshakes;

import dev.stereo528.moue_milkshakes.Blocks.Mixer.MixerScreen;
import dev.stereo528.moue_milkshakes.Util.Registar;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.renderer.RenderType;


public class MoueMilkshakesClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {

		BlockRenderLayerMap.INSTANCE.putBlocks(RenderType.cutout(), Registar.STRAWBERRY_CROP);
		MenuScreens.register(Registar.MIXER_MENU_TYPE, MixerScreen::new);
	}
}
