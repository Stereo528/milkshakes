package dev.stereo528.moue_milkshakes.EMI;

import dev.emi.emi.api.recipe.EmiRecipe;
import dev.emi.emi.api.recipe.EmiRecipeCategory;
import dev.emi.emi.api.stack.EmiIngredient;
import dev.emi.emi.api.stack.EmiStack;
import dev.emi.emi.api.widget.WidgetHolder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;
import org.jetbrains.annotations.Nullable;

import java.util.List;

import static dev.stereo528.moue_milkshakes.EMI.EmiEntry.MIXER_CAT;
import static dev.stereo528.moue_milkshakes.MoueMilkshakes.MODID;


public class MixerEmiRecipe implements EmiRecipe {

    private final ResourceLocation id;
    private static final ResourceLocation TEXTURE = ResourceLocation.tryBuild(MODID, "textures/gui/mixer.png");
    private static final ResourceLocation MILKTEX = ResourceLocation.tryBuild(MODID, "textures/gui/sprites/mixer/milk.png");
    private static final ResourceLocation PROGRESS = ResourceLocation.tryBuild(MODID, "textures/gui/sprites/mixer/progress.png");
    private static final EmiStack MILK = EmiStack.of(Items.MILK_BUCKET);
    private final EmiIngredient input, ingredient;
    private final EmiStack output;

    public MixerEmiRecipe(EmiStack input, EmiIngredient ingredient, EmiStack output, ResourceLocation id) {
        this.input = input;
        this.ingredient = ingredient;
        this.output = output;
        this.id = id;
    }


    @Override
    public EmiRecipeCategory getCategory() {
        return MIXER_CAT;
    }

    @Override
    public @Nullable ResourceLocation getId() {
        return id;
    }

    @Override
    public List<EmiIngredient> getInputs() {
        return List.of(input, ingredient);
    }

    @Override
    public List<EmiStack> getOutputs() {
        return List.of(output);
    }

    @Override
    public int getDisplayWidth() {
        return 116;
    }

    @Override
    public int getDisplayHeight() {
        return 52;
    }

   @Override
   public boolean supportsRecipeTree() {
        return false;
   }

    @Override
    public void addWidgets(WidgetHolder widgetHolder) {
        widgetHolder.addTexture(TEXTURE, 0, 0, 114, 50, 31, 14);
        widgetHolder.addAnimatedTexture(PROGRESS, 18, 19, 78, 14, 0, 0, 1000, true, false, false);
        widgetHolder.addTexture(MILKTEX, 1, 26, 16, 24, 0, 0);
        widgetHolder.addSlot(MILK, 0, 1).drawBack(false);
        widgetHolder.addSlot(input, 48, 1).drawBack(false);
        widgetHolder.addSlot(ingredient, 48, 33).drawBack(false);
        widgetHolder.addSlot(output, 96, 17).drawBack(false).recipeContext(this);
    }
}
