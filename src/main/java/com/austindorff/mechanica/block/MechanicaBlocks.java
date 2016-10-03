package com.austindorff.mechanica.block;

import java.util.HashMap;
import java.util.Map;

import com.austindorff.mechanica.Reference;
import com.austindorff.mechanica.block.machine.BlockAdvancedFurnaceCasing;
import com.austindorff.mechanica.block.wire.BlockWireBase;
import com.austindorff.mechanica.block.worldgen.BlockOre;
import com.austindorff.mechanica.block.worldgen.BlockTreeLeaves;
import com.austindorff.mechanica.block.worldgen.BlockTreeLog;
import com.austindorff.mechanica.block.worldgen.BlockTreeSapling;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
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
		MechanicaBlocks.BLOCKS.put(BlockAdvancedFurnaceCasing.NAME, new BlockAdvancedFurnaceCasing());
		MechanicaBlocks.BLOCKS.put(BlockWireBase.EnumWireType.COPPER.getUnlocalizedName(), new BlockWireBase(BlockWireBase.EnumWireType.COPPER.getRegistryName(), BlockWireBase.EnumWireType.COPPER.getUnlocalizedName(), Material.ROCK, 0.5F, 0.5F));
	}

	private static void initSubBlockClasses() {
		// Ores
		for (BlockOre.EnumOreType oreType : BlockOre.EnumOreType.values()) {
			MechanicaBlocks.BLOCKS.put(oreType.getUnlocalizedName(), new BlockOre(oreType));
		}
		// Tree Logs
		for (BlockTreeLog.EnumLogType logType : BlockTreeLog.EnumLogType.values()) {
			MechanicaBlocks.BLOCKS.put(logType.getUnlocalizedName(), new BlockTreeLog(logType));
		}
		// Tree Leaves
		for (BlockTreeLeaves.EnumLeafType leafType : BlockTreeLeaves.EnumLeafType.values()) {
			MechanicaBlocks.BLOCKS.put(leafType.getUnlocalizedName(), new BlockTreeLeaves(leafType));
		}
		// Tree Saplings
		for (BlockTreeSapling.EnumSaplingType saplingType : BlockTreeSapling.EnumSaplingType.values()) {
			MechanicaBlocks.BLOCKS.put(saplingType.getUnlocalizedName(), new BlockTreeSapling(saplingType));
		}
	}
	
}
