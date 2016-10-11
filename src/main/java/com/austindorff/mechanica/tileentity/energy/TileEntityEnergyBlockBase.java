package com.austindorff.mechanica.tileentity.energy;

import java.util.ArrayList;

import com.austindorff.mechanica.energy.ElectricPacket;
import com.austindorff.mechanica.energy.EnergyNetwork;
import com.austindorff.mechanica.energy.EnumDirection;
import com.austindorff.mechanica.energy.INetworkComponent;
import com.austindorff.mechanica.tileentity.connectable.TileEntityConnectable;
import com.austindorff.mechanica.tileentity.energy.wire.TileEntityWire;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;

public abstract class TileEntityEnergyBlockBase extends TileEntityConnectable implements INetworkComponent, ITickable {
	
	private boolean						hasBeenPlaced	= false;
														
	private ArrayList<EnergyNetwork>	networks		= new ArrayList<EnergyNetwork>();
														
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
	
	public abstract boolean canSendElectricPacket(ElectricPacket packet);
	
	public abstract void sendElectricPacket(ElectricPacket packet);
	
	public abstract void recieveElectricPacket(ElectricPacket packet);
	
	@Override
	public abstract void updateBlockState(BlockPos coords);
	
	@Override
	public void update() {
		if (!hasBeenPlaced) {
			hasBeenPlaced = true;
			incorporate();
		}
	}
	
	private void incorporate() {
		if (this instanceof TileEntityWire) {
			EnergyNetwork.incorporateComponent(this);
		}
	}
	
	@Override
	public void invalidate() {
		if (this instanceof TileEntityWire) {
			System.out.println("HELLO");
			if (this.getEnergyNetworks().get(0) != null) {
				this.getEnergyNetworks().get(0).removeComponent(this);
			}
		}
		super.invalidate();
	}
	
	@Override
	public BlockPos getPosition() {
		return this.pos;
	}
	
	@Override
	public abstract boolean doesTransferEnergy();
	
	@Override
	public abstract boolean doesUseEnergy();
	
	@Override
	public abstract boolean doesStoreEnergy();
	
	@Override
	public abstract boolean doesProduceEnergy();
	
	@Override
	public boolean doesBelongToNetwork(EnergyNetwork network) {
		return this.networks.contains(network);
	}
	
	@Override
	public ArrayList<INetworkComponent> getNeighbors() {
		ArrayList<INetworkComponent> neighbors = new ArrayList<INetworkComponent>();
		for (TileEntity tile : getAllNeighbors()) {
			if (tile == null || !(tile instanceof INetworkComponent)) {
				neighbors.add(null);
			} else {
				neighbors.add((INetworkComponent) tile);
			}
		}
		return neighbors;
	}
	
	@Override
	public abstract boolean doesOutputEnergy();
	
	@Override
	public boolean hasNetworkConnectionInDirection(EnumDirection direction) {
		return this.networks.size() > 0 && this.networks.size() <= direction.getBlockFace() && this.networks.get(direction.getBlockFace()) != null;
	}
	
	@Override
	public abstract boolean canFeedEnergyToNetworkInDirection(EnumDirection direction);
	
	@Override
	public abstract boolean canRecieveEnergyFromNetworkConnectionInDirection(EnumDirection direction);
	
	@Override
	public EnergyNetwork getEnergyNetworkInDirection(EnumDirection direction) {
		if (getEnergyNetworks() == null || getEnergyNetworks().size() <= direction.getBlockFace()) {
			return null;
		}
		return getEnergyNetworks().get(direction.getBlockFace());
	}
	
	@Override
	public ArrayList<EnergyNetwork> getEnergyNetworks() {
		return this.networks;
	}
	
	@Override
	public void setEnergyNetworkInDirection(EnergyNetwork network, EnumDirection direction) {
		if (direction == EnumDirection.ALL) {
			if (this.networks.size() < 6) {
				for (int i = 0; i < 6; i++) {
					this.networks.add(null);
				}
			}
			for (int i = 0; i < 6; i++) {
				this.networks.set(i, network);
			}
		} else {
			if (this.networks.size() < 6) {
				while (this.networks.size() < 6) {
					this.networks.add(null);
				}
			}
			this.networks.set(direction.getBlockFace(), network);
		}
	}
	
}
