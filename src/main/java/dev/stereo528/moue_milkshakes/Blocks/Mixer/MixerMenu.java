package dev.stereo528.moue_milkshakes.Blocks.Mixer;

import dev.stereo528.moue_milkshakes.Blocks.Mixer.Slots.MixerMilkSlot;
import dev.stereo528.moue_milkshakes.Blocks.Mixer.Slots.MixerResultSlot;
import dev.stereo528.moue_milkshakes.Util.Registar;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

import static dev.stereo528.moue_milkshakes.Blocks.Mixer.MixerMixing.isIngredientItem;

import static net.minecraft.world.item.Items.MILK_BUCKET;

public class MixerMenu extends AbstractContainerMenu {
    private final Container container;
    private final ContainerData containerData;

    public MixerMenu(@Nullable MenuType<?> menuType, int i, Inventory inventory, Container container, ContainerData containerData) {
        super(menuType, i);
        checkContainerSize(container, 4);
        this.container = container;
        this.containerData = containerData;
        this.addSlot(new MixerMilkSlot(container, 0, 32, 16)); //milk bucket
        this.addSlot(new Slot(container, 1, 80, 16)); //cup
        this.addSlot(new Slot(container, 2, 80, 48)); //ingredient
        this.addSlot(new MixerResultSlot(container, 3, 128, 32)); //result

        this.addDataSlots(containerData);

        for(int j = 0; j < 3; ++j) {
            for(int k = 0; k < 9; ++k) {
                this.addSlot(new Slot(inventory, k + j * 9 + 9, 8 + k * 18, 84 + j * 18));
            }
        }

        for(int j = 0; j < 9; ++j) {
            this.addSlot(new Slot(inventory, j, 8 + j * 18, 142));
        }
    }

    public MixerMenu(int i, Inventory inventory) {
        this(Registar.MIXER_MENU_TYPE, i, inventory, new SimpleContainer(4), new SimpleContainerData(2));
    }

    @Override
    public ItemStack quickMoveStack(Player player, int i) {
        int milkSlot = 0;
        int cupSlot = 1;
        int ingredientSlot = 2;
        int resultSlot = 3;
        int playerInvStart = resultSlot + 1;
        int playerInvEnd = playerInvStart + 36;
        ItemStack itemStackCopy = ItemStack.EMPTY;
        Slot slot = this.slots.get(i);
        if (slot != null && slot.hasItem()) {
            ItemStack itemStack = slot.getItem();
            itemStackCopy = itemStack.copy();
            if (i == resultSlot) {
                if (!this.moveItemStackTo(itemStack, playerInvStart, playerInvEnd, true)) {
                    return ItemStack.EMPTY;
                }
                slot.onQuickCraft(itemStack, itemStackCopy);
            } else if (i >= playerInvStart && i < playerInvEnd) {
                if (isIngredientItem(itemStack)) {
                    if (!this.moveItemStackTo(itemStack, ingredientSlot, ingredientSlot + 1, false)) {
                        return ItemStack.EMPTY;
                    }
                }
                if (itemStack.is(Registar.SHAKE_MIX_SHAKE_CUP)) {
                    if (!this.moveItemStackTo(itemStack, cupSlot, cupSlot + 1, false)) {
                        return ItemStack.EMPTY;
                    }
                }
                if (itemStack.is(MILK_BUCKET)) {
                    if (!this.moveItemStackTo(itemStack, milkSlot, milkSlot + 1, false)) {}
                }
            } else if (!this.moveItemStackTo(itemStack, playerInvStart, playerInvEnd, false)) {
                return ItemStack.EMPTY;
            }

            if (itemStack.isEmpty()) {
                slot.setByPlayer(ItemStack.EMPTY);
            } else {
                slot.setChanged();
            }

            if (itemStack.getCount() == itemStackCopy.getCount()) {
                return ItemStack.EMPTY;
            }

            slot.onTake(player, itemStack);
        }
        return itemStackCopy;
    }

    public int getFuel() {
        return this.containerData.get(0);
    }

    public int getMixtime() {
        return this.containerData.get(1);
    }

    @Override
    public boolean stillValid(Player player) {
        return this.container.stillValid(player);
    }
}