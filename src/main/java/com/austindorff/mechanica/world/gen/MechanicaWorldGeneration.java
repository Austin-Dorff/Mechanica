package com.austindorff.mechanica.world.gen;

import java.util.Random;

import com.austindorff.mechanica.blocks.MechanicaBlockDeclaration;

import cpw.mods.fml.common.IWorldGenerator;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.biome.BiomeGenForest;
import net.minecraft.world.biome.BiomeGenPlains;
import net.minecraft.world.biome.BiomeGenSwamp;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.feature.WorldGenMinable;

public class MechanicaWorldGeneration implements IWorldGenerator {
	
	@Override
	public void generate(Random random, int chunkX, int chunkZ, World world, IChunkProvider chunkGenerator, IChunkProvider chunkProvider) {
		switch (world.provider.dimensionId) {
			case 0:
				generateSurface(world, random, chunkX*16, chunkZ*16);
				break;			
			case 1:
				generateEnd(world, random, chunkX*16, chunkZ*16);
				break;	
			case -1:
				generateNether(world, random, chunkX*16, chunkZ*16);
				break;	
			default:
			
		}
	}
	
	private void generateSurface(World world, Random random, int x, int z) {
		
		//Copper
		this.addOreSpawn(MechanicaBlockDeclaration.blockOres, 0, world, random, x, z, 16, 16, 4 + random.nextInt(3), 22, 30, 45, 100);
		//Tin
		this.addOreSpawn(MechanicaBlockDeclaration.blockOres, 1, world, random, x, z, 16, 16, 4 + random.nextInt(3), 22, 30, 38, 75);
		//Silver
		this.addOreSpawn(MechanicaBlockDeclaration.blockOres, 2, world, random, x, z, 16, 16, 2 + random.nextInt(3), 10, 14, 10, 28);
		//Lead
		this.addOreSpawn(MechanicaBlockDeclaration.blockOres, 3, world, random, x, z, 16, 16, 4 + random.nextInt(3), 16, 27, 15, 38);
		
		//Rubber Trees
		BiomeGenBase biome = world.getWorldChunkManager().getBiomeGenAt(x, z);
		WorldGenRubberTree worldRubberTreeGen = new WorldGenRubberTree(false);
		if (biome instanceof BiomeGenPlains || biome instanceof BiomeGenForest || biome instanceof BiomeGenSwamp) {
			for (int x2 = 0; x2 < 4; x2++) {
				int xPos = (x + random.nextInt(16) + 8);
				int zPos = (z + random.nextInt(16) + 8);
				worldRubberTreeGen.generate(world, random, xPos, world.getHeightValue(xPos, zPos), zPos);
			}
		}
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
			(new WorldGenMinable(block, meta, maxVeinSize, Blocks.stone)).generate(world, random, posX, posY, posZ);
		}
	}
	
}
