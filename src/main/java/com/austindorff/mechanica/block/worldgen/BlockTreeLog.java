package com.austindorff.mechanica.block.worldgen;

import com.austindorff.mechanica.Reference;
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
	
	public static final PropertyEnum<EnumFacing.Axis> AXIS = PropertyEnum.<EnumFacing.Axis> create("axis", EnumFacing.Axis.class);
	
	public BlockTreeLog(EnumLogType logType) {
		super(logType.getRegistryName(), logType.getUnlocalizedName(), logType.material, logType.hardness, logType.resistance);
		this.setSoundType(SoundType.WOOD);
		this.setDefaultState(this.getDefaultState().withProperty(AXIS, EnumFacing.Axis.Y));
	}
	
	public enum EnumLogType {
		
		RUBBER("Rubber");
		
		private String	name;
						
		public float	hardness	= 6.0F;
		public float	resistance	= 2.0F;
		public Material	material	= Material.WOOD;
									
		private EnumLogType(String name) {
			this.name = name;
		}
		
		public String getRegistryName() {
			return Reference.MOD_ID + ":" + this.getUnlocalizedName();
		}
		
		public String getUnlocalizedName() {
			return "block" + this.name + "Log";
		}
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
