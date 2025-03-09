package dev.stereo528.moue_milkshakes.Blocks.Mixer;

import com.mojang.blaze3d.systems.RenderSystem;
import dev.stereo528.moue_milkshakes.Blocks.MixerBlockEntity;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Inventory;

import static dev.stereo528.moue_milkshakes.MoueMilkshakes.MODID;

public class MixerScreen extends AbstractContainerScreen<MixerMenu> {

    private static final ResourceLocation MIXER_LOCATION = ResourceLocation.tryBuild(MODID, "textures/gui/mixer.png");
    private static final ResourceLocation MILK = ResourceLocation.tryBuild(MODID, "mixer/milk");
    private static final ResourceLocation PROGRESS = ResourceLocation.tryBuild(MODID, "mixer/progress");

    public MixerScreen(MixerMenu abstractContainerMenu, Inventory inventory, Component component) {
        super(abstractContainerMenu, inventory, component);
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float f, int i, int j) {
        int x = (this.width - this.imageWidth) / 2;
        int y = (this.height - this.imageHeight) / 2;
        guiGraphics.blit(MIXER_LOCATION, x, y, 0, 0, this.imageWidth, this.imageHeight);

        int fuel = (this.menu).getFuel();
        int step = Mth.ceil(Mth.clamp((float) fuel / (float) MixerBlockEntity.getMaxFuel(), 0.f, 1.f)*23.f) + 1;
        if (fuel > 0) {
            guiGraphics.blitSprite(MILK, 16, 24, 0, 24 - step, x + 32, y + 40 + 24 - step, 16, step);
        }
        int mixStep = (int) (78.f*(1.f - (float) this.menu.getMixtime() / 200.f));
        if (this.menu.getMixtime() > 0) {
            if (mixStep > 0) {
                guiGraphics.blitSprite(PROGRESS, 78, 14, 0, 0, x + 49, y + 33, mixStep, 14);
            }
        }
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
        renderBg(guiGraphics, partialTicks, mouseX, mouseY);
        super.render(guiGraphics, mouseX, mouseY, partialTicks);
        renderTooltip(guiGraphics, mouseX, mouseY);
    }
}
