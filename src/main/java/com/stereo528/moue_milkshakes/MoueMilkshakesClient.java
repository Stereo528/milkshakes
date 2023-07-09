package com.stereo528.moue_milkshakes;

import eu.midnightdust.lib.config.MidnightConfig;
import com.stereo528.moue_milkshakes.Config.ModConfig;
import org.quiltmc.loader.api.ModContainer;
import org.quiltmc.qsl.base.api.entrypoint.client.ClientModInitializer;

import static com.stereo528.moue_milkshakes.MoueMilkshakes.MODID;

public class MoueMilkshakesClient implements ClientModInitializer {

	@Override
	public void onInitializeClient(ModContainer mod) {
		MidnightConfig.init(MODID, ModConfig.class);
	}
}
