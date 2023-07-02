package com.stereo528.grimace_milkshakes.Recipe;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.minecraft.core.NonNullList;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;

public class MixerRecipe implements Recipe<SimpleContainer> {
	private final ResourceLocation id;
	private final ItemStack output;
	private final NonNullList<Ingredient> recipeItems;

	public MixerRecipe(ResourceLocation id, ItemStack output, NonNullList<Ingredient> recipeItems) {
		this.id = id;
		this.output = output;
		this.recipeItems = recipeItems;
	}


	@Override
	public boolean matches(SimpleContainer container, Level level) {
		if(level.isClientSide()) {
			return false;
		}

		return recipeItems.get(0).test(container.getItem(1));
	}

	@Override
	public ItemStack assemble(SimpleContainer container, RegistryAccess registryAccess) {
		return output;
	}

	@Override
	public boolean canCraftInDimensions(int width, int height) {
		return true;
	}

	@Override
	public ItemStack getResultItem(RegistryAccess registryAccess) {
		return output.copy();
	}

	@Override
	public ResourceLocation getId() {
		return id;
	}

	@Override
	public RecipeSerializer<?> getSerializer() {
		return Serializer.INSTANCE;
	}

	@Override
	public RecipeType<?> getType() {
		return Type.INSTANCE;
	}

	public static class Type implements RecipeType<MixerRecipe> {
		private Type() {}
		public static final Type INSTANCE = new Type();
		public static final String ID = "milkshake_mixing";
	}

	public static class Serializer implements RecipeSerializer<MixerRecipe> {
		public static final Serializer INSTANCE = new Serializer();
		public static final String ID = "milkshake_mixing";


		@Override
		public MixerRecipe fromJson(ResourceLocation resourceLocation, JsonObject jsonObject) {
			ItemStack output = ShapedRecipe.itemStackFromJson(GsonHelper.getAsJsonObject(jsonObject, "output"));
			JsonArray ingredients = GsonHelper.getAsJsonArray(jsonObject, "ingredients");
			NonNullList<Ingredient> inputs = NonNullList.withSize(1, Ingredient.EMPTY);

			for(int i=0; i < inputs.size(); i++) {
				inputs.set(i, Ingredient.fromJson(ingredients.get(i)));
			}
			return new MixerRecipe(resourceLocation, output, inputs);
		}

		@Override
		public MixerRecipe fromNetwork(ResourceLocation resourceLocation, FriendlyByteBuf friendlyByteBuf) {
			NonNullList<Ingredient> inputs = NonNullList.withSize(friendlyByteBuf.readInt(), Ingredient.EMPTY);

			for (int i = 0; i < inputs.size(); i++) {
				inputs.set(i, Ingredient.fromNetwork(friendlyByteBuf));
			}
			ItemStack output = friendlyByteBuf.readItem();

			return new MixerRecipe(resourceLocation, output, inputs);
		}

		@Override
		public void toNetwork(FriendlyByteBuf friendlyByteBuf, MixerRecipe recipe) {
			friendlyByteBuf.writeInt(recipe.getIngredients().size());
			for(Ingredient ingredient : recipe.getIngredients()) {
				ingredient.toNetwork(friendlyByteBuf);
			}
			friendlyByteBuf.writeItem(recipe.output);

		}
	}
}
