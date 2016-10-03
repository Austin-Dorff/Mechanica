package com.austindorff.mechanica.tileentity;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;

public abstract class TileEntityBase extends TileEntity {
	
	public abstract boolean isCorrectTileEntity(TileEntity tile);
	
	public TileEntity getNeighborUp() {
		return this.worldObj.getTileEntity(new BlockPos(pos.getX(), pos.getY() + 1, pos.getZ()));
	}
	
	public TileEntity getNeighborDown() {
		return this.worldObj.getTileEntity(new BlockPos(pos.getX(), pos.getY() - 1, pos.getZ()));
	}
	
	public TileEntity getNeighborNorth() {
		return this.worldObj.getTileEntity(new BlockPos(pos.getX(), pos.getY(), pos.getZ() - 1));
	}
	
	public TileEntity getNeighborEast() {
		return this.worldObj.getTileEntity(new BlockPos(pos.getX() + 1, pos.getY(), pos.getZ()));
	}
	
	public TileEntity getNeighborSouth() {
		return this.worldObj.getTileEntity(new BlockPos(pos.getX(), pos.getY(), pos.getZ() + 1));
	}
	
	public TileEntity getNeighborWest() {
		return this.worldObj.getTileEntity(new BlockPos(pos.getX() - 1, pos.getY(), pos.getZ()));
	}
	
	public boolean isNeighborNorth() {
		return getNeighborNorth() != null && isCorrectTileEntity(getNeighborNorth());
	}
	
	public boolean isNeighborEast() {
		return getNeighborEast() != null && isCorrectTileEntity(getNeighborEast());
	}
	
	public boolean isNeighborSouth() {
		return getNeighborSouth() != null && isCorrectTileEntity(getNeighborSouth());
	}
	
	public boolean isNeighborWest() {
		return getNeighborWest() != null && isCorrectTileEntity(getNeighborWest());
	}
	
	public boolean isNeighborUp() {
		return getNeighborUp() != null && isCorrectTileEntity(getNeighborUp());
	}
	
	public boolean isNeighborDown() {
		return getNeighborDown() != null && isCorrectTileEntity(getNeighborDown());
	}
	
	public boolean isNeighborNorthEast() {
		return worldObj.getTileEntity(new BlockPos(pos.getX() + 1, pos.getY(), pos.getZ() - 1)) != null && isCorrectTileEntity(worldObj.getTileEntity(new BlockPos(pos.getX() + 1, pos.getY(), pos.getZ() - 1)));
	}
	
	public boolean isNeighborNorthWest() {
		return worldObj.getTileEntity(new BlockPos(pos.getX() - 1, pos.getY(), pos.getZ() - 1)) != null && isCorrectTileEntity(worldObj.getTileEntity(new BlockPos(pos.getX() - 1, pos.getY(), pos.getZ() - 1)));
	}
	
	public boolean isNeighborSouthEast() {
		return worldObj.getTileEntity(new BlockPos(pos.getX() + 1, pos.getY(), pos.getZ() + 1)) != null && isCorrectTileEntity(worldObj.getTileEntity(new BlockPos(pos.getX() + 1, pos.getY(), pos.getZ() + 1)));
	}
	
	public boolean isNeighborSouthWest() {
		return worldObj.getTileEntity(new BlockPos(pos.getX() - 1, pos.getY(), pos.getZ() + 1)) != null && isCorrectTileEntity(worldObj.getTileEntity(new BlockPos(pos.getX() - 1, pos.getY(), pos.getZ() + 1)));
	}
	
	public boolean isNeighborX(int direction) {
		return worldObj.getTileEntity(new BlockPos(pos.getX() + direction, pos.getY(), pos.getZ())) != null && isCorrectTileEntity(worldObj.getTileEntity(new BlockPos(pos.getX() + direction, pos.getY(), pos.getZ())));
	}
	
	public boolean isNeighborY(int direction) {
		return worldObj.getTileEntity(new BlockPos(pos.getX(), pos.getY() + direction, pos.getZ())) !=  null && isCorrectTileEntity(worldObj.getTileEntity(new BlockPos(pos.getX(), pos.getY() + direction, pos.getZ())));
	}
	
	public boolean isNeighborZ(int direction) {
		return worldObj.getTileEntity(new BlockPos(pos.getX(), pos.getY(), pos.getZ() + direction)) != null && isCorrectTileEntity(worldObj.getTileEntity(new BlockPos(pos.getX(), pos.getY(), pos.getZ() + direction)));
	}
}
