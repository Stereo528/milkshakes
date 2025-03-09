package dev.stereo528.moue_milkshakes.Blocks;

import dev.stereo528.moue_milkshakes.Blocks.Mixer.MixerMenu;
import dev.stereo528.moue_milkshakes.Blocks.Mixer.MixerMixing;
import dev.stereo528.moue_milkshakes.Util.Registar;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.WorldlyContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BaseContainerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import static dev.stereo528.moue_milkshakes.Blocks.Mixer.MixerMixing.isIngredientItem;
import static net.minecraft.world.item.Items.BUCKET;
import static net.minecraft.world.item.Items.MILK_BUCKET;

public class MixerBlockEntity extends BaseContainerBlockEntity implements WorldlyContainer {//, RecipeCraftingHolder {

    private NonNullList<ItemStack> items;

    private static final int FUEL_PER_TICK = 1;  // 5 crafts per milk bucket
    private static final int MAX_FUEL = 1000; // doing this in milli-buckets because im too lazy to convert to droplets rn
    int fuel;
    protected final ContainerData fuelData;
    int mixTime;
    private Item currentIngredient;

    public MixerBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(Registar.MIXERBET, blockPos, blockState);
        this.items = NonNullList.withSize(4, ItemStack.EMPTY);
        this.fuelData = new ContainerData() {
            @Override
            public int get(int i) {
                int data;
                switch (i) {
                    case 0 -> data = MixerBlockEntity.this.fuel;
                    case 1 -> data = MixerBlockEntity.this.mixTime;
                    default -> data = 0;
                }
                return data;
            }

            @Override
            public void set(int i, int j) {
                switch (i) {
                    case 0 -> MixerBlockEntity.this.fuel = j;
                    case 1 -> MixerBlockEntity.this.mixTime = j;
                }
            }

            @Override
            public int getCount() {
                return 2;
            }
        };
    }

    @Override
    protected @NotNull Component getDefaultName() {
        return Component.translatable("moue_milkshakes.mixer");
    }

    @Override
    protected @NotNull NonNullList<ItemStack> getItems() {
        return this.items;
    }

    @Override
    protected void setItems(NonNullList<ItemStack> nonNullList) {
        this.items = nonNullList;
    }

    @Override
    protected @NotNull AbstractContainerMenu createMenu(int i, Inventory inventory) {
        return new MixerMenu(Registar.MIXER_MENU_TYPE, i, inventory, this, this.fuelData);
    }

    @Override
    protected void loadAdditional(CompoundTag compoundTag, HolderLookup.Provider provider) {
        super.loadAdditional(compoundTag, provider);
        ContainerHelper.loadAllItems(compoundTag, this.items, provider);
        this.fuel = compoundTag.getInt("fuel");
        this.mixTime = compoundTag.getInt("mixTime");
        if (this.mixTime > 0) {
            this.currentIngredient = this.items.get(2).getItem();
        }

    }

    @Override
    protected void saveAdditional(CompoundTag compoundTag, HolderLookup.Provider provider) {
        super.saveAdditional(compoundTag, provider);
        ContainerHelper.saveAllItems(compoundTag, this.items, provider);
        compoundTag.putInt("fuel", this.fuel);
        compoundTag.putInt("mixTime", this.mixTime);
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
        ItemStack fuelItem = mixerBlockEntity.getItem(0);
        if (mixerBlockEntity.fuel <= 0 && fuelItem.is(MILK_BUCKET)) {
            mixerBlockEntity.fuel = MAX_FUEL;
            mixerBlockEntity.setItem(0, BUCKET.getDefaultInstance());
            setChanged(level, blockPos, blockState);
        }
        boolean mixable = isMixable(mixerBlockEntity.items);
        boolean mixTimeLeft = mixerBlockEntity.mixTime > 0;
        ItemStack ingredientItem = mixerBlockEntity.items.get(2);
        if (mixTimeLeft) {
            --mixerBlockEntity.mixTime;
            mixerBlockEntity.fuel -= FUEL_PER_TICK;
            if (mixerBlockEntity.mixTime == 0) {
                makeShake(ingredientItem, mixerBlockEntity);
                ingredientItem.shrink(1);
            } else if (!mixable || !ingredientItem.is(mixerBlockEntity.currentIngredient) || mixerBlockEntity.fuel <= 0) {
                mixerBlockEntity.mixTime = 0;
            }
            setChanged(level, blockPos, blockState);
        } else if (mixable && mixerBlockEntity.fuel > 0) {
            mixerBlockEntity.mixTime = 200; //should be 10 seconds (200 ticks) for all shakes
            mixerBlockEntity.currentIngredient = ingredientItem.getItem();
            setChanged(level, blockPos, blockState);
        }
    }

    private static void makeShake(ItemStack itemStack, MixerBlockEntity mixerBlockEntity) {
        mixerBlockEntity.items.get(1).shrink(1);
        mixerBlockEntity.items.set(3, MixerMixing.makeShake(itemStack));
    }

    private static boolean isMixable(NonNullList<ItemStack> items) {
        ItemStack itemStack = items.get(2);
        if (itemStack.isEmpty()) {
            return false;
        }
        if (!items.get(3).isEmpty()) {
            return false;
        }
        if (isIngredientItem(itemStack)) {
            ItemStack cup = items.get(1);
            return !cup.isEmpty();
        }
        return false;
    }

    public static int getMaxFuel() {
        return MAX_FUEL;
    }


}
