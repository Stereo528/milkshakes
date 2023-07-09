package com.stereo528.moue_milkshakes.Mixin;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.FogRenderer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import org.quiltmc.loader.api.minecraft.ClientOnly;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static com.stereo528.moue_milkshakes.Util.Registar.MOUE;

@Mixin(FogRenderer.class)
public class FogRendererMixin {
	@Shadow
	private static float fogRed;

	@Shadow
	private static float fogBlue;

	@Shadow
	private static float fogGreen;

	@ClientOnly
	@Inject(method = "setupColor", at = @At("TAIL"))
	private static void grimaceColor(Camera camera, float f, ClientLevel clientLevel, int i, float g, CallbackInfo ci) {
		Entity entity = camera.getEntity();
		if(entity instanceof Player player) {
			if(player.hasEffect(MOUE)) {
				fogRed = 84f/255f;
				fogGreen = 62f/255f;
				fogBlue = 98f/255f;

				RenderSystem.clearColor(fogRed, fogGreen, fogBlue, 0.0F);
			}
		}
	}
}
