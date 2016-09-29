package com.austindorff.mechanica.block.worldgen;

import com.austindorff.mechanica.block.BlockBase;
import com.austindorff.mechanica.block.MechanicaBlocks;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumBlockRenderType;

public class BlockOre extends BlockBase {
	
	public BlockOre(String name) {
		super(name, Material.ROCK, 5.0F, 3.0F);
	}

	@Override
	public boolean isOpaqueCube(IBlockState state) {
		return false;
	}
	
}
