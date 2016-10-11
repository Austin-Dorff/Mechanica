package com.austindorff.mechanica.energy;

import net.minecraft.util.math.BlockPos;

public class NetworkNode {
	
	private INetworkComponent	nodeComponent;
	private EnergyNetwork		energyNetwork;
								
	public NetworkNode(INetworkComponent component, EnergyNetwork network) {
		this.nodeComponent = component;
		this.energyNetwork = network;
	}
	
	public EnergyNetwork getEnergyNetwork() {
		return energyNetwork;
	}
	
	public void setEnergyNetwork(EnergyNetwork energyNetwork) {
		this.energyNetwork = energyNetwork;
	}
	
	public INetworkComponent getNodeComponent() {
		return nodeComponent;
	}
	
	public boolean doesTransferEnergy() {
		return getNodeComponent().doesTransferEnergy();
	}
	
	public boolean doesUseEnergy() {
		return getNodeComponent().doesUseEnergy();
	}
	
	public boolean doesProduceEnergy() {
		return getNodeComponent().doesProduceEnergy();
	}
	
	public boolean doesStoreEnergy() {
		return getNodeComponent().doesStoreEnergy();
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
