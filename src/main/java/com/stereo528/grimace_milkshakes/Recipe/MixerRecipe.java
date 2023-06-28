package com.stereo528.grimace_milkshakes.Recipe;

import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;

public class MixerRecipe implements Recipe<SimpleContainer> {
	@Override
	public boolean matches(SimpleContainer container, Level level) {
		return false;
	}

	@Override
	public ItemStack assemble(SimpleContainer container, RegistryAccess registryAccess) {
		return null;
	}

	@Override
	public boolean canCraftInDimensions(int i, int j) {
		return false;
	}

	@Override
	public ItemStack getResultItem(RegistryAccess registryAccess) {
		return null;
	}

	@Override
	public ResourceLocation getId() {
		return null;
	}

	@Override
	public RecipeSerializer<?> getSerializer() {
		return null;
	}

	@Override
	public RecipeType<?> getType() {
		return null;
	}
}
