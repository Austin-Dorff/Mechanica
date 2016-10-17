package com.austindorff.mechanica.networking;

import java.util.ArrayList;

import net.minecraft.util.math.BlockPos;

public interface INetworkComponent {
	
	boolean doesBelongToNetwork(Network network);
		
	ArrayList<INetworkComponent> getNeighbors();
	
	boolean hasNetworkConnectionInDirection(EnumDirection direction);
	
	Network getNetworkInDirection(EnumDirection direction);
	
	ArrayList<Network> getNetworks();
	
	void setNetworkInDirection(Network network, EnumDirection direction);
		
	BlockPos getPosition();

	ArrayList<INetworkComponent> getNeighboringNetworkingPipes();
	
}
