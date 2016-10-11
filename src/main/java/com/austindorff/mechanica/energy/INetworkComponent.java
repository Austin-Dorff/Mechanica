package com.austindorff.mechanica.energy;

import java.util.ArrayList;

import net.minecraft.util.math.BlockPos;

public interface INetworkComponent {
	
	boolean doesBelongToNetwork(EnergyNetwork network);
		
	ArrayList<INetworkComponent> getNeighbors();
	
	boolean doesTransferEnergy();
	
	boolean doesUseEnergy();
	
	boolean doesStoreEnergy();
	
	boolean doesProduceEnergy();
	
	boolean doesOutputEnergy();
	
	boolean hasNetworkConnectionInDirection(EnumDirection direction);
	
	boolean canFeedEnergyToNetworkInDirection(EnumDirection direction);
	
	boolean canRecieveEnergyFromNetworkConnectionInDirection(EnumDirection direction);
	
	EnergyNetwork getEnergyNetworkInDirection(EnumDirection direction);
	
	ArrayList<EnergyNetwork> getEnergyNetworks();
	
	void setEnergyNetworkInDirection(EnergyNetwork network, EnumDirection direction);
		
	BlockPos getPosition();
	
}
