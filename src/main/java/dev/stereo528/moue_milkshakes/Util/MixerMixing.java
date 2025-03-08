package dev.stereo528.moue_milkshakes.Util;

import net.minecraft.world.item.ItemStack;

import java.util.List;

public class MixerMixing {
    private final List<ItemStack> ingredients;

    public MixerMixing(List<ItemStack> ingredients) {
        this.ingredients = ingredients;
    }

    private void setDefaultIngredients() {
        addIngredient(Registar.SUGAR_STRAWBERRY.getDefaultInstance());
    }

    public void addIngredient(ItemStack itemStack) {
        ingredients.add(itemStack);
    }


    public boolean isIngredientItem(ItemStack itemStack) {
        for (ItemStack item: this.ingredients) {
            if (item.equals(itemStack)) {
            return true;
            }
        }
        return false;
    }
}
