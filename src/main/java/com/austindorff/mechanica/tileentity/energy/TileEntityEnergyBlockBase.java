package com.austindorff.mechanica.tileentity.energy;

import com.austindorff.mechanica.energy.EnergyNetwork;
import com.austindorff.mechanica.network.packet.energy.PacketEnergy;
import com.austindorff.mechanica.tileentity.connectable.TileEntityConnectable;

import net.minecraft.tileentity.TileEntity;

public abstract class TileEntityEnergyBlockBase extends TileEntityConnectable {
	
	private EnergyNetwork network;
	
	public TileEntityEnergyBlockBase() {
		this.network = new EnergyNetwork(this);
	}
	
	@Override
	public void updateConnections() {
		super.updateConnections();
		if (hasNeighborOnNetwork() != null) {
			setEnergyNetwork(hasNeighborOnNetwork().getEnergyNetwork());
		} else {
			setEnergyNetwork(new EnergyNetwork(this));
		}
	}
	
	private TileEntityEnergyBlockBase hasNeighborOnNetwork() {
		int networkSize = 0;
		TileEntityEnergyBlockBase tile = null;
		if (isNeighborUp()) {
			if (((TileEntityEnergyBlockBase) getNeighborUp()).getEnergyNetwork().getNetworkTileEntities().size() > networkSize) {
				networkSize = ((TileEntityEnergyBlockBase) getNeighborUp()).getEnergyNetwork().getNetworkTileEntities().size();
				tile = ((TileEntityEnergyBlockBase) getNeighborUp());
			}
		} else if (isNeighborDown()) {
			if (((TileEntityEnergyBlockBase) getNeighborDown()).getEnergyNetwork().getNetworkTileEntities().size() > networkSize) {
				networkSize = ((TileEntityEnergyBlockBase) getNeighborDown()).getEnergyNetwork().getNetworkTileEntities().size();
				tile = ((TileEntityEnergyBlockBase) getNeighborDown());
			}
		} else if (isNeighborNorth()) {
			if (((TileEntityEnergyBlockBase) getNeighborNorth()).getEnergyNetwork().getNetworkTileEntities().size() > networkSize) {
				networkSize = ((TileEntityEnergyBlockBase) getNeighborNorth()).getEnergyNetwork().getNetworkTileEntities().size();
				tile = ((TileEntityEnergyBlockBase) getNeighborNorth());
			}
		} else if (isNeighborEast()) {
			if (((TileEntityEnergyBlockBase) getNeighborEast()).getEnergyNetwork().getNetworkTileEntities().size() > networkSize) {
				networkSize = ((TileEntityEnergyBlockBase) getNeighborEast()).getEnergyNetwork().getNetworkTileEntities().size();
				tile = ((TileEntityEnergyBlockBase) getNeighborEast());
			}
		} else if (isNeighborSouth()) {
			if (((TileEntityEnergyBlockBase) getNeighborSouth()).getEnergyNetwork().getNetworkTileEntities().size() > networkSize) {
				networkSize = ((TileEntityEnergyBlockBase) getNeighborSouth()).getEnergyNetwork().getNetworkTileEntities().size();
				tile = ((TileEntityEnergyBlockBase) getNeighborSouth());
			}
		} else if (isNeighborWest()) {
			if (((TileEntityEnergyBlockBase) getNeighborWest()).getEnergyNetwork().getNetworkTileEntities().size() > networkSize) {
				networkSize = ((TileEntityEnergyBlockBase) getNeighborWest()).getEnergyNetwork().getNetworkTileEntities().size();
				tile = ((TileEntityEnergyBlockBase) getNeighborWest());
			}
		}
		return tile;
	}
	
	public void setEnergyNetwork(EnergyNetwork network) {
		this.network = network;
		this.network.addTileEntityToNetwork(this);
	}
	
	public EnergyNetwork getEnergyNetwork() {
		return this.network;
	}
	
	@Override
	public abstract boolean isCorrectTileEntity(TileEntity tile);
	
	public abstract boolean canAcceptEnergyPacket(PacketEnergy packet);
	
	public boolean canSendEnergyPacket(PacketEnergy packet) {
		return this.network.canAcceptPacket(packet);
	}
	
	public abstract void sendEnergyPacket(PacketEnergy packet);
	
	public abstract void recieveEnergyPacket(PacketEnergy packet);
	
}
