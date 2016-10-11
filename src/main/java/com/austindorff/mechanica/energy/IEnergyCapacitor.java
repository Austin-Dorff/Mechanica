package com.austindorff.mechanica.energy;

public interface IEnergyCapacitor extends INetworkComponent {

	boolean isFull();
	
	boolean isEmpty();
	
	boolean isPartiallyFull();
	
	void acceptEnergyPacket(ElectricPacket packet);
	
	boolean canAcceptElectricPacket(ElectricPacket packet);
	
	int getMinecraftAmperesOutput();
	
}
