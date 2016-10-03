package com.austindorff.mechanica.tileentity.energy.storage.batterybox;

import com.austindorff.mechanica.energy.EnumResistance;
import com.austindorff.mechanica.energy.EnumVoltage;
import com.austindorff.mechanica.tileentity.energy.storage.TileEntityEnergyStorageBlockBase;

import net.minecraft.util.math.BlockPos;

public class TileEntityBatteryBox extends TileEntityEnergyStorageBlockBase {

	public TileEntityBatteryBox() {
		super(40000);
	}

	@Override
	public EnumVoltage getVoltageEnum() {
		return EnumVoltage.TIER_ONE;
	}

	@Override
	public EnumResistance getResistanceEnum() {
		return EnumResistance.TIER_TWO;
	}

	@Override
	public void updateBlockState(BlockPos coords) {
		
	}

}
