package com.austindorff.mechanica.client.gui.energy.producer;

import com.austindorff.mechanica.Reference;
import com.austindorff.mechanica.client.gui.component.ModeButton;
import com.austindorff.mechanica.container.energy.producer.ContainerCoalGenerator;
import com.austindorff.mechanica.tileentity.energy.producer.coalgenerator.TileEntityCoalGenerator;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.ResourceLocation;

public class GuiCoalGenerator extends GuiContainer {
	
	private IInventory				playerInventory;
	private TileEntityCoalGenerator	tile;
	private ContainerCoalGenerator	container;
	private ModeButton				modeButton;
									
	public GuiCoalGenerator(IInventory playerInventory, TileEntityCoalGenerator tile, ContainerCoalGenerator container) {
		super(container);
		this.container = container;
		this.playerInventory = playerInventory;
		this.tile = tile;
	}
	
	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
		GlStateManager.color(1.0F, 1.0F, 1.0F);
		this.mc.getTextureManager().bindTexture(new ResourceLocation(Reference.MOD_ID + ":" + "textures/gui/coalGenerator.png"));
		this.drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);
		
		int i = (this.width - this.xSize) / 2;
		int j = (this.height - this.ySize) / 2;
		if (tile.burning()) {
			int k = this.getBurnLeftScaled(13);
			this.drawTexturedModalRect(i + 81, j + 38 + 12 - k, 176, 12 - k, 14, k + 1);
		}
		
		int f = this.getEnergyStoredScaled(54);
		if (f == 0 && this.tile.getField(2) > 0) {
			f = 1;
		}
		this.drawTexturedModalRect(i + 61, j + 16, 176, 14, f, 18);
	}
	
	private int getBurnLeftScaled(int pixels) {
		int i = this.tile.getField(1);
		
		if (i == 0) {
			i = 200;
		}
		
		return this.tile.getField(3) * pixels / i;
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
		String title = tile.getDisplayName().getUnformattedText();
		this.fontRendererObj.drawString(title, this.xSize / 2 - this.fontRendererObj.getStringWidth(title) / 2, 6, 4210752);
		this.fontRendererObj.drawString(this.playerInventory.getDisplayName().getUnformattedText(), 8, this.ySize - 96 + 2, 4210752);
	}
	
	private int getEnergyStoredScaled(int pixels) {
		int i = this.tile.getField(2);
		return i != 0 ? (i * pixels / this.tile.getField(4)) : 0;
	}
}
