package com.austindorff.mechanica.client.gui.component;

import net.minecraft.client.gui.GuiButton;

public class ModeButton extends GuiButton {
	
	public ModeButton(int buttonId, int x, int y, int widthIn, int heightIn, String buttonText) {
		super(buttonId, x, y, widthIn, heightIn, buttonText);
		this.visible = true;
		this.displayString = buttonText;
	}
}
