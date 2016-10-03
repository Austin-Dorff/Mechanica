package com.austindorff.mechanica.tileentity.energy.producer;

import com.austindorff.mechanica.energy.EnumResistance;
import com.austindorff.mechanica.energy.EnumVoltage;
import com.austindorff.mechanica.energy.IEnergyProducer;
import com.austindorff.mechanica.network.packet.energy.PacketEnergy;
import com.austindorff.mechanica.tileentity.energy.TileEntityEnergyBlockBase;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;

public abstract class TileEntityEnergyProducerBase extends TileEntityEnergyBlockBase implements IEnergyProducer {

	@Override
	public abstract EnumVoltage getVoltageProducedEnum();

	@Override
	public int getCurrentProduced() {
		return this.getVoltageProducedEnum().getVoltage() / this.getResistanceEnum().getResistance();
	}

	@Override
	public abstract EnumResistance getResistanceEnum();

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
		return this.getEnergyNetwork().canAcceptPacket(new PacketEnergy(this, this.getVoltageProducedEnum().getVoltage(), this.getResistanceEnum().getResistance(), this.pos, false));
	}

	@Override
	public abstract boolean isCorrectTileEntity(TileEntity tile);

	@Override
	public boolean canAcceptEnergyPacket(PacketEnergy packet) {
		return false;
	}

	@Override
	public abstract void sendEnergyPacket(PacketEnergy packet);

	@Override
	public void recieveEnergyPacket(PacketEnergy packet) {
		
	}
	
	public boolean canInjectEnergyPacketIntoNetwork() {
		return this.getEnergyNetwork().canAcceptPacket(getEnergyPacket());
	}
	
	public PacketEnergy getEnergyPacket() {
		return new PacketEnergy(this, this.getVoltageProducedEnum().getVoltage(), this.getResistanceEnum().getResistance(), this.pos, true);
	}

}
