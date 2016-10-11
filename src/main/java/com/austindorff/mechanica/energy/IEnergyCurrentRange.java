package com.austindorff.mechanica.energy;

public interface IEnergyCurrentRange extends INetworkComponent {
	
	boolean hasMinMinecraftAmperes();
	
	boolean hasMaxMinecraftAmperes();
	
	int getMinMinecraftAmperes();
	
	int getMaxMinecraftAmperes();
}
