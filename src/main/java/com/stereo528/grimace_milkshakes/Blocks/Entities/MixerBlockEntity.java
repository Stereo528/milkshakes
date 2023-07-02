package com.stereo528.grimace_milkshakes.Blocks.Entities;

import com.stereo528.grimace_milkshakes.Recipe.MixerRecipe;
import com.stereo528.grimace_milkshakes.Screens.MixerScreenHandler;
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
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;
import org.quiltmc.qsl.networking.api.PacketByteBufs;

import java.util.Optional;

import static com.stereo528.grimace_milkshakes.Util.Registar.MIXER_BLOCK_ENTITY;

public class MixerBlockEntity extends BlockEntity implements MenuProvider, ImplementedInventory {
	private final NonNullList<ItemStack> inventory = NonNullList.withSize(3, ItemStack.EMPTY);

	protected final ContainerData containerData;
	private int progress = 0;
	private int maxProgress = 64;

	public MixerBlockEntity(BlockPos blockPos, BlockState blockState) {
		super(MIXER_BLOCK_ENTITY, blockPos, blockState);
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
		return new MixerScreenHandler(i, inventory, this, this.containerData);
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
		if(hasRecipe(entity, level)) {
			entity.progress++;
			setChanged(level, blockPos, state);
			if (entity.progress >= entity.maxProgress) {
				craftItem(entity, level);
			}
			else {
				entity.resetProgress();
				setChanged(level, blockPos, state);
			}
		}
	}

	private static void craftItem(MixerBlockEntity entity, Level level) {
		SimpleContainer container = new SimpleContainer(entity.getContainerSize());
		for(int i=0; i < entity.getContainerSize(); i++) {
			container.setItem(i, entity.getItem(i));
		}
		Optional<MixerRecipe> recipe = entity.getLevel().getRecipeManager().getRecipeFor(MixerRecipe.Type.INSTANCE, container, entity.getLevel());

		if (hasRecipe(entity, level)) {
			entity.removeItem(1, 1);
			entity.setItem(2, new ItemStack(recipe.get().getResultItem(level.registryAccess()).getItem(), entity.getItem(2).getCount() + 1));
			entity.resetProgress();
		}

	}

	private static boolean hasRecipe(MixerBlockEntity entity, Level level) {
		SimpleContainer inventory = new SimpleContainer(entity.getContainerSize());
		for (int i = 0; i < entity.getContainerSize(); i++) {
			inventory.setItem(i, entity.getItem(i));
		}
		Optional<MixerRecipe> match = entity.getLevel().getRecipeManager().getRecipeFor(MixerRecipe.Type.INSTANCE, inventory, entity.getLevel());

		return match.isPresent() && canInsertAmountIntoOutputSlot(inventory)
			&& canInsertItemIntoOutputSlot(inventory, match.get().getResultItem(level.registryAccess()).getItem());
	}

	private static boolean canInsertItemIntoOutputSlot(SimpleContainer inventory, Item output) {
		return inventory.getItem(2).getItem() == output || inventory.getItem(2).isEmpty();
	}

	private static boolean canInsertAmountIntoOutputSlot(SimpleContainer inventory) {
		return inventory.getItem(2).getMaxStackSize() > inventory.getItem(2).getCount();
	}
}
