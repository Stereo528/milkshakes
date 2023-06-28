package com.stereo528.grimace_milkshakes.Blocks.Entities;

import com.stereo528.grimace_milkshakes.Recipe.MixerRecipe;
import com.stereo528.grimace_milkshakes.Util.Registar;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class MixerBlockEntity extends BlockEntity implements MenuProvider, ImplementedInventory {
	private final NonNullList<ItemStack> inventory = NonNullList.withSize(3, ItemStack.EMPTY);

	protected final ContainerData containerData;
	private int progress = 0;
	private int maxProgress = 64;

	public MixerBlockEntity(BlockEntityType<?> blockEntityType, BlockPos blockPos, BlockState blockState) {
		super(blockEntityType, blockPos, blockState);
		this.containerData = new ContainerData() {
			@Override
			public int get(int index) {
				return switch (index) {
					case 0 -> MixerBlockEntity.this.progress;
					case 1 -> MixerBlockEntity.this.maxProgress;
					default -> 0;
				};
			}

			@Override
			public void set(int index, int value) {
				switch (index) {
					case 0 -> MixerBlockEntity.this.progress = value;
					case 1 -> MixerBlockEntity.this.maxProgress = value;
				}
			}

			@Override
			public int getCount() {
				return 2;
			}
		};
	}

	@Override
	public NonNullList<ItemStack> getItems() {
		return this.inventory;
	}

	@Override
	public Component getDisplayName() {
		return Component.translatable("container.grimace_milkshakes.mixer");
	}

	@Override
	public boolean stillValid(Player player) {
		return true;
	}

	@Nullable
	@Override
	public AbstractContainerMenu createMenu(int i, Inventory inventory, Player player) {
		return null;
	}

	@Override
	protected void saveAdditional(CompoundTag compoundTag) {
		super.saveAdditional(compoundTag);
		ContainerHelper.saveAllItems(compoundTag, inventory);
		compoundTag.putInt("mixer_block.progress", progress);
	}

	@Override
	public void load(CompoundTag compoundTag) {
		ContainerHelper.loadAllItems(compoundTag, inventory);
		super.load(compoundTag);
		progress = compoundTag.getInt("mixer_block.progress");
	}

	private void resetProgress() {
		this.progress = 0;
	}

	public static void tick(Level level, BlockPos blockPos, BlockState state, MixerBlockEntity entity) {
		if(level.isClientSide()) {
			return;
		}
		if(hasRecipe())
	}

	private static boolean hasRecipe(MixerBlockEntity entity) {
		SimpleContainer inventory = new SimpleContainer(entity.getContainerSize());
		for (int i = 0; i < entity.getContainerSize(); i++) {
			inventory.setItem(i, entity.getItem(i));
		}
		Optional<MixerRecipe> match = entity.getLevel().getRecipeManager().getRecipeFor(MixerRecipe.Type.INSTANCE, inventory, entity.getLevel());

		return match.isPresent() && canInsertAmountIntoOutputSlot(inventory)
			&& canInsertItemIntoOutputSlot(inventory, match.get().getResultItem().getItem());
	}
}
