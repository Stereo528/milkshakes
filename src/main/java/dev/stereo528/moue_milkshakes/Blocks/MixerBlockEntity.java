package dev.stereo528.moue_milkshakes.Blocks;

import dev.stereo528.moue_milkshakes.Util.MixerMenu;
import dev.stereo528.moue_milkshakes.Util.Registar;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.network.chat.Component;
import net.minecraft.world.WorldlyContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BaseContainerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class MixerBlockEntity extends BaseContainerBlockEntity implements WorldlyContainer {//, RecipeCraftingHolder {

    private NonNullList<ItemStack> items;

    public MixerBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(Registar.MIXERBET, blockPos, blockState);
        this.items = NonNullList.withSize(2, ItemStack.EMPTY);
    }

    @Override
    protected Component getDefaultName() {
        return Component.translatable("moue_milkshakes.mixer");
    }

    @Override
    protected NonNullList<ItemStack> getItems() {
        return this.items;
    }

    @Override
    protected void setItems(NonNullList<ItemStack> nonNullList) {
        this.items = nonNullList;
    }

    @Override
    protected AbstractContainerMenu createMenu(int i, Inventory inventory) {
        return new MixerMenu(Registar.MIXER_MENU_TYPE, i, inventory, this);
    }

    @Override
    public int[] getSlotsForFace(Direction direction) {
        return new int[0];
    }

    @Override
    public boolean canPlaceItemThroughFace(int i, ItemStack itemStack, @Nullable Direction direction) {
        return false;
    }

    @Override
    public boolean canTakeItemThroughFace(int i, ItemStack itemStack, Direction direction) {
        return false;
    }

    @Override
    public int getContainerSize() {
        return this.items.size();
    }

    public static void serverTick(Level level, BlockPos blockPos, BlockState blockState, MixerBlockEntity mixerBlockEntity) {

    }
}
