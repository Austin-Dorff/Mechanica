package com.austindorff.mechanica.tileentity.energy.wire;

import java.util.ArrayList;

import com.austindorff.mechanica.block.energy.wire.BlockWireBase;
import com.austindorff.mechanica.block.energy.wire.BlockWireBase.EnumWireType;
import com.austindorff.mechanica.energy.ElectricPacket;
import com.austindorff.mechanica.energy.EnergyNetwork;
import com.austindorff.mechanica.energy.EnumDirection;
import com.austindorff.mechanica.energy.IEnergyConductor;
import com.austindorff.mechanica.energy.INetworkComponent;
import com.austindorff.mechanica.tileentity.energy.TileEntityEnergyBlockBase;

import net.minecraft.block.Block;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;

public class TileEntityWire extends TileEntityEnergyBlockBase implements IEnergyConductor {
	
	public TileEntityWire() {
	}
	
	@Override
	public boolean isCorrectTileEntity(TileEntity tile) {
		return tile instanceof TileEntityWire;
	}
	
	private BlockStateContainer getBlockStateContainer() {
		return this.worldObj.getBlockState(this.pos).getBlock().getBlockState();
	}
	
	@Override
	public void updateBlockState(BlockPos coords) {
		IBlockState state = this.worldObj.getBlockState(coords);
		Block block = state.getBlock();
		if (block instanceof BlockWireBase) {
			this.worldObj.notifyBlockUpdate(coords, state, ((BlockWireBase) block).getActualState(state, this.worldObj, coords), 3);
		}
	}
	
	@Override
	public boolean hasMinMinecraftAmperes() {
		IProperty<EnumWireType> property = ((IProperty<EnumWireType>) getBlockStateContainer().getProperty("wire_type"));
		switch (property.getName()) {
			case "Copper": {
				return EnumWireType.COPPER.hasMinMinecraftAmperes();
			}
		}
		return false;
	}
	
	@Override
	public boolean hasMaxMinecraftAmperes() {
		IProperty<EnumWireType> property = ((IProperty<EnumWireType>) getBlockStateContainer().getProperty("wire_type"));
		switch (property.getName()) {
			case "Copper": {
				return EnumWireType.COPPER.hasMaxMinecraftAmperes();
			}
		}
		return false;
	}
	
	@Override
	public int getMinMinecraftAmperes() {
		IProperty<EnumWireType> property = ((IProperty<EnumWireType>) getBlockStateContainer().getProperty("wire_type"));
		switch (property.getName()) {
			case "Copper": {
				return EnumWireType.COPPER.minMinecraftAmperes();
			}
		}
		return 0;
	}
	
	@Override
	public int getMaxMinecraftAmperes() {
		IProperty<EnumWireType> property = ((IProperty<EnumWireType>) getBlockStateContainer().getProperty("wire_type"));
		switch (property.getName()) {
			case "Copper": {
				return EnumWireType.COPPER.maxMinecraftAmperes();
			}
		}
		return 0;
	}
	
	@Override
	public boolean canAcceptElectricPacket(ElectricPacket packet) {
		return true;
	}
	
	@Override
	public boolean canSendElectricPacket(ElectricPacket packet) {
		return this.getEnergyNetworks().get(0) != null && this.getEnergyNetworks().get(0).canAcceptEnergyPacket(packet);
	}
	
	@Override
	public void sendElectricPacket(ElectricPacket packet) {
		if (this.getEnergyNetworks().get(0).canAcceptEnergyPacket(packet)) {
			this.getEnergyNetworks().get(0).injectEnergyPacket(packet);
		}
	}
	
	@Override
	public void recieveElectricPacket(ElectricPacket packet) {
	
	}
	
	@Override
	public boolean doesTransferEnergy() {
		return true;
	}
	
	@Override
	public boolean doesOutputEnergy() {
		return false;
	}
	
	@Override
	public boolean doesUseEnergy() {
		return false;
	}
	
	@Override
	public boolean doesStoreEnergy() {
		return false;
	}
	
	@Override
	public boolean doesProduceEnergy() {
		return false;
	}
	
	@Override
	public boolean hasLossOverDistance() {
		return false;
	}
	
	@Override
	public int getLossPerUnitDistance() {
		return 0;
	}
	
	@Override
	public void setUnitDistanceForLoss(int distance) {
	
	}
	
	@Override
	public boolean canFeedEnergyToNetworkInDirection(EnumDirection direction) {
		return true;
	}
	
	@Override
	public boolean canRecieveEnergyFromNetworkConnectionInDirection(EnumDirection direction) {
		return true;
	}
	
}
