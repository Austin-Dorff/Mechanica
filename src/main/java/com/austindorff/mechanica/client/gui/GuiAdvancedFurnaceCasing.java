package com.austindorff.mechanica.client.gui;

import java.io.IOException;

import org.lwjgl.input.Mouse;

import com.austindorff.mechanica.Reference;
import com.austindorff.mechanica.client.gui.component.ModeButton;
import com.austindorff.mechanica.container.machine.ContainerAdvancedFurnaceCasing;
import com.austindorff.mechanica.network.packet.block.machine.PacketAdvancedFurnace;
import com.austindorff.mechanica.tileentity.machine.TileAdvancedFurnaceCasing;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.inventory.IInventory;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.FMLClientHandler;

public class GuiAdvancedFurnaceCasing extends GuiContainer {
	
	private IInventory						playerInventory;
	private TileAdvancedFurnaceCasing		tile;
	private ContainerAdvancedFurnaceCasing	container;
	private ModeButton						modeButton;
											
	public GuiAdvancedFurnaceCasing(IInventory playerInventory, TileAdvancedFurnaceCasing tile, ContainerAdvancedFurnaceCasing container) {
		super(container);
		this.container = container;
		this.playerInventory = playerInventory;
		this.tile = tile;
	}
	
	@Override
	public void initGui() {
		super.initGui();
		updateModeButton();
	}
	
	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
		GlStateManager.color(1.0F, 1.0F, 1.0F);
		this.mc.getTextureManager().bindTexture(new ResourceLocation(Reference.MOD_ID + ":" + "textures/gui/advancedFurnaceCasing.png"));
		this.drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);
		
		int i = (this.width - this.xSize) / 2;
		int j = (this.height - this.ySize) / 2;
		if (tile.shouldBeActive()) {
			this.drawTexturedModalRect(i + 81, j + 55, 176, 0, 14, 12);
		}
		
		int l = this.getCookProgressScaled(24);
		this.drawTexturedModalRect(i + 99, j + 35, 176, 60, l, 16);
		int f = this.getFuelLevelScaled(45);
		this.drawTexturedModalRect(i + 29, j + 21 + 45 - f, 176, 15, 17, f);
		drawButton(mouseX, mouseY);
	}
	
	private void drawButton(int mouseX, int mouseY) {
		modeButton.drawButton(this.mc, mouseX, mouseY);
	}
	
	@Override
	public void actionPerformed(GuiButton button) throws IOException {
		Minecraft client = FMLClientHandler.instance().getClient();
		int mouseX = Mouse.getX() * width / client.displayWidth;
		int mouseY = height - Mouse.getY() * height / client.displayHeight - 1;
		super.actionPerformed(button);
		switch (button.id) {
			case 0: {
				if (this.tile.isMaster()) {
					this.tile.toggleMode();
					Reference.CHANNEL.sendToServer(new PacketAdvancedFurnace(this.tile.getPos(), this.tile.writeToNBT(new NBTTagCompound())));
				}
				break;
			}
		}
		updateModeButton();
		drawButton(mouseX, mouseY);
	}

	private void updateModeButton() {
		int i = (this.width - this.xSize) / 2;
		int j = (this.height - this.ySize) / 2;
		this.modeButton = new ModeButton(0, i + 70, j + 16, 32, 16, tile.getModeString());
		if (this.buttonList.size() == 0) {
			this.buttonList.add(this.modeButton);
		} else {
			this.buttonList.set(0, this.modeButton);
		}
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
		String title = tile.getDisplayName().getUnformattedText();
		this.fontRendererObj.drawString(title, this.xSize / 2 - this.fontRendererObj.getStringWidth(title) / 2, 6, 4210752);
		this.fontRendererObj.drawString(this.playerInventory.getDisplayName().getUnformattedText(), 8, this.ySize - 96 + 2, 4210752);
	}
	
	private int getCookProgressScaled(int pixels) {
		int i = this.tile.getField(2);
		int j = this.tile.getField(3);
		return j != 0 && i != 0 ? i * pixels / j : 0;
	}
	
	private int getFuelLevelScaled(int pixels) {
		int i = this.tile.getField(4);
		return i != 0 ? (i * pixels / this.tile.maxFuelLevel) : 0;
	}
	
}
