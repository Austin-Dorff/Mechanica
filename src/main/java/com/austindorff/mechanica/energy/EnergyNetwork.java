package com.austindorff.mechanica.energy;

import java.util.ArrayList;

import com.austindorff.mechanica.network.packet.energy.PacketEnergy;
import com.austindorff.mechanica.tileentity.energy.TileEntityEnergyBlockBase;
import com.austindorff.mechanica.tileentity.energy.storage.batterybox.TileEntityBatteryBox;

public class EnergyNetwork {
	
	private ArrayList<TileEntityEnergyBlockBase> tileEntitiesInNetwork;
	
	public EnergyNetwork(TileEntityEnergyBlockBase tile) {
		this.tileEntitiesInNetwork = new ArrayList<TileEntityEnergyBlockBase>();
		addTileEntityToNetwork(tile);
	}
	
	public void addTileEntityToNetwork(TileEntityEnergyBlockBase tile) {
		this.tileEntitiesInNetwork.add(tile);
	}
	
	public void removeTileEntityFromNetwork(TileEntityEnergyBlockBase tile) {
		this.tileEntitiesInNetwork.remove(tile);
	}
	
	public ArrayList<TileEntityEnergyBlockBase> getNetworkTileEntities() {
		return this.tileEntitiesInNetwork;
	}
	
	public boolean canAcceptPacket(PacketEnergy packet) {
		for (TileEntityEnergyBlockBase tile : getNetworkTileEntities()) {
			if (tile instanceof TileEntityEnergyBlockBase) {
				tile = ((TileEntityEnergyBlockBase) tile);
				if (tile.canAcceptEnergyPacket(packet)) {
					return true;
				}
			}
		}
		return false;
	}
	
	public void injectPacket(PacketEnergy packet) {
		for (TileEntityEnergyBlockBase tile : getNetworkTileEntities()) {
			if (tile instanceof TileEntityBatteryBox) {
				tile = ((TileEntityBatteryBox) tile);
				if (tile.canAcceptEnergyPacket(packet)) {
					tile.recieveEnergyPacket(packet);
				}
			}
		}
	}
	
}
