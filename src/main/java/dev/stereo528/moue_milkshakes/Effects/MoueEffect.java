package dev.stereo528.moue_milkshakes.Effects;

import net.minecraft.client.renderer.FogRenderer;
import net.minecraft.core.Holder;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;

import static dev.stereo528.moue_milkshakes.Util.Registar.MOUE_EFFECT;

public class MoueEffect extends MobEffect {
	public MoueEffect(MobEffectCategory mobEffectCategory, int color) {
		super(mobEffectCategory, color);
	}

	public static class GrimaceFogFunction implements FogRenderer.MobEffectFogFunction {
		@Override
		public Holder<MobEffect> getMobEffect() {
			return (Holder<MobEffect>) MOUE_EFFECT;
		}

		@Override
		public void setupFog(FogRenderer.FogData fogData, LivingEntity livingEntity, MobEffectInstance mobEffectInstance, float f, float g) {

			float h = mobEffectInstance.isInfiniteDuration() ? 15.0F : Mth.lerp(Math.min(1.0F, (float) mobEffectInstance.getDuration() / 20.0F), f, 15.0F);
			fogData.start = 0.0f;
			fogData.end = h;
		}
	}
}
