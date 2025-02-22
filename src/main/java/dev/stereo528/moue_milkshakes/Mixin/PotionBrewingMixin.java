package dev.stereo528.moue_milkshakes.Mixin;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.alchemy.PotionBrewing;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.function.Predicate;

import static dev.stereo528.moue_milkshakes.Util.Registar.*;

@Mixin(PotionBrewing.class)
public abstract class PotionBrewingMixin {

	@Shadow
	@Final
	@Mutable
	private static Predicate<ItemStack> ALLOWED_CONTAINER;

	@Inject(method = "<clinit>", at = @At("TAIL"))
	private static void addShakesToBrewing(CallbackInfo ci) {
		ALLOWED_CONTAINER = ALLOWED_CONTAINER.or(itemStack -> itemStack.is(SHAKE_MIX_SHAKE_CUP));

	}
}
