package com.austindorff.mechanica.block;

import java.util.HashMap;
import java.util.Map;

import com.austindorff.mechanica.Reference;
import com.austindorff.mechanica.block.machine.AdvancedFurnaceCasing;
import com.austindorff.mechanica.block.worldgen.BlockOre;
import com.austindorff.mechanica.block.worldgen.BlockTreeLeaves;
import com.austindorff.mechanica.block.worldgen.BlockTreeLog;
import com.austindorff.mechanica.block.worldgen.BlockTreeSapling;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class MechanicaBlocks {
	
	public static Map<String, Block> BLOCKS = new HashMap<String, Block>();
	
	public static void init() {
		initSubBlockClasses();
		initSingletonBlockClasses();
		registerBlocks();
	}
	
	public static void registerBlocks() {
		for (Block block : BLOCKS.values()) {
			GameRegistry.register(block);
			GameRegistry.register(new ItemBlock(block).setRegistryName(block.getRegistryName()));
		}
	}
	
	public static void registerRenderers() {
		for (Block block : BLOCKS.values()) {
			ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(block), 0, new ModelResourceLocation(block.getRegistryName(), "inventory"));
		}
	}
	
	private static void initSingletonBlockClasses() {
		MechanicaBlocks.BLOCKS.put(Reference.ADVANCED_FURNACE_CASING, new AdvancedFurnaceCasing(Reference.ADVANCED_FURNACE_CASING));		
	}

	private static void initSubBlockClasses() {
		// Ores
		for (String oreName : Reference.ORE_TYPES) {
			MechanicaBlocks.BLOCKS.put(oreName, new BlockOre(oreName));
		}
		// Tree Logs
		for (String treeLogName : Reference.TREE_LOG_TYPES) {
			MechanicaBlocks.BLOCKS.put(treeLogName, new BlockTreeLog(treeLogName));
		}
		// Tree Leaves
		for (String treeLeafName : Reference.TREE_LEAVES_TYPES) {
			MechanicaBlocks.BLOCKS.put(treeLeafName, new BlockTreeLeaves(treeLeafName));
		}
		// Tree Saplings
		for (String treeSaplingName : Reference.TREE_SAPLING_TYPES) {
			MechanicaBlocks.BLOCKS.put(treeSaplingName, new BlockTreeSapling(treeSaplingName));
		}
	}
	
	private static String getBlockName(String typeName, String suffix) {
		return "block" + typeName + suffix;
	}
	
}
