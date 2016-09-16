package com.austindorff.mechanica.itemblocks;

import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class ItemBlockSapling extends ItemBlock {

	public ItemBlockSapling(Block block) {
		super(block);
	}
	
	public String getUnlocalizedName(ItemStack itemStack) {
		return super.getUnlocalizedName() + "." + "RubberTreeSapling";
	}

}