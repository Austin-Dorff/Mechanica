package com.austindorff.mechanica.energy;

import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

public interface IEnergyConsumer {
	
	boolean hasMinVoltage();
	
	boolean hasMaxVoltage();
	
	EnumVoltage getMinVoltage();

	EnumVoltage getMaxVoltage();
	
	EnumResistance getResistance();
	
	int getCurrentEnergyConsumption();
	
	EnumVoltage getCurrentVoltageRecieving();
	
	boolean canConnectToEnergyNetworkInDirection(EnumFacing facing);
	
	boolean canRecieveEnergyFromNetworkInDirection(EnumFacing facing);
	
}
