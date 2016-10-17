package com.austindorff.mechanica.networking;

import net.minecraft.util.math.BlockPos;

public class NetworkNode {
	
	private INetworkComponent	nodeComponent;
	private Network		network;
								
	public NetworkNode(INetworkComponent component, Network network) {
		this.nodeComponent = component;
		this.network = network;
	}
	
	public Network getEnergyNetwork() {
		return network;
	}
	
	public void setHydraulicNetwork(Network hydraulicNetwork) {
		this.network = hydraulicNetwork;
	}
	
	public INetworkComponent getNodeComponent() {
		return nodeComponent;
	}
	
	public boolean equals(Object object) {
		if (object instanceof NetworkNode) {
			return this.getPosition().equals(((NetworkNode) object).getPosition());
		}
		return false;
	}
	
	private BlockPos getPosition() {
		return this.getNodeComponent().getPosition();
	}
	
}
