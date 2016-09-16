package com.austindorff.mechanica.blocks;

import static com.austindorff.mechanica.blocks.MechanicaBlockDeclaration.blockBasicMachineCasing;
import static com.austindorff.mechanica.blocks.MechanicaBlockDeclaration.blockLeaves;
import static com.austindorff.mechanica.blocks.MechanicaBlockDeclaration.blockOres;
import static com.austindorff.mechanica.blocks.MechanicaBlockDeclaration.blockSaplings;
import static com.austindorff.mechanica.blocks.MechanicaBlockDeclaration.blockTreeLogs;

import com.austindorff.mechanica.blocks.machine.BasicSmelter;
import com.austindorff.mechanica.blocks.world.BlockMechanicaLeaves;
import com.austindorff.mechanica.blocks.world.BlockMechanicaSaplings;
import com.austindorff.mechanica.blocks.world.BlockOres;
import com.austindorff.mechanica.blocks.world.BlockTreeLogs;
import com.austindorff.mechanica.itemblocks.ItemBlockBasicSmelter;
import com.austindorff.mechanica.itemblocks.ItemBlockLeaves;
import com.austindorff.mechanica.itemblocks.ItemBlockOres;
import com.austindorff.mechanica.itemblocks.ItemBlockSapling;
import com.austindorff.mechanica.itemblocks.ItemBlockTreeLog;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.material.Material;

public class MechanicaBlocks {
	
	public static void init() {
		initBlocks();
		registerBlocks();
	}
	
	public static void initBlocks() {
		blockOres = new BlockOres().setBlockName("blockOres");
		blockTreeLogs = new BlockTreeLogs().setBlockName("blockTreeLogs");
		blockLeaves = new BlockMechanicaLeaves().setBlockName("blockLeaves");
		blockSaplings = new BlockMechanicaSaplings().setBlockName("blockSaplings");
		blockBasicMachineCasing = new BasicSmelter(Material.iron).setBlockName("blockBasicMachineCasing");
	}
	
	public static void registerBlocks() {
		GameRegistry.registerBlock(blockTreeLogs, ItemBlockTreeLog.class, "blockTreeLogs");
		GameRegistry.registerBlock(blockLeaves, ItemBlockLeaves.class, "blockLeaves");
		GameRegistry.registerBlock(blockSaplings, ItemBlockSapling.class, "blockSaplings");
		GameRegistry.registerBlock(blockOres, ItemBlockOres.class, "blockOres");
		GameRegistry.registerBlock(blockBasicMachineCasing, ItemBlockBasicSmelter.class, "blockBasicMachineCasing");
	}

}
