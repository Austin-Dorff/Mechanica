package com.austindorff.mechanica.tileentity.energy.producer;

import java.util.ArrayList;

import com.austindorff.mechanica.energy.ElectricPacket;
import com.austindorff.mechanica.energy.EnergyNetwork;
import com.austindorff.mechanica.energy.EnumDirection;
import com.austindorff.mechanica.energy.IEnergyProducer;
import com.austindorff.mechanica.energy.INetworkComponent;
import com.austindorff.mechanica.tileentity.energy.TileEntityEnergyBlockBase;
import com.austindorff.mechanica.tileentity.energy.wire.TileEntityWire;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;

public abstract class TileEntityEnergyProducerBase extends TileEntityEnergyBlockBase implements IEnergyProducer {
	
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
	
	public ElectricPacket getElectricPacket() {
		return new ElectricPacket(this, this.getMinecraftAmperesProducedPerTick());
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
	public boolean doesOutputEnergy() {
		return true;
	}
	
	@Override
	public abstract void updateBlockState(BlockPos coords);
	
	@Override
	public abstract boolean doesStoreEnergy();
	
	@Override
	public boolean canSendElectricPacket(ElectricPacket packet) {
		for (EnergyNetwork net : this.getEnergyNetworks()) {
			if (net != null && net.canAcceptEnergyPacket(getElectricPacket())) {
				return true;
			}
		}
		return false;
	}
	
	@Override
	public abstract int getMinecraftAmperesProducedPerTick();
	
	@Override
	public boolean canFeedEnergyToNetworkInDirection(EnumDirection direction) {
		return true;
	}
	
	@Override
	public boolean canRecieveEnergyFromNetworkConnectionInDirection(EnumDirection direction) {
		return true;
	}
	
	@Override
	public ArrayList<EnergyNetwork> getEnergyNetworks() {
		ArrayList<EnergyNetwork> networks = new ArrayList<EnergyNetwork>();
		for (int i = 0; i < 6; i++) {
			networks.add(null);
		}
		int index = 0;
		for (INetworkComponent neighbor : this.getNeighbors()) {
			if (neighbor instanceof TileEntityWire) {
				if ((neighbor.getEnergyNetworks().size() > 0) && (neighbor.getEnergyNetworks().get(0) != null)) {
					networks.set(index, neighbor.getEnergyNetworks().get(0));
				}
			}
			index++;
		}
		return networks;
	}
	
}
