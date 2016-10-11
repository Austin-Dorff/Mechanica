package com.austindorff.mechanica.tileentity.energy.storage;

import java.util.ArrayList;

import com.austindorff.mechanica.energy.ElectricPacket;
import com.austindorff.mechanica.energy.EnergyNetwork;
import com.austindorff.mechanica.energy.EnumDirection;
import com.austindorff.mechanica.energy.IEnergyCapacitor;
import com.austindorff.mechanica.energy.INetworkComponent;
import com.austindorff.mechanica.tileentity.energy.TileEntityEnergyBlockBase;
import com.austindorff.mechanica.tileentity.energy.wire.TileEntityWire;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;

public abstract class TileEntityEnergyStorageBlockBase extends TileEntityEnergyBlockBase implements IEnergyCapacitor {
	
	private int storedEnergy;
	private int storageLimit;
	
	public TileEntityEnergyStorageBlockBase(int storageLimit) {
		this.storageLimit = storageLimit;
		this.storedEnergy = 0;
	}
	
	public int getEnergyStored() {
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
		for (EnergyNetwork net : this.getEnergyNetworks()) {
			if (net.canAcceptEnergyPacket(packet)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public void sendElectricPacket(ElectricPacket packet) {
		for (EnergyNetwork net : this.getEnergyNetworks()) {
			if (net.canAcceptEnergyPacket(packet)) {
				net.injectEnergyPacket(packet);
				break;
			}
		}
		this.markDirty();
	}

	@Override
	public void recieveElectricPacket(ElectricPacket packet) {
		this.storedEnergy += packet.getMinecraftAmperes();
		this.markDirty();
	}

	public abstract int getMinecraftAmperesOutput();

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
	
	@Override
	public boolean doesOutputEnergy() {
		return true;
	}

	@Override
	public void acceptEnergyPacket(ElectricPacket packet) {
		this.storedEnergy += packet.getMinecraftAmperes();
		this.markDirty();
	}

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
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		super.writeToNBT(compound);
		compound.setInteger("storedEnergy", this.storedEnergy);
		return compound;
	}
	
	@Override
	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);
		this.storedEnergy = compound.getInteger("storedEnergy");
	}

}
