package com.stereo528.grimace_milkshakes.Screens;

import com.stereo528.grimace_milkshakes.Util.Registar;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

public class MixerScreenHandler extends AbstractContainerMenu {
	private final Container container;
	private final ContainerData containerData;

	public MixerScreenHandler(int syncId, Inventory inventory) {
		this(syncId, inventory, new SimpleContainer(3), new SimpleContainerData(2));
	}
	public MixerScreenHandler(int syncId, Inventory inventory, Container container, ContainerData containerData) {
		super(Registar.MIXER_SCREEN_HANDLER, syncId);
		checkContainerSize(container, 3);
		this.container = container;
		container.startOpen(inventory.player);
		this.containerData = containerData;

		this.addSlot(new Slot(container, 0, 12, 12));
		this.addSlot(new Slot(container, 1, 60, 12));
		this.addSlot(new Slot(container, 2, 60, 60));

		addPlayerInventory(inventory);
		addPlayerHotbar(inventory);
	}


	public boolean isCrafting() {
		return containerData.get(0) > 0;
	}

	public int getScaledProgress() {
		int progress = this.containerData.get(0);
		int maxProgress = this.containerData.get(1);
		int progressArrowSize = 26;

		return maxProgress != 0 && progress != 0 ? progress * progressArrowSize / maxProgress : 0;
	}

	@Override
	public ItemStack quickMoveStack(Player player, int invSlot) {
		ItemStack newStack = ItemStack.EMPTY;
		Slot slot = this.slots.get(invSlot);
		if(slot != null && slot.hasItem()) {
			ItemStack originalStack = slot.getItem();
			newStack = originalStack.copy();
			if(invSlot < this.container.getContainerSize()) {
				if (!this.moveItemStackTo(originalStack, this.container.getContainerSize(), this.slots.size(), true)) {
					return ItemStack.EMPTY;
				}
			} else if (!this.moveItemStackTo(originalStack, 0, this.container.getContainerSize(), false)) {
				return ItemStack.EMPTY;
			}

			if(originalStack.isEmpty()) {
				slot.setByPlayer(ItemStack.EMPTY);
			} else {
				slot.setChanged();
			}
		}
		return newStack;
	}

	@Override
	public boolean stillValid(Player player) {
		return this.container.stillValid(player);
	}
	private void addPlayerInventory(Inventory inventory) {
		for(int i=0; i<3; ++i) {
			for(int j=0; j<9; ++j) {
				this.addSlot(new Slot(inventory, j + i * 9 + 9, 8 + j * 18, 86 + i * 18));
			}
		}
	}

	private void addPlayerHotbar(Inventory inventory) {
		for (int i=0; i<9; ++i) {
			this.addSlot(new Slot(inventory, i, 8 + i * 18, 144));
		}
	}
}
