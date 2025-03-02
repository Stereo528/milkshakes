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
			return MOUE_EFFECT;
		}

		@Override
		public void setupFog(FogRenderer.FogData fogData, LivingEntity livingEntity, MobEffectInstance mobEffectInstance, float f, float g) {
			float h = Mth.lerp(mobEffectInstance.getBlendFactor(livingEntity, g), f, 15.0F);
			fogData.start = fogData.mode == FogRenderer.FogMode.FOG_SKY ? 0.0F : h * 0.75F;
			fogData.end = h;
		}

		public float getModifiedVoidDarkness(LivingEntity livingEntity, MobEffectInstance mobEffectInstance, float f, float g) {
			return 1.0F - mobEffectInstance.getBlendFactor(livingEntity, g);
		}
	}
}
