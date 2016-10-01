package com.austindorff.mechanica.tileentity.wire;

import com.austindorff.mechanica.Reference;
import com.austindorff.mechanica.block.MechanicaBlocks;
import com.austindorff.mechanica.block.wire.BlockWireBase;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;

public class TileEntityWireBase extends TileEntity {
	
	public boolean isCorrectTileEntity(TileEntity tile) {
		return tile instanceof TileEntityWireBase;
	}
	
	public boolean isNeighborNorth() {
		return (this.pos.getZ() >= (-1 * this.worldObj.getWorldBorder().getSize())) && isNeighborZ(-1);
	}
	
	public boolean isNeighborSouth() {
		return (this.pos.getZ() <= (this.worldObj.getWorldBorder().getSize())) && isNeighborZ(1);
	}
	
	public boolean isNeighborEast() {
		return (this.pos.getX() <= (this.worldObj.getWorldBorder().getSize())) && isNeighborX(1);
	}
	
	public boolean isNeighborWest() {
		return (this.pos.getX() >= (-1 * this.worldObj.getWorldBorder().getSize())) && isNeighborX(-1);
	}
	
	public boolean isNeighborUp() {
		return (this.pos.getY() <= this.worldObj.getHeight()) && isNeighborY(1);
	}
	
	public boolean isNeighborDown() {
		return (this.pos.getY() - 1 >= 0) && isNeighborY(-1);
	}
	
	public boolean isNeighborX(int direction) {
		return (this.pos.getX() + direction <= (this.worldObj.getWorldBorder().getSize())) && isCorrectTileEntity(worldObj.getTileEntity(new BlockPos(pos.getX() + direction, pos.getY(), pos.getZ())));
	}
	
	public boolean isNeighborY(int direction) {
		return (pos.getY() + direction >= 0) && isCorrectTileEntity(worldObj.getTileEntity(new BlockPos(pos.getX(), pos.getY() + direction, pos.getZ())));
	}
	
	public boolean isNeighborZ(int direction) {
		return (this.pos.getZ() + direction <= (this.worldObj.getWorldBorder().getSize())) && isCorrectTileEntity(worldObj.getTileEntity(new BlockPos(pos.getX(), pos.getY(), pos.getZ() + direction)));
	}

	public void updateWireConnections() {
		worldObj.setBlockState(pos, MechanicaBlocks.BLOCKS.get(Reference.COPPER_WIRE_NAME).getDefaultState().withProperty(BlockWireBase.UP, isNeighborUp()).withProperty(BlockWireBase.DOWN, isNeighborDown()).withProperty(BlockWireBase.NORTH, isNeighborNorth()).withProperty(BlockWireBase.EAST, isNeighborEast()).withProperty(BlockWireBase.SOUTH, isNeighborSouth()).withProperty(BlockWireBase.WEST, isNeighborWest()));
		if (isNeighborUp()) {
			((TileEntityWireBase) worldObj.getTileEntity(new BlockPos(pos.getX(), pos.getY() + 1, pos.getZ()))).updateWireConnections();
		}
		if (isNeighborDown()) {
			((TileEntityWireBase) worldObj.getTileEntity(new BlockPos(pos.getX(), pos.getY() - 1, pos.getZ()))).updateWireConnections();
		}
		if (isNeighborNorth()) {
			((TileEntityWireBase) worldObj.getTileEntity(new BlockPos(pos.getX(), pos.getY(), pos.getZ() - 1))).updateWireConnections();
		}
		if (isNeighborEast()) {
			((TileEntityWireBase) worldObj.getTileEntity(new BlockPos(pos.getX() + 1, pos.getY(), pos.getZ()))).updateWireConnections();
		}
		if (isNeighborSouth()) {
			((TileEntityWireBase) worldObj.getTileEntity(new BlockPos(pos.getX(), pos.getY(), pos.getZ() + 1))).updateWireConnections();
		}
		if (isNeighborWest()) {
			((TileEntityWireBase) worldObj.getTileEntity(new BlockPos(pos.getX() - 1, pos.getY(), pos.getZ()))).updateWireConnections();
		}
	}

}
