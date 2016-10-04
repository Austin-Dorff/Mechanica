package com.austindorff.mechanica.energy;

import net.minecraft.util.EnumFacing;

public interface IEnergyCapacitor {

	boolean isFull();
	
	boolean isEmpty();
	
	boolean isPartiallyFull();
	
	boolean canAcceptMinecraftAmperes(float minecraftAmperes);
	
	float getMinecraftAmperesOutput();
	
	boolean canConnectToEnergyNetworkInDirection(EnumFacing facing);
	
	boolean canFeedEnergyToNetworkInDirection(EnumFacing facing);
	
	boolean canRecieveEnergyFromNetworkInDirection(EnumFacing facing);
	
}
