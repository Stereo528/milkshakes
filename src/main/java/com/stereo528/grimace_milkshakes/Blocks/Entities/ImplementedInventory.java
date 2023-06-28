package com.stereo528.grimace_milkshakes.Blocks.Entities;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.Container;
import net.minecraft.world.WorldlyContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.core.NonNullList;
import net.minecraft.core.Direction;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.function.Supplier;

/**
 * A simple {@code WorldlyContainer} implementation with only default methods + an item list getter.
 *
 * <h2>Reading and writing to tags</h2>
 * Use {@link ContainerHelper#saveAllItems(CompoundTag, NonNullList)} and {@link ContainerHelper#loadAllItems(CompoundTag, NonNullList)}
 * on {@linkplain #getItems() the item list}.
 * <p>Edited by Stereo528 for Mojmap</p>
 * License: <a href="https://creativecommons.org/publicdomain/zero/1.0/">CC0</a>
 * @author Juuz
 *
 */
public interface ImplementedInventory extends WorldlyContainer {
	/**
	 * Gets the item list of this inventory.
	 * Must return the same instance every time it's called.
	 *
	 * @return the item list
	 */
	NonNullList<ItemStack> getItems();

	// WorldlyContainer

	/**
	 * Gets the available slots to automation on the side.
	 *
	 * <p>The default implementation returns an array of all slots.
	 *
	 * @param side the side
	 * @return the available slots
	 */
	@Override
	default int[] getSlotsForFace(Direction side) {
		int[] result = new int[getItems().size()];
		for (int i = 0; i < result.length; i++) {
			result[i] = i;
		}

		return result;
	}

	/**
	 * Returns true if the stack can be inserted in the slot at the side.
	 *
	 * <p>The default implementation returns true.
	 *
	 * @param slot the slot
	 * @param stack the stack
	 * @param side the side
	 * @return true if the stack can be inserted
	 */
	@Override
	default boolean canPlaceItemThroughFace(int slot, ItemStack stack, @Nullable Direction side) {
		return true;
	}

	/**
	 * Returns true if the stack can be extracted from the slot at the side.
	 *
	 * <p>The default implementation returns true.
	 *
	 * @param slot the slot
	 * @param stack the stack
	 * @param side the side
	 * @return true if the stack can be extracted
	 */
	@Override
	default boolean canTakeItemThroughFace(int slot, ItemStack stack, Direction side) {
		return true;
	}

	// Inventory

	/**
	 * Returns the inventory size.
	 *
	 * <p>The default implementation returns the size of {@link #getItems()}.
	 *
	 * @return the inventory size
	 */
	@Override
	default int getContainerSize() {
		return getItems().size();
	}

	/**
	 * @return true if this inventory has only empty stacks, false otherwise
	 */
	@Override
	default boolean isEmpty() {
		for (int i = 0; i < getContainerSize(); i++) {
			ItemStack stack = getItem(i);
			if (!stack.isEmpty()) {
				return false;
			}
		}

		return true;
	}

	/**
	 * Gets the item in the slot.
	 *
	 * @param slot the slot
	 * @return the item in the slot
	 */
	@Override
	default ItemStack getItem(int slot) {
		return getItems().get(slot);
	}

	/**
	 * Takes a stack of the size from the slot.
	 *
	 * <p>(default implementation) If there are less items in the slot than what are requested,
	 * takes all items in that slot.
	 *
	 * @param slot the slot
	 * @param count the item count
	 * @return a stack
	 */
	@Override
	default ItemStack removeItem(int slot, int count) {
		ItemStack result = ContainerHelper.removeItem(getItems(), slot, count);
		if (!result.isEmpty()) {
			setChanged();
		}

		return result;
	}

	/**
	 * Removes the current stack in the {@code slot} and returns it.
	 *
	 * <p>The default implementation uses {@link ContainerHelper#takeItem(List, int)}
	 *
	 * @param slot the slot
	 * @return the removed stack
	 */
	@Override
	default ItemStack removeItemNoUpdate(int slot) {
		return ContainerHelper.takeItem(getItems(), slot);
	}

	/**
	 * Replaces the current stack in the {@code slot} with the provided stack.
	 *
	 * <p>If the stack is too big for this inventory ({@link Container#getMaxStackSize()}),
	 * it gets resized to this inventory's maximum amount.
	 *
	 * @param slot the slot
	 * @param stack the stack
	 */
	@Override
	default void setItem(int slot, ItemStack stack) {
		getItems().set(slot, stack);
		if (stack.getCount() > getMaxStackSize()) {
			stack.setCount(getMaxStackSize());
		}
	}

	@Override
	default void setChanged() {
	}

	/**
	 * Clears {@linkplain #getItems() the item list}.
	 */
	@Override
	default void clearContent() {
		getItems().clear();
	}
}