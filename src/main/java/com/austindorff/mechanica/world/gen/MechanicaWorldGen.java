package com.austindorff.mechanica.world.gen;

import java.util.Random;

import com.austindorff.mechanica.Reference;
import com.austindorff.mechanica.block.MechanicaBlocks;
import com.austindorff.mechanica.block.worldgen.BlockOre;

import net.minecraft.block.Block;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkGenerator;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.feature.WorldGenMinable;
import net.minecraftforge.fml.common.IWorldGenerator;

public class MechanicaWorldGen implements IWorldGenerator {
	
	@Override
	public void generate(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider) {
		switch (world.provider.getDimension()) {
			case 0:
				generateSurface(world, random, chunkX * 16, chunkZ * 16);
				break;
			case 1:
				generateEnd(world, random, chunkX * 16, chunkZ * 16);
				break;
			case -1:
				generateNether(world, random, chunkX * 16, chunkZ * 16);
				break;
			default:
			
		}
		
	}
	
	private void generateSurface(World world, Random random, int x, int z) {
		this.addOreSpawn(MechanicaBlocks.BLOCKS.get(Reference.ORE_TYPES[0]), 0, world, random, x, z, 16, 16, 4 + random.nextInt(3), 22, 30, 45, 100);
		this.addOreSpawn(MechanicaBlocks.BLOCKS.get(Reference.ORE_TYPES[1]), 0, world, random, x, z, 16, 16, 4 + random.nextInt(3), 22, 30, 38, 75);
		this.addOreSpawn(MechanicaBlocks.BLOCKS.get(Reference.ORE_TYPES[2]), 0, world, random, x, z, 16, 16, 2 + random.nextInt(3), 10, 14, 10, 28);
		this.addOreSpawn(MechanicaBlocks.BLOCKS.get(Reference.ORE_TYPES[3]), 0, world, random, x, z, 16, 16, 4 + random.nextInt(3), 16, 27, 15, 38);
		
		// Rubber Trees
//		if (!(world.getWorldType() == WorldType.FLAT)) {
//			BiomeCache biome = world.getWorldBorder().getBiomeGenerator(new BlockPos(x, 10, z));
//			GenRubberTree worldRubberTreeGen = new GenRubberTree(false, BlockTreeLog.getStateById(0), BlockTreeLeaves.getStateById(0));
//			if (biome instanceof BiomeGenPlains || biome instanceof BiomeGenForest || biome instanceof BiomeGenSwamp) {
//				for (int x2 = 0; x2 < 4; x2++) {
//					int xPos = (x + random.nextInt(16) + 8);
//					int zPos = (z + random.nextInt(16) + 8);
//					worldRubberTreeGen.generate(world, random, new BlockPos(xPos, world.getChunksLowestHorizon(xPos, zPos), zPos));
//				}
//			}
//		}
	}
	
	private void generateNether(World world, Random random, int x, int z) {
	
	}
	
	private void generateEnd(World world, Random random, int x, int z) {
	
	}
	
	private void addOreSpawn(Block block, int meta, World world, Random random, int blockXPos, int blockZPos, int maxXPos, int maxZPos, int maxVeinSize, int minChanceToSpawn, int maxChanceToSpawn, int minYPos, int maxYPos) {
		for (int i = 0; i < (minChanceToSpawn + random.nextInt(maxChanceToSpawn - minChanceToSpawn)); i++) {
			int posX = blockXPos + random.nextInt(maxXPos);
			int posY = minYPos + random.nextInt(maxYPos - minYPos);
			int posZ = blockZPos + random.nextInt(maxZPos);
			(new WorldGenMinable(((BlockOre) block).getStateFromMeta(meta), maxVeinSize)).generate(world, random, new BlockPos(posX, posY, posZ));
		}
	}
	
}
