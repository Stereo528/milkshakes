package dev.stereo528.moue_milkshakes.Util;

import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

public class MixerMenu extends AbstractContainerMenu {
    private final Container container;

    public MixerMenu(@Nullable MenuType<?> menuType, int i, Inventory inventory, Container container) {
        super(menuType, i);
        checkContainerSize(container, 4);
        this.container = container;
        this.addSlot(new Slot(container, 0, 16, 16)); //milk bucket
        this.addSlot(new Slot(container, 1, 64, 16)); //cup
        this.addSlot(new Slot(container, 2, 64, 48)); //ingredient
        this.addSlot(new Slot(container, 3, 112, 32)); //result

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
        this(Registar.MIXER_MENU_TYPE, i, inventory, new SimpleContainer(4));
    }

    @Override
    public ItemStack quickMoveStack(Player player, int i) {
        return ItemStack.EMPTY;
    }



    @Override
    public boolean stillValid(Player player) {
        return this.container.stillValid(player);
    }
}
