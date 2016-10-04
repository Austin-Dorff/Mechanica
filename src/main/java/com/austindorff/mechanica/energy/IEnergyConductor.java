package com.austindorff.mechanica.energy;

import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

public interface IEnergyConductor {
		
	boolean hasMinMinecraftAmperes();
	
	boolean hasMaxMinecraftAmperes();
	
	float getMinMinecraftAmperes();
	
	float getMaxMinecraftAmperes();
	
	boolean canConnectToEnergyNetworkInDirection(EnumFacing facing);
	
	boolean canFeedEnergyToNetworkInDirection(EnumFacing facing);
}
