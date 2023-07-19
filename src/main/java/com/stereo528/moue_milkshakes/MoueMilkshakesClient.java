package com.stereo528.moue_milkshakes;

import com.stereo528.moue_milkshakes.Effects.MoueEffect.*;
import net.minecraft.client.renderer.FogRenderer;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import org.quiltmc.loader.api.ModContainer;
import org.quiltmc.qsl.base.api.entrypoint.client.ClientModInitializer;

import static com.stereo528.moue_milkshakes.MoueMilkshakes.MODID;
import static com.stereo528.moue_milkshakes.Util.Registar.MOUE_EFFECT;

public class MoueMilkshakesClient implements ClientModInitializer {
	@Override
	public void onInitializeClient(ModContainer mod) {
		Registry.register(BuiltInRegistries.MOB_EFFECT, new ResourceLocation(MODID, "moue"), MOUE_EFFECT);
		FogRenderer.MOB_EFFECT_FOG.add(new GrimaceFogFunction());
	}
}
