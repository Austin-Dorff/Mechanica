package com.austindorff.mechanica.client.gui.component;

import com.austindorff.mechanica.Reference;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.util.ResourceLocation;

public class ModeButton extends GuiButton {
	
	public ModeButton(int buttonId, int x, int y, int widthIn, int heightIn, String buttonText) {
		super(buttonId, x, y, widthIn, heightIn, buttonText);
		this.visible = true;
		this.displayString = buttonText;
	}
	
	public void drawButton(Minecraft mc, int mouseX, int mouseY) {
		super.drawButton(mc, mouseX, mouseY);
		if (this.visible) {
			FontRenderer fontrenderer = mc.fontRendererObj;
			int l = 14737632;
			if (packedFGColour != 0) {
				l = packedFGColour;
			} else if (!this.enabled) {
				l = 10526880;
			} else if (this.hovered) {
				l = 16777120;
			}
			
			this.drawCenteredString(fontrenderer, this.displayString, this.xPosition + this.width / 2, this.yPosition + (this.height - 8) / 2, l);
		}
	}
	
}
