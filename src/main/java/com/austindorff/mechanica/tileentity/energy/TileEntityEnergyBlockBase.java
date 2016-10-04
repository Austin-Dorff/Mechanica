package com.austindorff.mechanica.tileentity.energy;

import java.util.List;

import com.austindorff.mechanica.energy.ElectricPacket;
import com.austindorff.mechanica.energy.EnergyNetwork;
import com.austindorff.mechanica.energy.INetworkComponent;
import com.austindorff.mechanica.tileentity.connectable.TileEntityConnectable;
import com.google.common.collect.Lists;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;

public abstract class TileEntityEnergyBlockBase extends TileEntityConnectable implements INetworkComponent, ITickable {
	
	private EnergyNetwork	network;
	private boolean			hasBeenPlaced	= false;
											
	public TileEntityEnergyBlockBase() {
	}
	
	@Override
	public void updateConnections() {
		if (isConnectableNeighborUp()) {
			updateBlockState(getNeighborUp().getPos());
		}
		if (isConnectableNeighborDown()) {
			updateBlockState(getNeighborDown().getPos());
		}
		if (isConnectableNeighborNorth()) {
			updateBlockState(getNeighborNorth().getPos());
		}
		if (isConnectableNeighborEast()) {
			updateBlockState(getNeighborEast().getPos());
		}
		if (isConnectableNeighborSouth()) {
			updateBlockState(getNeighborSouth().getPos());
		}
		if (isConnectableNeighborWest()) {
			updateBlockState(getNeighborWest().getPos());
		}
	}
	
	public boolean isConnectableNeighborNorth() {
		return getNeighborNorth() != null && getNeighborNorth() instanceof TileEntityEnergyBlockBase;
	}
	
	public boolean isConnectableNeighborEast() {
		return getNeighborEast() != null && getNeighborEast() instanceof TileEntityEnergyBlockBase;
	}
	
	public boolean isConnectableNeighborSouth() {
		return getNeighborSouth() != null && getNeighborSouth() instanceof TileEntityEnergyBlockBase;
	}
	
	public boolean isConnectableNeighborWest() {
		return getNeighborWest() != null && getNeighborWest() instanceof TileEntityEnergyBlockBase;
	}
	
	public boolean isConnectableNeighborUp() {
		return getNeighborUp() != null && getNeighborUp() instanceof TileEntityEnergyBlockBase;
	}
	
	public boolean isConnectableNeighborDown() {
		return getNeighborDown() != null && getNeighborDown() instanceof TileEntityEnergyBlockBase;
	}
	
	@Override
	public abstract boolean isCorrectTileEntity(TileEntity tile);
	
	public abstract boolean canAcceptElectricPacket(ElectricPacket packet);
	
	public boolean canSendElectricPacket(ElectricPacket packet) {
		return this.network.canAcceptPacket(packet);
	}
	
	public abstract void sendElectricPacket(ElectricPacket packet);
	
	public abstract void recieveElectricPacket(ElectricPacket packet);
	
	@Override
	public EnergyNetwork getNetwork() {
		return this.network;
	}
	
	@Override
	public void setNetwork(EnergyNetwork network) {
		this.network = network;
	}
	
	@Override
	public abstract void updateBlockState(BlockPos coords);
	
	@Override
	public void update() {
		if (!hasBeenPlaced) {
			hasBeenPlaced = true;
			init();
		}
	}
	
	private void init() {
		EnergyNetwork.integrate(this, getNeighbors());
	}
	
	@Override
	public void invalidate() {
		super.invalidate();
		
		EnergyNetwork graph = this.getNetwork();
		if (graph != null) {
			graph.remove(this);
		}
	}
	
	private List<INetworkComponent> getNeighbors() {
		List<INetworkComponent> neighbors = Lists.newArrayList();
		for (EnumFacing f : EnumFacing.VALUES) {
			TileEntity otherTile = worldObj.getTileEntity(pos.offset(f));
			if (!(otherTile instanceof TileEntityEnergyBlockBase)) {
				continue;
			}				
			INetworkComponent otherComponent = ((TileEntityEnergyBlockBase) otherTile);
			if (otherComponent.getNetwork() != null) {
				neighbors.add(otherComponent);
			}
		}
		return neighbors;
	}
	
	public void updateNeighbors() {
		EnergyNetwork graph = this.getNetwork();
		if (graph != null) {
			graph.addNeighors(this, getNeighbors());
		}
	}
	
	public void broadcastDirty() {
		if (getNetwork() == null) {
			return;
		}			
		for (INetworkComponent object : getNetwork().getComponents()) {
			if (!(object instanceof TileEntityEnergyBlockBase)) {
				continue;
			}
			TileEntityEnergyBlockBase proxy = (TileEntityEnergyBlockBase) object;
			if (!proxy.isInvalid()) {
				proxy.markDirty();
			}
		}
	}

	@Override
	public abstract boolean doesTransferEnergy();

	@Override
	public abstract boolean doesUseEnergy();

	@Override
	public abstract boolean doesStoreEnergy();

	@Override
	public abstract boolean doesProduceEnergy();
	
}
