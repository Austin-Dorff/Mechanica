package com.austindorff.mechanica.block.worldgen;

import com.austindorff.mechanica.block.BlockBase;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockTreeLog extends BlockBase {
	
	public static final PropertyEnum<EnumFacing.Axis>	AXIS			= PropertyEnum.<EnumFacing.Axis> create("axis", EnumFacing.Axis.class);
																		
	public BlockTreeLog(String name) {
		super(name, Material.WOOD, 2.0F, 0.5F);
		this.setSoundType(SoundType.WOOD);
		this.setDefaultState(this.getDefaultState().withProperty(AXIS, EnumFacing.Axis.Y));
	}
	
	@Override
	public BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, new IProperty[] { AXIS });
	}
	
	public IBlockState getStateFromMeta(int meta) {
		EnumFacing.Axis enumfacing$axis = EnumFacing.Axis.Y;
		int i = meta & 12;
		
		if (i == 4) {
			enumfacing$axis = EnumFacing.Axis.X;
		} else if (i == 8) {
			enumfacing$axis = EnumFacing.Axis.Z;
		}
		
		return this.getDefaultState().withProperty(AXIS, enumfacing$axis);
	}
	
	/**
	 * Convert the BlockState into the correct metadata value
	 */
	public int getMetaFromState(IBlockState state) {
		int i = 0;
		EnumFacing.Axis enumfacing$axis = (EnumFacing.Axis) state.getValue(AXIS);
		
		if (enumfacing$axis == EnumFacing.Axis.X) {
			i |= 4;
		} else if (enumfacing$axis == EnumFacing.Axis.Z) {
			i |= 8;
		}
		
		return i;
	}
	
	@Override
	public boolean canSustainLeaves(IBlockState state, net.minecraft.world.IBlockAccess world, BlockPos pos) {
		return true;
	}
	
	@Override
	public boolean isWood(net.minecraft.world.IBlockAccess world, BlockPos pos) {
		return true;
	}
	
	@Override
	public boolean isOpaqueCube(IBlockState state) {
		return false;
	}
}
