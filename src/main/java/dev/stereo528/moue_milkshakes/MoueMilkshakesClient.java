package dev.stereo528.moue_milkshakes;

import dev.stereo528.moue_milkshakes.Effects.MoueEffect.*;
import dev.stereo528.moue_milkshakes.Util.Registar;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.minecraft.client.renderer.FogRenderer;
import net.minecraft.client.renderer.RenderType;


public class MoueMilkshakesClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {

		//FogRenderer.MOB_EFFECT_FOG.add(new GrimaceFogFunction());
		BlockRenderLayerMap.INSTANCE.putBlocks(RenderType.cutout(), Registar.STRAWBERRY_CROP);
	}
}
