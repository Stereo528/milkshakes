package dev.stereo528.moue_milkshakes.EMI;

import dev.emi.emi.api.EmiPlugin;
import dev.emi.emi.api.EmiRegistry;
import dev.emi.emi.api.recipe.EmiRecipeCategory;
import dev.emi.emi.api.render.EmiTexture;
import dev.emi.emi.api.stack.EmiStack;
import dev.stereo528.moue_milkshakes.Blocks.Mixer.MixerMixing;
import dev.stereo528.moue_milkshakes.Util.Registar;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.List;

import static dev.stereo528.moue_milkshakes.MoueMilkshakes.LOGGER;
import static dev.stereo528.moue_milkshakes.MoueMilkshakes.MODID;

public class EmiEntry implements EmiPlugin {
    public static final ResourceLocation EMI_TEXTURE = ResourceLocation.tryBuild(MODID, "textures/gui/mixer.png");
    public static final EmiStack MIXER = EmiStack.of(Registar.MIXER);
    public static final EmiRecipeCategory MIXER_CAT = new EmiRecipeCategory(ResourceLocation.tryBuild(MODID, "mixer"), MIXER, new EmiTexture(EMI_TEXTURE, 0, 0, 256, 256));

    @Override
    public void register(EmiRegistry emiRegistry) {
        emiRegistry.addCategory(MIXER_CAT);
        emiRegistry.addWorkstation(MIXER_CAT, MIXER);
        for (ItemStack ingredient : MixerMixing.getIngredients()) {
            String pid = getId(ingredient);
            for (Item[] recipe : MixerMixing.getRecipes()) {
                try {
                     Item result = recipe[1];
                     if(ingredient.getItem() == recipe[0]) {
                         ResourceLocation id = ResourceLocation.tryBuild(MODID, "/mixing/" + pid + "/" + getId(new ItemStack(result)));
                         emiRegistry.addRecipe(new MixerEmiRecipe(
                                 EmiStack.of(Registar.SHAKE_MIX_SHAKE_CUP), EmiStack.of(ingredient), EmiStack.of(result), id));
                     }
                }
                catch (Exception e) {
                    LOGGER.error("Error registering Mixer Recipe", e);
                }
            }
        }
    }

    private static String getId(ItemStack item) {
        ResourceLocation resLoc = BuiltInRegistries.ITEM.getKey(item.getItem());
        return resLoc.getNamespace() + "/" + resLoc.getPath();
    }
}
