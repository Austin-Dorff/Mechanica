package com.austindorff.mechanica.block;

import com.austindorff.mechanica.Reference;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumBlockRenderType;

public abstract class BlockBase extends Block {

	public BlockBase(String regName, String unlocName, Material material, float hardness, float resistance) {
		super(material);
		this.setUnlocalizedName(unlocName);
		this.setRegistryName(regName);
        this.setCreativeTab(Reference.TAB_MECHANICA);
        this.setHardness(hardness);
        this.setResistance(resistance);
	}
	
	@Override
	public abstract boolean isOpaqueCube(IBlockState state);

	@Override
	public EnumBlockRenderType getRenderType(IBlockState state) {
		return EnumBlockRenderType.MODEL;
	}
}
