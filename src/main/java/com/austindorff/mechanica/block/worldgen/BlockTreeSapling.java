package com.austindorff.mechanica.block.worldgen;

import java.util.Random;

import com.austindorff.mechanica.Reference;
import com.austindorff.mechanica.world.gen.plant.tree.GenRubberTree;

import net.minecraft.block.BlockBush;
import net.minecraft.block.IGrowable;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

public class BlockTreeSapling extends BlockBush implements IGrowable {
	
	public static final PropertyInteger		STAGE	= PropertyInteger.create("stage", 0, 1);
													
	public BlockTreeSapling(EnumSaplingType saplingType) {
		this.setUnlocalizedName(saplingType.getUnlocalizedName());
		this.setRegistryName(saplingType.getRegistryName());
		this.setHardness(saplingType.hardness);
		this.setResistance(saplingType.resistance);
		this.setSoundType(SoundType.PLANT);
		this.setCreativeTab(Reference.TAB_MECHANICA);
	}
	
	public enum EnumSaplingType {
		
		RUBBER("Rubber");
		
		private String	name;
						
		public float	hardness	= 1.0F;
		public float	resistance	= 1.0F;
		public Material	material	= Material.PLANTS;
									
		private EnumSaplingType(String name) {
			this.name = name;
		}
		
		public String getRegistryName() {
			return Reference.MOD_ID + ":" + this.getUnlocalizedName();
		}
		
		public String getUnlocalizedName() {
			return "block" + this.name + "Sapling";
		}
	}
	
	@Override
	public BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, new IProperty[] { STAGE });
	}
	
	@Override
	public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
		if (!worldIn.isRemote) {
			super.updateTick(worldIn, pos, state, rand);
			
			if (worldIn.getLightFromNeighbors(pos.up()) >= 9 && rand.nextInt(7) == 0) {
				this.grow(worldIn, pos, state, rand);
			}
		}
	}
	
	public void grow(World worldIn, BlockPos pos, IBlockState state, Random rand) {
		if (((Integer) state.getValue(STAGE)).intValue() == 0) {
			worldIn.setBlockState(pos, state.cycleProperty(STAGE), 4);
		} else {
			this.generateTree(worldIn, pos, state, rand);
		}
	}
	
	public void generateTree(World worldIn, BlockPos pos, IBlockState state, Random rand) {
		if (!net.minecraftforge.event.terraingen.TerrainGen.saplingGrowTree(worldIn, rand, pos))
			return;
		WorldGenerator worldgenerator = (WorldGenerator) (new GenRubberTree(true));
		int i = 0;
		int j = 0;
		boolean flag = false;
		
		IBlockState iblockstate2 = Blocks.AIR.getDefaultState();
		
		if (!worldgenerator.generate(worldIn, rand, pos.add(i, 0, j))) {
			if (flag) {
				worldIn.setBlockState(pos.add(i, 0, j), state, 4);
				worldIn.setBlockState(pos.add(i + 1, 0, j), state, 4);
				worldIn.setBlockState(pos.add(i, 0, j + 1), state, 4);
				worldIn.setBlockState(pos.add(i + 1, 0, j + 1), state, 4);
			} else {
				worldIn.setBlockState(pos, state, 4);
			}
		}
	}
	
	@Override
	public IBlockState getStateFromMeta(int meta) {
		return this.getDefaultState().withProperty(STAGE, meta);
	}
	
	@Override
	public int getMetaFromState(IBlockState state) {
		return ((Integer) state.getValue(STAGE)).intValue();
	}
	
	@Override
	public boolean isOpaqueCube(IBlockState state) {
		return false;
	}
	
	public boolean canGrow(World worldIn, BlockPos pos, IBlockState state, boolean isClient) {
		return true;
	}
	
	public boolean canUseBonemeal(World worldIn, Random rand, BlockPos pos, IBlockState state) {
		return (double) worldIn.rand.nextFloat() < 0.45D;
	}
	
	public void grow(World worldIn, Random rand, BlockPos pos, IBlockState state) {
		this.grow(worldIn, pos, state, rand);
	}
}
