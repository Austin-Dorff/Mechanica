package com.austindorff.mechanica.world.gen;

import java.util.Random;

import com.austindorff.mechanica.blocks.MechanicaBlockDeclaration;

import net.minecraft.block.Block;
import net.minecraft.block.BlockSapling;
import net.minecraft.init.Blocks;
import net.minecraft.util.Direction;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenTrees;
import net.minecraftforge.common.util.ForgeDirection;

public class WorldGenRubberTree extends WorldGenTrees {
	
	private final int		minTreeHeight;
	private final boolean	vinesGrow;
	private final int		metaWood;
	private final int		metaLeaves;
							
	public WorldGenRubberTree(boolean bool) {
		this(bool, 4, 0, 0, false);
	}
	
	public WorldGenRubberTree(boolean bool, int minHeight, int woodMeta, int metaLeaves, boolean canVinesGrow) {
		super(bool, minHeight, woodMeta, metaLeaves, canVinesGrow);
		this.minTreeHeight = minHeight;
		this.metaWood = woodMeta;
		this.metaLeaves = metaLeaves;
		this.vinesGrow = canVinesGrow;
	}
	
	@Override
	public boolean generate(World world, Random rand, int posX, int posY, int posZ) {
		int l = rand.nextInt(3) + this.minTreeHeight;
		boolean flag = true;
		
		if (posY >= 1 && posY + l + 1 <= 256) {
			byte b0;
			int k1;
			Block block;
			
			for (int i1 = posY; i1 <= posY + 1 + l; ++i1) {
				b0 = 1;
				
				if (i1 == posY) {
					b0 = 0;
				}
				
				if (i1 >= posY + 1 + l - 2) {
					b0 = 2;
				}
				
				for (int j1 = posX - b0; j1 <= posX + b0 && flag; ++j1) {
					for (k1 = posZ - b0; k1 <= posZ + b0 && flag; ++k1) {
						if (i1 >= 0 && i1 < 256) {
							block = world.getBlock(j1, i1, k1);
							
							if (!this.isReplaceable(world, j1, i1, k1)) {
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
				Block block2 = world.getBlock(posX, posY - 1, posZ);
				
				boolean isSoil = block2.canSustainPlant(world, posX, posY - 1, posZ, ForgeDirection.UP, (BlockSapling) MechanicaBlockDeclaration.blockSaplings);
				if (isSoil && posY < 256 - l - 1) {
					block2.onPlantGrow(world, posX, posY - 1, posZ, posX, posY, posZ);
					b0 = 3;
					byte b1 = 0;
					int l1;
					int i2;
					int j2;
					int i3;
					
					for (k1 = 0; k1 < l; ++k1) {
						block = world.getBlock(posX, posY + k1, posZ);
						
						if (block.isAir(world, posX, posY + k1, posZ) || block.isLeaves(world, posX, posY + k1, posZ)) {
							world.setBlock(posX, posY + k1, posZ, MechanicaBlockDeclaration.blockTreeLogs, 0, 1);
						}
					}
					
					for (k1 = posY - b0 + l; k1 <= posY + l; ++k1) {
						i3 = k1 - (posY + l);
						l1 = b1 + 1 - i3 / 2;
						
						for (i2 = posX - l1; i2 <= posX + l1; ++i2) {
							j2 = i2 - posX;
							
							for (int k2 = posZ - l1; k2 <= posZ + l1; ++k2) {
								int l2 = k2 - posZ;
								
								if (Math.abs(j2) != l1 || Math.abs(l2) != l1 || rand.nextInt(2) != 0 && i3 != 0) {
									Block block1 = world.getBlock(i2, k1, k2);
									
									if (block1.isAir(world, i2, k1, k2) || block1.isLeaves(world, i2, k1, k2)) {
										world.setBlock(i2, k1, k2, Blocks.air, 0, 1);
										world.setBlock(i2, k1, k2, MechanicaBlockDeclaration.blockLeaves, 0, 1);
									}
								}
							}
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
