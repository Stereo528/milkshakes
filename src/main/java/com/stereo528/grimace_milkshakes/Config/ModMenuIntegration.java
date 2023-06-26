package com.stereo528.grimace_milkshakes.Config;


import com.stereo528.grimace_milkshakes.GrimaceMilkshakes;
import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import eu.midnightdust.lib.config.MidnightConfig;

public class ModMenuIntegration implements ModMenuApi {
	@Override
	public ConfigScreenFactory<?> getModConfigScreenFactory() {
		return parent -> MidnightConfig.getScreen(parent, GrimaceMilkshakes.MODID);
	}
}
