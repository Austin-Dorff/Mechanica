package com.austindorff.mechanica.energy;

import java.util.ArrayList;
import java.util.HashMap;

public class EnergyNetwork {
	
	private INetworkComponent						masterComponent;
													
	private ArrayList<NetworkNode>					networkNodes		= new ArrayList<NetworkNode>();
	private ArrayList<INetworkComponent>			networkComponents	= new ArrayList<INetworkComponent>();
																		
	private HashMap<NetworkNode, INetworkComponent>	nodeToComponent		= new HashMap<NetworkNode, INetworkComponent>();
	private HashMap<INetworkComponent, NetworkNode>	componentToNode		= new HashMap<INetworkComponent, NetworkNode>();
																		
	public EnergyNetwork() {
	
	}
	
	public EnergyNetwork(INetworkComponent masterComponent) {
		this.masterComponent = masterComponent;
		addNodeToNetwork(masterComponent);
	}
	
	public EnergyNetwork(HashMap<INetworkComponent, NetworkNode> componentToNode, HashMap<NetworkNode, INetworkComponent> nodeToComponent, INetworkComponent masterComponent) {
		this.componentToNode = componentToNode;
		this.nodeToComponent = nodeToComponent;
		this.masterComponent = masterComponent;
		for (INetworkComponent component : this.nodeToComponent.values()) {
			component.setEnergyNetworkInDirection(this, EnumDirection.ALL);
		}
	}
	
	public static EnergyNetwork incorporateComponent(INetworkComponent newComponent) {
		ArrayList<INetworkComponent> neighbors = newComponent.getNeighbors();
		ArrayList<EnergyNetwork> neighboringEnergyNetworks = new ArrayList<EnergyNetwork>();
		for (INetworkComponent neighbor : neighbors) {
			if (neighbor != null) {
				EnergyNetwork neighborNet = neighbor.getEnergyNetworkInDirection(EnumDirection.ALL);
				if ((neighbor != null) && (neighbor instanceof IEnergyConductor) && (neighborNet != null)) {
					if (!neighboringEnergyNetworks.contains(neighborNet)) {
						neighboringEnergyNetworks.add(neighborNet);
					}
				}
			}
		}
		EnergyNetwork newComponentNet = new EnergyNetwork(newComponent);
		newComponent.setEnergyNetworkInDirection(newComponentNet, EnumDirection.ALL);
		neighboringEnergyNetworks.add(new EnergyNetwork(newComponent));
		if (neighboringEnergyNetworks.size() > 1) {
			return mergeNetworks(neighboringEnergyNetworks);
		}
		return newComponentNet;
	}
	
	public void removeComponent(INetworkComponent componentToRemove) {
		NetworkNode nodeToRemove = this.componentToNode.get(componentToRemove);
		if (nodeToRemove != null) {
			this.nodeToComponent.remove(nodeToRemove, componentToRemove);
			this.networkNodes.remove(nodeToRemove);
			this.componentToNode.remove(componentToRemove, nodeToRemove);
			this.networkComponents.remove(componentToRemove);
			split(componentToRemove);
		}
	}
	
	private void split(INetworkComponent componentToRemove) {
		for (INetworkComponent neighbor : componentToRemove.getNeighbors()) {
			if (neighbor != null) {
				neighbor.setEnergyNetworkInDirection(new EnergyNetwork(neighbor), EnumDirection.ALL);
			}
		}
		EnergyNetwork.recreate(this);
	}
	
	private static void recreate(EnergyNetwork energyNetwork) {
		ArrayList<INetworkComponent> alreadySeen = new ArrayList<INetworkComponent>();
		for (INetworkComponent component : energyNetwork.nodeToComponent.values()) {
			if (!alreadySeen.contains(component)) {
				EnergyNetwork newNet = EnergyNetwork.incorporateComponent(component);
				component.setEnergyNetworkInDirection(newNet, EnumDirection.ALL);
				alreadySeen.add(component);
			}
		}
	}
	
	private static EnergyNetwork mergeNetworks(ArrayList<EnergyNetwork> energyNets) {
		HashMap<INetworkComponent, NetworkNode> componentToNodeNew = new HashMap<INetworkComponent, NetworkNode>();
		HashMap<NetworkNode, INetworkComponent> nodeToComponentNew = new HashMap<NetworkNode, INetworkComponent>();
		for (EnergyNetwork net : energyNets) {
			componentToNodeNew.putAll(net.componentToNode);
			nodeToComponentNew.putAll(net.nodeToComponent);
		}
		EnergyNetwork newNet = new EnergyNetwork(componentToNodeNew, nodeToComponentNew, energyNets.get(0).getMasterComponent());
		for (INetworkComponent component : newNet.nodeToComponent.values()) {
			component.setEnergyNetworkInDirection(newNet, EnumDirection.ALL);
		}
		return newNet;
	}
	
	public INetworkComponent getMasterComponent() {
		return masterComponent;
	}
	
	public void setMasterComponent(INetworkComponent masterComponent) {
		this.masterComponent = masterComponent;
	}
	
	private NetworkNode addNodeToNetwork(INetworkComponent component) {
		NetworkNode newNode = new NetworkNode(component, this);
		this.networkNodes.add(newNode);
		this.networkComponents.add(component);
		this.nodeToComponent.put(newNode, component);
		this.componentToNode.put(component, newNode);
		return newNode;
	}
	
	private ArrayList<IEnergyCapacitor> getEnergyCapacitorBlocksOnNetwork() {
		ArrayList<IEnergyCapacitor> capacitors = new ArrayList<IEnergyCapacitor>();
		for (INetworkComponent component : this.nodeToComponent.values()) {
			for (INetworkComponent neighbor : component.getNeighbors()) {
				if (neighbor instanceof IEnergyCapacitor && !capacitors.contains(neighbor)) {
					capacitors.add(((IEnergyCapacitor) neighbor));
				}
			}
		}
		return capacitors;
	}
	
	private ArrayList<IEnergyProducer> getEnergyProducerBlocksOnNetwork() {
		ArrayList<IEnergyProducer> producers = new ArrayList<IEnergyProducer>();
		for (INetworkComponent component : this.nodeToComponent.values()) {
			for (INetworkComponent neighbor : component.getNeighbors()) {
				if (neighbor instanceof IEnergyProducer && !producers.contains(neighbor)) {
					producers.add(((IEnergyProducer) neighbor));
				}
			}
		}
		return producers;
	}
	
	private ArrayList<IEnergyConsumer> getEnergyConsumerBlocksOnNetwork() {
		ArrayList<IEnergyConsumer> consumers = new ArrayList<IEnergyConsumer>();
		for (INetworkComponent component : this.nodeToComponent.values()) {
			for (INetworkComponent neighbor : component.getNeighbors()) {
				if (neighbor instanceof IEnergyConsumer && !consumers.contains(neighbor)) {
					consumers.add(((IEnergyConsumer) neighbor));
				}
			}
		}
		return consumers;
	}
	
	public boolean canAcceptEnergyPacket(ElectricPacket packet) {
		if (this.getEnergyCapacitorBlocksOnNetwork().size() != 0 || this.getEnergyConsumerBlocksOnNetwork().size() != 0) {
			for (IEnergyCapacitor capacitor : this.getEnergyCapacitorBlocksOnNetwork()) {
				if (capacitor.canAcceptElectricPacket(packet)) {
					return true;
				}
			}
			for (IEnergyConsumer consumer : this.getEnergyConsumerBlocksOnNetwork()) {
				if (consumer.canAcceptElectricPacket(packet)) {
					return true;
				}
			}
		}
		return false;
	}
	
	public void injectEnergyPacket(ElectricPacket packet) {
		if (this.getEnergyCapacitorBlocksOnNetwork().size() != 0 || this.getEnergyConsumerBlocksOnNetwork().size() != 0) {
			for (IEnergyCapacitor capacitor : this.getEnergyCapacitorBlocksOnNetwork()) {
				if (capacitor.canAcceptElectricPacket(packet)) {
					capacitor.acceptEnergyPacket(packet);
					return;
				}
			}
			for (IEnergyConsumer consumer : this.getEnergyConsumerBlocksOnNetwork()) {
				if (consumer.canAcceptElectricPacket(packet)) {
					consumer.acceptEnergyPacket(packet);
					return;
				}
			}
		}
	}
	
}
