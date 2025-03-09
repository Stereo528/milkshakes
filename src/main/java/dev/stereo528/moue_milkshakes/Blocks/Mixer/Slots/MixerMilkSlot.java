package dev.stereo528.moue_milkshakes.Blocks.Mixer.Slots;

import net.minecraft.world.Container;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

import static net.minecraft.world.item.Items.MILK_BUCKET;

public class MixerMilkSlot extends Slot {

    public MixerMilkSlot(Container container, int i, int j, int k) {
        super(container, i, j, k);
    }

    @Override
    public boolean mayPlace(ItemStack itemStack) {
        return itemStack.is(MILK_BUCKET);
    }
}
