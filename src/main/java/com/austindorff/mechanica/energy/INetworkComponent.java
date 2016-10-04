package com.austindorff.mechanica.energy;

public interface INetworkComponent {
	
	EnergyNetwork getNetwork();
	
	void setNetwork(EnergyNetwork network);
	
	boolean doesTransferEnergy();
	
	boolean doesUseEnergy();
	
	boolean doesStoreEnergy();
	
	boolean doesProduceEnergy();

}
