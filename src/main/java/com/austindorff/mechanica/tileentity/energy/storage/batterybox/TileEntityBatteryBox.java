package com.austindorff.mechanica.tileentity.energy.storage.batterybox;

import com.austindorff.mechanica.tileentity.energy.storage.TileEntityEnergyStorageBlockBase;

import net.minecraft.util.math.BlockPos;

public class TileEntityBatteryBox extends TileEntityEnergyStorageBlockBase {

	public TileEntityBatteryBox() {
		super(40000);
	}

	@Override
	public void updateBlockState(BlockPos coords) {
		
	}

	@Override
	public int getMinecraftAmperesOutput() {
		return 32;
	}
}
