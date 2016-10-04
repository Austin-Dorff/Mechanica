package com.austindorff.mechanica.tileentity.energy.wire;

import com.austindorff.mechanica.block.energy.wire.BlockWireBase;
import com.austindorff.mechanica.block.energy.wire.BlockWireBase.EnumWireType;
import com.austindorff.mechanica.energy.ElectricPacket;
import com.austindorff.mechanica.energy.IEnergyConductor;
import com.austindorff.mechanica.tileentity.energy.TileEntityEnergyBlockBase;

import net.minecraft.block.Block;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;

public class TileEntityWire extends TileEntityEnergyBlockBase implements IEnergyConductor {
	
	private boolean isPowered = false;
	private float minecraftAmperes;
	
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
	public float getMinMinecraftAmperes() {
		IProperty<EnumWireType> property = ((IProperty<EnumWireType>) getBlockStateContainer().getProperty("wire_type"));
		switch (property.getName()) {
			case "Copper": {
				return EnumWireType.COPPER.minMinecraftAmperes();
			}
		}
		return 0;
	}
	
	@Override
	public float getMaxMinecraftAmperes() {
		IProperty<EnumWireType> property = ((IProperty<EnumWireType>) getBlockStateContainer().getProperty("wire_type"));
		switch (property.getName()) {
			case "Copper": {
				return EnumWireType.COPPER.maxMinecraftAmperes();
			}
		}
		return 0;
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
	public boolean canAcceptElectricPacket(ElectricPacket packet) {
		return isPowered == false;
	}

	@Override
	public boolean canSendElectricPacket(ElectricPacket packet) {
		return true;
	}

	@Override
	public void sendElectricPacket(ElectricPacket packet) {
		this.getNetwork().injectPacket(packet);
		this.isPowered = false;
		this.minecraftAmperes = 0;
	}

	@Override
	public void recieveElectricPacket(ElectricPacket packet) {
		this.isPowered = true;
		this.minecraftAmperes = packet.getMinecraftAmperes();
	}

	@Override
	public boolean doesTransferEnergy() {
		return true;
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
	
}
