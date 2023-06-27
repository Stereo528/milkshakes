package com.stereo528.grimace_milkshakes.Effects;

import com.stereo528.grimace_milkshakes.Util.Registar;
import net.minecraft.client.renderer.FogRenderer;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import org.quiltmc.loader.api.minecraft.ClientOnly;

public class GrimaceEffect extends MobEffect {
	public GrimaceEffect(MobEffectCategory mobEffectCategory, int color) {
		super(mobEffectCategory, color);
		FogRenderer.MOB_EFFECT_FOG.add(new GrimaceFogFunction());
	}


	@ClientOnly
	private static class GrimaceFogFunction implements FogRenderer.MobEffectFogFunction {
		@Override
		public MobEffect getMobEffect() {
			return Registar.GRIMACE;
		}

		@Override
		public void setupFog(FogRenderer.FogData fogData, LivingEntity livingEntity, MobEffectInstance mobEffectInstance, float f, float g) {

			float h = mobEffectInstance.isInfiniteDuration() ? 15.0F : Mth.lerp(Math.min(1.0F, (float) mobEffectInstance.getDuration() / 20.0F), f, 15.0F);
			fogData.start = 0.0f;
			fogData.end = h;
		}
	}
}
