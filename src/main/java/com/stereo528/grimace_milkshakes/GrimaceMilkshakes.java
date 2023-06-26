package com.stereo528.grimace_milkshakes;

import org.quiltmc.loader.api.ModContainer;
import org.quiltmc.qsl.base.api.entrypoint.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GrimaceMilkshakes implements ModInitializer {
	public static final String MODID = "example";
	public static final Logger LOGGER = LoggerFactory.getLogger("Example");

	@Override
	public void onInitialize(ModContainer mod) {
		LOGGER.info("Hello world from {}!", mod.metadata().name());
	}
}
