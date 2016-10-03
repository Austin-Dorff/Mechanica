package com.austindorff.mechanica.tileentity.energy.storage;

import com.austindorff.mechanica.energy.EnumResistance;
import com.austindorff.mechanica.energy.EnumVoltage;
import com.austindorff.mechanica.energy.IEnergyStorage;
import com.austindorff.mechanica.network.packet.energy.PacketEnergy;
import com.austindorff.mechanica.tileentity.energy.TileEntityEnergyBlockBase;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

public abstract class TileEntityEnergyStorageBlockBase extends TileEntityEnergyBlockBase implements IEnergyStorage {
	
	private int storedEnergy;
	private int storageLimit;
	
	public TileEntityEnergyStorageBlockBase(int storageLimit) {
		this.storageLimit = storageLimit;
		this.storedEnergy = 0;
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
	public boolean canAcceptEnergyPacket(PacketEnergy packet) {
		return this.storedEnergy + packet.getEnergyValue() <= this.storageLimit;
	}

	@Override
	public boolean canSendEnergyPacket(PacketEnergy packet) {
		return getEnergyNetwork().canAcceptPacket(packet);
	}

	@Override
	public void sendEnergyPacket(PacketEnergy packet) {
		this.getEnergyNetwork().injectPacket(packet);
	}

	@Override
	public void recieveEnergyPacket(PacketEnergy packet) {
		this.storedEnergy += packet.getEnergyValue();
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
		return this.getEnergyNetwork().canAcceptPacket(new PacketEnergy(this, this.getVoltageEnum().getVoltage(), this.getResistanceEnum().getResistance(), this.pos, false));
	}

	@Override
	public boolean canRecieveEnergyFromNetworkInDirection(EnumFacing facing) {
		return false;
	}

	@Override
	public int getEnergyOutput() {
		return this.getVoltageEnum().getVoltage() / this.getResistanceEnum().getResistance();
	}

	@Override
	public abstract EnumVoltage getVoltageEnum();

	@Override
	public abstract EnumResistance getResistanceEnum();
	

}
