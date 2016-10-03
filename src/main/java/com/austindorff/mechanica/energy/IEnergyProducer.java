package com.austindorff.mechanica.energy;

import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

public interface IEnergyProducer {
	
	EnumVoltage getVoltageProducedEnum();
	
	int getCurrentProduced();
	
	EnumResistance getResistanceEnum();
	
	boolean canConnectToEnergyNetworkInDirection(EnumFacing facing);
	
	boolean canFeedEnergyToNetworkInDirection(EnumFacing facing);

}
