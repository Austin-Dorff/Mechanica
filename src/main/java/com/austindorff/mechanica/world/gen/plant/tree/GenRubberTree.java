package com.austindorff.mechanica.world.gen.plant.tree;

import java.util.Random;

import com.austindorff.mechanica.block.MechanicaBlocks;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenTrees;

public class GenRubberTree extends WorldGenTrees {
	
	private int		minTreeHeight	= 6;
									
	public GenRubberTree(boolean bool) {
		super(bool);
	}
	
	public boolean generate(World worldIn, Random rand, BlockPos position) {
		int i = rand.nextInt(3) + this.minTreeHeight;
		boolean flag = true;
		
		if (position.getY() >= 1 && position.getY() + i + 1 <= worldIn.getHeight()) {
			for (int j = position.getY(); j <= position.getY() + 1 + i; ++j) {
				int k = 1;
				
				if (j == position.getY()) {
					k = 0;
				}
				
				if (j >= position.getY() + 1 + i - 2) {
					k = 2;
				}
				
				BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();
				
				for (int l = position.getX() - k; l <= position.getX() + k && flag; ++l) {
					for (int i1 = position.getZ() - k; i1 <= position.getZ() + k && flag; ++i1) {
						if (j >= 0 && j < worldIn.getHeight()) {
							if (!this.isReplaceable(worldIn, blockpos$mutableblockpos.setPos(l, j, i1))) {
								flag = false;
							}
						} else {
							flag = false;
						}
					}
				}
			}
			
			if (!flag) {
				return false;
			} else {
				IBlockState state = worldIn.getBlockState(position.down());
				
				if (state.getBlock().canSustainPlant(state, worldIn, position.down(), net.minecraft.util.EnumFacing.UP, (net.minecraft.block.BlockSapling) Blocks.SAPLING) && position.getY() < worldIn.getHeight() - i - 1) {
					this.setDirtAt(worldIn, position.down());
					
					for (int i3 = position.getY() - 3 + i; i3 <= position.getY() + i; ++i3) {
						int i4 = i3 - (position.getY() + i);
						int j1 = 1 - i4 / 2;
						
						for (int k1 = position.getX() - j1; k1 <= position.getX() + j1; ++k1) {
							int l1 = k1 - position.getX();
							
							for (int i2 = position.getZ() - j1; i2 <= position.getZ() + j1; ++i2) {
								int j2 = i2 - position.getZ();
								
								if (Math.abs(l1) != j1 || Math.abs(j2) != j1 || rand.nextInt(2) != 0 && i4 != 0) {
									BlockPos blockpos = new BlockPos(k1, i3, i2);
									state = worldIn.getBlockState(blockpos);
									
									if (state.getBlock().isAir(state, worldIn, blockpos) || state.getBlock().isLeaves(state, worldIn, blockpos) || state.getMaterial() == Material.VINE) {
										this.setBlockAndNotifyAdequately(worldIn, blockpos, MechanicaBlocks.BLOCKS.get("blockRubberTreeLeaves").getDefaultState());
									}
								}
							}
						}
					}
					
					for (int j3 = 0; j3 < i; ++j3) {
						BlockPos upN = position.up(j3);
						state = worldIn.getBlockState(upN);
						
						if (state.getBlock().isAir(state, worldIn, upN) || state.getBlock().isLeaves(state, worldIn, upN) || state.getMaterial() == Material.VINE) {
							this.setBlockAndNotifyAdequately(worldIn, position.up(j3), MechanicaBlocks.BLOCKS.get("blockRubberTreeLog").getDefaultState());
						}
					}
					return true;
				} else {
					return false;
				}
			}
		} else {
			return false;
		}
	}
}
