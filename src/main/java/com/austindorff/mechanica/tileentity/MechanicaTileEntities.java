package com.austindorff.mechanica.tileentity;

import com.austindorff.mechanica.tileentity.machine.TileMultiblockBasicSmelter;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.tileentity.TileEntity;

public class MechanicaTileEntities {
	

	public static void init() {
		registerTileEntities();
	}

	private static void registerTileEntities() {
		GameRegistry.registerTileEntity(TileMultiblockBasicSmelter.class, "basic_machine_casing_multiblock");
	}

}
