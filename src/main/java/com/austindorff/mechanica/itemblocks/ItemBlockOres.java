package com.austindorff.mechanica.itemblocks;

import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class ItemBlockOres extends ItemBlock {

	final static String[] subBlocks = new String[] {"Copper", "Tin", "Silver", "Lead"};
	
	public ItemBlockOres(Block block) {
		super(block);
		this.setHasSubtypes(true);
	}
	
	public String getUnlocalizedName(ItemStack itemStack) {
		int i = itemStack.getItemDamage();
		if (i < 0 || i >= subBlocks.length) {
			i = 0;
		}
		return super.getUnlocalizedName() + "." + subBlocks[i] + "Ore";
	}
	
	public int getMetadata(int meta) {
		return meta;
	}

}
