package com.austindorff.mechanica.itemblock.worldgen;

import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class ItemBlockOre extends ItemBlock {
	
	public ItemBlockOre(Block block) {
		super(block);
		this.setHasSubtypes(true);
	}
	
	public String getUnlocalizedName(ItemStack itemStack) {
		return super.getUnlocalizedName();
	}
	
	public int getMetadata(int meta) {
		return meta;
	}

}
