package com.stereo528.moue_milkshakes.Mixin;

import net.minecraft.world.inventory.BrewingStandMenu;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import static com.stereo528.moue_milkshakes.Util.Registar.SHAKE_MIX;
import static com.stereo528.moue_milkshakes.Util.Registar.SHAKE_MIX_SHAKE_CUP;

@Mixin(BrewingStandMenu.PotionSlot.class)
public class BrewingStandMenuMixin {
	@Inject(method = "mayPlaceItem", at = @At("HEAD"), cancellable = true)
	private static void addShakeMixCupToMatches(ItemStack itemStack, CallbackInfoReturnable<Boolean> cir) {
		if (itemStack.is(SHAKE_MIX_SHAKE_CUP) || itemStack.is(SHAKE_MIX)) {
			cir.setReturnValue(true);
			cir.cancel();
		}

	}
}
