package com.austindorff.mechanica.energy;

import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

public interface IEnergyProducer {
	
	float getMinecraftAmperesProduced();
		
	boolean canConnectToEnergyNetworkInDirection(EnumFacing facing);
	
	boolean canFeedEnergyToNetworkInDirection(EnumFacing facing);

}
