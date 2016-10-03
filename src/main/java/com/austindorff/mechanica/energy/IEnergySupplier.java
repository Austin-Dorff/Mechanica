package com.austindorff.mechanica.energy;

import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

public interface IEnergySupplier {
	
	boolean hasMinVoltage();
	
	boolean hasMaxVoltage();
	
	EnumVoltage getMinVoltageEnum();
	
	EnumVoltage getMaxVoltageEnum();
	
	EnumResistance getResistanceEnum();
	
	boolean canConnectToEnergyNetworkInDirection(EnumFacing facing);
	
	boolean canFeedEnergyToNetworkInDirection(EnumFacing facing);
}
