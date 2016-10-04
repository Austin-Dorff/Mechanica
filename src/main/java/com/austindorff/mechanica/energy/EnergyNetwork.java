package com.austindorff.mechanica.energy;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.austindorff.mechanica.tileentity.energy.storage.batterybox.TileEntityBatteryBox;

public class EnergyNetwork {
	
	private Map<INetworkComponent, Boolean>			doesComponentTransferEnergy	= new HashMap<INetworkComponent, Boolean>();
	private Map<INetworkComponent, Boolean>			doesComponentStoreEnergy	= new HashMap<INetworkComponent, Boolean>();
	private Map<INetworkComponent, Boolean>			doesComponentProduceEnergy	= new HashMap<INetworkComponent, Boolean>();
	private Map<INetworkComponent, Boolean>			doesComponentUseEnergy		= new HashMap<INetworkComponent, Boolean>();
																				
	private Map<INetworkComponent, EnergyNetNode>	networkComponentsToNodes	= new HashMap<INetworkComponent, EnergyNetNode>();
	private Map<EnergyNetNode, INetworkComponent>	nodesToNetworkComponents	= new HashMap<EnergyNetNode, INetworkComponent>();
	private Map<EnergyNetNode, EnergyNetNode>		networkNodeNeighbors		= new HashMap<EnergyNetNode, EnergyNetNode>();
	private Map<EnergyNetNode, EnergyNetNode>		reverseNetworkNodeNeighbors	= new HashMap<EnergyNetNode, EnergyNetNode>();
	private List<EnergyNetNode>						networkNodes				= new ArrayList<EnergyNetNode>();
																				
	public EnergyNetwork() {
	}
	
	public static void integrate(INetworkComponent component, List<INetworkComponent> neighbors) {
		EnergyNetwork otherNetwork = null;
		for (INetworkComponent neighbor : neighbors) {
			if (neighbor.getNetwork() != null) {
				otherNetwork = neighbor.getNetwork();
				break;
			}
		}
		if (otherNetwork != null) {
			otherNetwork.add(component, neighbors);
		}
	}
	
	public void add(INetworkComponent component, Iterable<INetworkComponent> neighbors) {
		if (component.getNetwork() == null) {
			component.setNetwork(this);
			EnergyNetNode node = new EnergyNetNode(this, component);
			this.networkComponentsToNodes.put(component, node);
			this.nodesToNetworkComponents.put(node, component);
			this.networkNodes.add(node);
			this.doesComponentProduceEnergy.put(component, component.doesProduceEnergy());
			this.doesComponentUseEnergy.put(component, component.doesUseEnergy());
			this.doesComponentStoreEnergy.put(component, component.doesStoreEnergy());
			this.doesComponentTransferEnergy.put(component, component.doesTransferEnergy());
			addNeighors(component, neighbors);
		}
	}
	
	public void addNeighors(INetworkComponent component, Iterable<INetworkComponent> neighbors) {
		EnergyNetNode node = this.networkComponentsToNodes.get(component);
		for (INetworkComponent neighbor : neighbors) {
			if (this != neighbor.getNetwork()) {
				this.mergeWith(neighbor.getNetwork());
				EnergyNetNode nodeNeighbor = this.networkComponentsToNodes.get(neighbor);
				this.networkNodeNeighbors.put(node, nodeNeighbor);
				this.reverseNetworkNodeNeighbors.put(nodeNeighbor, node);
			}
		}
	}
	
	public void removeNeighbor(INetworkComponent component, INetworkComponent neighbor) {
	
	}
	
	private void splitAfterRemoval() {
		
	}
	
	private void mergeWith(EnergyNetwork net) {
		
	}
	
	public void remove(INetworkComponent component) {
	
	}
	
	public Collection<INetworkComponent> getComponents() {
		return null;
	}
	
	public Collection<INetworkComponent> getNeighbors(INetworkComponent component) {
		return this.nodesToNetworkComponents.values();
	}
	
	public boolean contains(INetworkComponent component) {
		return this.networkComponentsToNodes.get(component) != null;
	}
	
	public boolean canAcceptPacket(ElectricPacket packet) {
		Iterator<INetworkComponent> itter = this.getComponents().iterator();
		while (itter.hasNext()) {
			INetworkComponent component = itter.next();
			if (component instanceof TileEntityBatteryBox && ((TileEntityBatteryBox) component).canAcceptElectricPacket(packet)) {
				return true;
			}
		}
		return false;
	}
	
	public void injectPacket(ElectricPacket packet) {
		Iterator<INetworkComponent> itter = this.getComponents().iterator();
		while (itter.hasNext()) {
			INetworkComponent component = itter.next();
			if (component instanceof TileEntityBatteryBox) {
				((TileEntityBatteryBox) component).recieveElectricPacket(packet);
			}
		}
	}
	
	private class EnergyNetNode {
		
		private EnergyNetwork		network;
		private INetworkComponent	component;
									
		public EnergyNetNode(EnergyNetwork network, INetworkComponent component) {
			this.network = network;
			this.component = component;
		}
		
		public INetworkComponent getComponent() {
			return this.component;
		}
		
		public EnergyNetwork getNetwork() {
			return this.network;
		}
		
	}
	
}
