package dev.stereo528.moue_milkshakes.Blocks.Mixer;

import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

import static dev.stereo528.moue_milkshakes.Util.Registar.SUGAR_STRAWBERRY;

public class MixerMixing {
    private static final List<ItemStack> ingredients = new ArrayList<>();

    public static void init() {
        setDefaultIngredients();
    }

    private static void setDefaultIngredients() {
        addIngredient(new ItemStack(SUGAR_STRAWBERRY));
    }

    public static void addIngredient(ItemStack itemStack) {
        ingredients.add(itemStack);
    }

    public static List<ItemStack> getIngredients() {
        return ingredients;
    }


    public static boolean isIngredientItem(ItemStack itemStack) {
        for (ItemStack item: ingredients) {
            if (item.is(itemStack.getItem())) {
            return true;
            }
        }
        return false;
    }
}
