package com.austindorff.mechanica.tileentity.energy.producer;

import com.austindorff.mechanica.energy.ElectricPacket;
import com.austindorff.mechanica.energy.IEnergyProducer;
import com.austindorff.mechanica.tileentity.energy.TileEntityEnergyBlockBase;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;

public abstract class TileEntityEnergyProducerBase extends TileEntityEnergyBlockBase implements IEnergyProducer {

	@Override
	public abstract float getMinecraftAmperesProduced();

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
		return this.getNetwork().canAcceptPacket(getElectricPacket());
	}

	@Override
	public abstract boolean isCorrectTileEntity(TileEntity tile);

	@Override
	public boolean canAcceptElectricPacket(ElectricPacket packet) {
		return false;
	}

	@Override
	public abstract void sendElectricPacket(ElectricPacket packet);

	@Override
	public void recieveElectricPacket(ElectricPacket packet) {
		
	}
	
	public boolean canInjectEnergyPacketIntoNetwork() {
		return this.getNetwork().canAcceptPacket(getElectricPacket());
	}
	
	public ElectricPacket getElectricPacket() {
		return new ElectricPacket(this.getMinecraftAmperesProduced());
	}
	
	@Override
	public boolean doesTransferEnergy() {
		return false;
	}
	
	@Override
	public boolean doesUseEnergy() {
		return false;
	}

	@Override
	public boolean doesProduceEnergy() {
		return true;
	}

	@Override
	public abstract void updateBlockState(BlockPos coords);

	@Override
	public abstract boolean doesStoreEnergy();

}
