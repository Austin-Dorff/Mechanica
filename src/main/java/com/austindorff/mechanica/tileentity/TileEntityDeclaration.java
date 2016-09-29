package com.austindorff.mechanica.tileentity;

import com.austindorff.mechanica.tileentity.machine.TileAdvancedFurnaceCasing;

import net.minecraftforge.fml.common.registry.GameRegistry;

public class TileEntityDeclaration {
	

	public static void init() {
		registerTileEntities();
	}

	private static void registerTileEntities() {
		GameRegistry.registerTileEntity(TileAdvancedFurnaceCasing.class, "basic_machine_casing_multiblock");
	}

}
