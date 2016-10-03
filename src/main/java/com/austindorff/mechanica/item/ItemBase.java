package com.austindorff.mechanica.item;

import com.austindorff.mechanica.Reference;

import net.minecraft.item.Item;

public class ItemBase extends Item {
	
	public ItemBase(String regName, String unlocName) {
		this.setCreativeTab(Reference.TAB_MECHANICA);
		this.setRegistryName(regName);
		this.setUnlocalizedName(unlocName);
	}
}
