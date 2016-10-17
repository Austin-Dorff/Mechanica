package com.austindorff.mechanica.networking;

import java.util.ArrayList;
import java.util.HashMap;

import com.austindorff.mechanica.tileentity.networking.pipe.TileNetworkingPipeBase;

public class Network {
	
	private INetworkComponent						masterComponent;
													
	private ArrayList<NetworkNode>					networkNodes		= new ArrayList<NetworkNode>();
	private ArrayList<INetworkComponent>			networkComponents	= new ArrayList<INetworkComponent>();
																		
	private HashMap<NetworkNode, INetworkComponent>	nodeToComponent		= new HashMap<NetworkNode, INetworkComponent>();
	private HashMap<INetworkComponent, NetworkNode>	componentToNode		= new HashMap<INetworkComponent, NetworkNode>();
																		
	public Network() {
	
	}
	
	public Network(INetworkComponent masterComponent) {
		this.masterComponent = masterComponent;
		addNodeToNetwork(masterComponent);
	}
	
	public Network(HashMap<INetworkComponent, NetworkNode> componentToNode, HashMap<NetworkNode, INetworkComponent> nodeToComponent, INetworkComponent masterComponent) {
		this.componentToNode = componentToNode;
		this.nodeToComponent = nodeToComponent;
		this.masterComponent = masterComponent;
		for (INetworkComponent component : this.nodeToComponent.values()) {
			component.setNetworkInDirection(this, EnumDirection.ALL);
		}
	}
	
	public static Network incorporateComponent(INetworkComponent newComponent) {
		ArrayList<INetworkComponent> neighbors = newComponent.getNeighbors();
		ArrayList<Network> neighboringEnergyNetworks = new ArrayList<Network>();
		for (INetworkComponent neighbor : neighbors) {
			if (neighbor != null) {
				Network neighborNet = neighbor.getNetworkInDirection(EnumDirection.ALL);
				if ((neighbor != null) && (neighbor instanceof INetworkComponent) && (neighborNet != null)) {
					if (!neighboringEnergyNetworks.contains(neighborNet)) {
						neighboringEnergyNetworks.add(neighborNet);
					}
				}
			}
		}
		Network newComponentNet = new Network(newComponent);
		newComponent.setNetworkInDirection(newComponentNet, EnumDirection.ALL);
		neighboringEnergyNetworks.add(new Network(newComponent));
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
		for (INetworkComponent neighbor : componentToRemove.getNeighboringNetworkingPipes()) {
			if (neighbor != null) {
				neighbor.setNetworkInDirection(new Network(neighbor), EnumDirection.ALL);
			}
		}
		Network.recreate(this);
	}
	
	private static void recreate(Network energyNetwork) {
		ArrayList<INetworkComponent> alreadySeen = new ArrayList<INetworkComponent>();
		for (INetworkComponent component : energyNetwork.nodeToComponent.values()) {
			if (!alreadySeen.contains(component)) {
				Network newNet = Network.incorporateComponent(component);
				component.setNetworkInDirection(newNet, EnumDirection.ALL);
				alreadySeen.add(component);
			}
		}
	}
	
	private static Network mergeNetworks(ArrayList<Network> energyNets) {
		HashMap<INetworkComponent, NetworkNode> componentToNodeNew = new HashMap<INetworkComponent, NetworkNode>();
		HashMap<NetworkNode, INetworkComponent> nodeToComponentNew = new HashMap<NetworkNode, INetworkComponent>();
		for (Network net : energyNets) {
			componentToNodeNew.putAll(net.componentToNode);
			nodeToComponentNew.putAll(net.nodeToComponent);
		}
		Network newNet = new Network(componentToNodeNew, nodeToComponentNew, energyNets.get(0).getMasterComponent());
		for (INetworkComponent component : newNet.nodeToComponent.values()) {
			component.setNetworkInDirection(newNet, EnumDirection.ALL);
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
	
}
