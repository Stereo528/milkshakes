package dev.stereo528.moue_milkshakes.Blocks.Mixer;

import dev.stereo528.moue_milkshakes.MoueMilkshakes;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static dev.stereo528.moue_milkshakes.Util.Registar.*;
import static net.minecraft.world.item.Items.*;

public class MixerMixing {
    private static final List<ItemStack> ingredients = new ArrayList<>();
    private static final List<ItemStack> results = new ArrayList<>();
    private static final List<Item[]> recipes = new ArrayList<>();

    public static void init() {
        setDefaultRecipes();
    }

    private static void setDefaultRecipes() {
        addRecipe(SUGAR, VANILLA_SHAKE);
        addRecipe(COCOA_BEANS, CHOCOLATE_SHAKE);
        addRecipe(SUGAR_STRAWBERRY, STRAWBERRY_SHAKE);
        addRecipe(GLOW_BERRIES, MOUE_SHAKE);
        addRecipe(CARROT, CARROT_SMOOTHIE);
        addRecipe(GOLDEN_CARROT, GOLDEN_CARROT_SMOOTHIE);
        addRecipe(NETHER_STAR, NETHER_STAR_SHAKE);
    }

    public static void addIngredient(ItemStack itemStack) {
        ingredients.add(itemStack);
    }

    public static void addRecipe(Item ingredient, Item result) {
        ingredients.add(ingredient.getDefaultInstance());
        results.add(result.getDefaultInstance());
        recipes.add(new Item[]{ingredient, result});
    }

    public static List<ItemStack> getIngredients() {
        return ingredients;
    }

    public static List<Item[]> getRecipes() {
        return recipes;
    }

    public static ItemStack makeShake(ItemStack itemStack) {
        for (Item[] items : recipes) {
            if (itemStack.is(items[0])) {
                return items[1].getDefaultInstance();
            }
        }
        return null;
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
