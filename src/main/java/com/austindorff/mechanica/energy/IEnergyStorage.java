package com.austindorff.mechanica.energy;

import com.austindorff.mechanica.network.packet.energy.PacketEnergy;

import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

public interface IEnergyStorage {

	boolean isFull();
	
	boolean isEmpty();
	
	boolean isPartiallyFull();
	
	EnumVoltage getVoltageEnum();
	
	EnumResistance getResistanceEnum();
	
	boolean canConnectToEnergyNetworkInDirection(EnumFacing facing);
	
	boolean canFeedEnergyToNetworkInDirection(EnumFacing facing);
	
	boolean canRecieveEnergyFromNetworkInDirection(EnumFacing facing);
	
	int getEnergyOutput();
}
