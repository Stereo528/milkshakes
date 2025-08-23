package dev.stereo528.moue_milkshakes.Items;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;

import java.util.List;

public class WonderlandShakeItem extends Item {

    public WonderlandShakeItem(Properties properties) {
        super(properties);
    }

    @Override
    public void appendHoverText(ItemStack itemStack, TooltipContext tooltipContext, List<Component> list, TooltipFlag tooltipFlag) {
        list.add(Component.translatable("itemTooltip.moue_milkshakes.wonderland").withStyle(ChatFormatting.GRAY));
    }
}
