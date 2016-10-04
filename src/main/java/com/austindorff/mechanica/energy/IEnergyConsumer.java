package com.austindorff.mechanica.energy;

import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

public interface IEnergyConsumer {
	
	boolean hasMinMinecraftAmperes();
	
	boolean hasMaxMinecraftAmperes();
	
	float getMinMinecraftAmperes();

	float getMaxMinecraftAmperes();
	
	float getCurrentMinecraftAmperesConsumption();
	
	boolean canConnectToEnergyNetworkInDirection(EnumFacing facing);
	
	boolean canRecieveEnergyFromNetworkInDirection(EnumFacing facing);
	
}
