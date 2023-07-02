package com.stereo528.grimace_milkshakes.Screens;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

import static com.stereo528.grimace_milkshakes.GrimaceMilkshakes.MODID;

public class MixerScreen extends AbstractContainerScreen<MixerScreenHandler> {
	private static final ResourceLocation TEXTURE = new ResourceLocation(MODID, "textures/gui/mixer_gui.png");
	public MixerScreen(MixerScreenHandler handler, Inventory inventory, Component component) {
		super(handler, inventory, component);
	}

	@Override
	protected void init() {
		super.init();
		titleLabelX = 32;
	}

	@Override
	protected void renderBg(GuiGraphics guiGraphics, float delta, int mouseX, int mouseY) {
		RenderSystem.setShader(GameRenderer::getPositionTexShader);
		RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
		RenderSystem.setShaderTexture(0, TEXTURE);
		int x = (width - imageWidth) / 2;
		int y = (height - imageHeight) /2;
		guiGraphics.blit(TEXTURE, x, y, 0, 0, imageWidth, imageHeight);

		renderProgressArrow(guiGraphics, x, y);
	}

	private void renderProgressArrow(GuiGraphics guiGraphics, int x, int y) {
		if(menu.isCrafting()) {
			guiGraphics.blit(TEXTURE, x + 105, y + 33, 176, 0, 8, menu.getScaledProgress());
		}
	}

	@Override
	public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float delta) {
		this.renderBackground(guiGraphics);
		super.render(guiGraphics, mouseX, mouseY, delta);
		renderTooltip(guiGraphics, mouseX, mouseY);
	}
}
