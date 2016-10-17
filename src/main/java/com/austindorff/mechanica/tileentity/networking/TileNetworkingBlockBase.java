package com.austindorff.mechanica.tileentity.networking;

import java.util.ArrayList;

import com.austindorff.mechanica.networking.EnumDirection;
import com.austindorff.mechanica.networking.INetworkComponent;
import com.austindorff.mechanica.networking.Network;
import com.austindorff.mechanica.tileentity.connectable.TileEntityConnectable;
import com.austindorff.mechanica.tileentity.networking.pipe.TileNetworkingPipeBase;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;

public abstract class TileNetworkingBlockBase extends TileEntityConnectable implements INetworkComponent, ITickable {
	
	private boolean						hasBeenPlaced	= false;
														
	private ArrayList<Network>	networks		= new ArrayList<Network>();
														
	public TileNetworkingBlockBase() {
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
		return getNeighborNorth() != null && getNeighborNorth() instanceof TileNetworkingBlockBase;
	}
	
	public boolean isConnectableNeighborEast() {
		return getNeighborEast() != null && getNeighborEast() instanceof TileNetworkingBlockBase;
	}
	
	public boolean isConnectableNeighborSouth() {
		return getNeighborSouth() != null && getNeighborSouth() instanceof TileNetworkingBlockBase;
	}
	
	public boolean isConnectableNeighborWest() {
		return getNeighborWest() != null && getNeighborWest() instanceof TileNetworkingBlockBase;
	}
	
	public boolean isConnectableNeighborUp() {
		return getNeighborUp() != null && getNeighborUp() instanceof TileNetworkingBlockBase;
	}
	
	public boolean isConnectableNeighborDown() {
		return getNeighborDown() != null && getNeighborDown() instanceof TileNetworkingBlockBase;
	}
	
	@Override
	public abstract boolean isCorrectTileEntity(TileEntity tile);
	
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
		if (this instanceof TileNetworkingPipeBase) {
			Network.incorporateComponent((TileNetworkingPipeBase)this);
		}
	}
	
	@Override
	public void invalidate() {
		if (this instanceof TileNetworkingPipeBase) {
			if (this.getNetworks().get(0) != null) {
				this.getNetworks().get(0).removeComponent((TileNetworkingPipeBase)this);
			}
		}
		super.invalidate();
	}
	
	@Override
	public BlockPos getPosition() {
		return this.pos;
	}
	
	@Override
	public boolean doesBelongToNetwork(Network network) {
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
	public boolean hasNetworkConnectionInDirection(EnumDirection direction) {
		return this.networks.size() > 0 && this.networks.size() <= direction.getBlockFace() && this.networks.get(direction.getBlockFace()) != null;
	}
	
	@Override
	public Network getNetworkInDirection(EnumDirection direction) {
		if (getNetworks() == null || getNetworks().size() <= direction.getBlockFace()) {
			return null;
		}
		return getNetworks().get(direction.getBlockFace());
	}
	
	@Override
	public ArrayList<Network> getNetworks() {
		return this.networks;
	}
	
	@Override
	public void setNetworkInDirection(Network network, EnumDirection direction) {
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

	@Override
	public ArrayList<INetworkComponent> getNeighboringNetworkingPipes() {
		return this.getNeighbors();
	}
	
}
