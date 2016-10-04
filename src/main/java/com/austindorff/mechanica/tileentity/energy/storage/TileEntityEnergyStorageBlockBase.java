package com.austindorff.mechanica.tileentity.energy.storage;

import com.austindorff.mechanica.energy.ElectricPacket;
import com.austindorff.mechanica.energy.IEnergyCapacitor;
import com.austindorff.mechanica.tileentity.energy.TileEntityEnergyBlockBase;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;

public abstract class TileEntityEnergyStorageBlockBase extends TileEntityEnergyBlockBase implements IEnergyCapacitor {
	
	private float storedEnergy;
	private float storageLimit;
	
	public TileEntityEnergyStorageBlockBase(float storageLimit) {
		this.storageLimit = storageLimit;
		this.storedEnergy = 0;
	}
	
	public float getEnergyStored() {
		return this.storedEnergy;
	}

	@Override
	public boolean isCorrectTileEntity(TileEntity tile) {
		return tile instanceof TileEntityEnergyStorageBlockBase;
	}

	@Override
	public boolean isFull() {
		return this.storedEnergy == this.storageLimit;
	}

	@Override
	public boolean isEmpty() {
		return this.storedEnergy == 0;
	}
	
	@Override
	public boolean isPartiallyFull() {
		return !isFull() && !isEmpty();
	}

	@Override
	public boolean canAcceptElectricPacket(ElectricPacket packet) {
		return this.storedEnergy + packet.getMinecraftAmperes() <= this.storageLimit;
	}

	@Override
	public boolean canSendElectricPacket(ElectricPacket packet) {
		return getNetwork().canAcceptPacket(packet);
	}

	@Override
	public void sendElectricPacket(ElectricPacket packet) {
		this.getNetwork().injectPacket(packet);
	}

	@Override
	public void recieveElectricPacket(ElectricPacket packet) {
		this.storedEnergy += packet.getMinecraftAmperes();
	}

	@Override
	public boolean canConnectToEnergyNetworkInDirection(EnumFacing facing) {
		switch (facing) {
			case UP: {
				return this.isNeighborUp();
			}
			case DOWN: {
				return this.isNeighborDown();
			}
			case NORTH: {
				return this.isNeighborNorth();
			}
			case EAST: {
				return this.isNeighborEast();
			}
			case SOUTH: {
				return this.isNeighborSouth();
			}
			case WEST: {
				return this.isNeighborWest();
			}
		}
		return false;
	}

	@Override
	public boolean canFeedEnergyToNetworkInDirection(EnumFacing facing) {
		return this.getNetwork().canAcceptPacket(new ElectricPacket(this.getMinecraftAmperesOutput()));
	}

	@Override
	public boolean canRecieveEnergyFromNetworkInDirection(EnumFacing facing) {
		return false;
	}

	public abstract float getMinecraftAmperesOutput();

	@Override
	public abstract boolean canAcceptMinecraftAmperes(float minecraftAmperes);

	@Override
	public abstract void updateBlockState(BlockPos coords);
	
	
	@Override
	public boolean doesTransferEnergy() {
		return false;
	}

	@Override
	public boolean doesUseEnergy() {
		return false;
	}

	@Override
	public boolean doesStoreEnergy() {
		return true;
	}

	@Override
	public boolean doesProduceEnergy() {
		return false;
	}

}
