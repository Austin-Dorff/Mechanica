package com.austindorff.mechanica.tileentity.energy.wire;

import com.austindorff.mechanica.block.wire.BlockWireBase;
import com.austindorff.mechanica.block.wire.BlockWireBase.EnumWireType;
import com.austindorff.mechanica.energy.EnumResistance;
import com.austindorff.mechanica.energy.EnumVoltage;
import com.austindorff.mechanica.energy.IEnergySupplier;
import com.austindorff.mechanica.network.packet.energy.PacketEnergy;
import com.austindorff.mechanica.tileentity.connectable.TileEntityConnectable;
import com.austindorff.mechanica.tileentity.energy.TileEntityEnergyBlockBase;

import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;

public class TileEntityWire extends TileEntityEnergyBlockBase implements IEnergySupplier {
	
	private boolean isPowered = false;
	private EnumVoltage voltage;
	private EnumResistance resistance;
	
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
		BlockWireBase block = ((BlockWireBase) state.getBlock());
		this.worldObj.notifyBlockUpdate(coords, state, block.getActualState(state, this.worldObj, coords), 3);
	}
	
	@Override
	public boolean hasMinVoltage() {
		IProperty<EnumWireType> property = ((IProperty<EnumWireType>) getBlockStateContainer().getProperty("wire_type"));
		switch (property.getName()) {
			case "Copper": {
				return EnumWireType.COPPER.hasMinVoltage();
			}
		}
		return false;
	}
	
	@Override
	public boolean hasMaxVoltage() {
		IProperty<EnumWireType> property = ((IProperty<EnumWireType>) getBlockStateContainer().getProperty("wire_type"));
		switch (property.getName()) {
			case "Copper": {
				return EnumWireType.COPPER.hasMaxVoltage();
			}
		}
		return false;
	}
	
	@Override
	public EnumVoltage getMinVoltageEnum() {
		IProperty<EnumWireType> property = ((IProperty<EnumWireType>) getBlockStateContainer().getProperty("wire_type"));
		switch (property.getName()) {
			case "Copper": {
				return EnumWireType.COPPER.minVoltage();
			}
		}
		return EnumVoltage.INVALID;
	}
	
	@Override
	public EnumVoltage getMaxVoltageEnum() {
		IProperty<EnumWireType> property = ((IProperty<EnumWireType>) getBlockStateContainer().getProperty("wire_type"));
		switch (property.getName()) {
			case "Copper": {
				return EnumWireType.COPPER.maxVoltage();
			}
		}
		return EnumVoltage.INVALID;
	}
	
	@Override
	public EnumResistance getResistanceEnum() {
		IProperty<EnumWireType> property = ((IProperty<EnumWireType>) getBlockStateContainer().getProperty("wire_type"));
		switch (property.getName()) {
			case "Copper": {
				return EnumWireType.COPPER.resistance();
			}
		}
		return EnumResistance.INVALID;
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
	
	public boolean isPowered() {
		return this.isPowered;
	}
	
	@Override
	public boolean canFeedEnergyToNetworkInDirection(EnumFacing facing) {
		return this.canConnectToEnergyNetworkInDirection(facing);
	}

	@Override
	public boolean canAcceptEnergyPacket(PacketEnergy packet) {
		return true;
	}

	@Override
	public boolean canSendEnergyPacket(PacketEnergy packet) {
		return true;
	}

	@Override
	public void sendEnergyPacket(PacketEnergy packet) {
		this.getEnergyNetwork().injectPacket(packet);
	}

	@Override
	public void recieveEnergyPacket(PacketEnergy packet) {
		this.isPowered = true;
		this.voltage = EnumVoltage.getEnumFromVoltage(packet.getVoltage());
		this.resistance = EnumResistance.getEnumFromResistance(packet.getResistance());
	}
	
}
