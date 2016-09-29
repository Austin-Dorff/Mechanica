package com.austindorff.mechanica.item;

import com.austindorff.mechanica.Reference;

import net.minecraft.item.Item;

public class ItemBase extends Item {
	
	public ItemBase(String name) {
		this.setCreativeTab(Reference.TAB_MECHANICA);
		this.setRegistryName(Reference.addItemRegistryName(name));
		this.setUnlocalizedName(Reference.addItemUnlocalizedName(name));
	}
}
