package com.austindorff.mechanica.tileentity;

import com.austindorff.mechanica.Reference;
import com.austindorff.mechanica.tileentity.advancedfurnace.TileEntityAdvancedFurnaceCasing;
import com.austindorff.mechanica.tileentity.networking.pipe.TileNetworkingPipeBase;
import com.austindorff.mechanica.tileentity.networking.pipe.stone.TileStoneNetworkingPipe;

import net.minecraftforge.fml.common.registry.GameRegistry;

public class TileDeclaration {
	

	public static void init() {
		registerTileEntities();
	}

	private static void registerTileEntities() {
		GameRegistry.registerTileEntity(TileEntityAdvancedFurnaceCasing.class, Reference.MOD_ID + ":" + "basic_machine_casing_multiblock");
		GameRegistry.registerTileEntity(TileNetworkingPipeBase.class, Reference.MOD_ID + ":" + "tile_entity_networking_pipe");
		GameRegistry.registerTileEntity(TileStoneNetworkingPipe.class, Reference.MOD_ID + ":" + "tile_entity_stone_networking_pipe");
	}

}
