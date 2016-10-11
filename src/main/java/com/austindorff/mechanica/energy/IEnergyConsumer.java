package com.austindorff.mechanica.energy;

public interface IEnergyConsumer extends IEnergyCurrentRange {
	
	int getCurrentMinecraftAmperesConsumption();
	
	boolean canAcceptElectricPacket(ElectricPacket packet);
	
	void acceptEnergyPacket(ElectricPacket packet);
	
}
