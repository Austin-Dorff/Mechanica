package com.austindorff.mechanica.tileentity;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;

public abstract class TileMultiblockBase extends TileBase implements ITickable {
	
	private boolean	hasMaster, isMaster;
	private int		masterX, masterY, masterZ;
					
	public TileMultiblockBase() {
		reset();
	}
	
	@Override
	public void update() {
		if (!worldObj.isRemote) {
			if (isMaster()) {
				tileBehavior();
			}
		}
	}
	
	public boolean isMasterYCoordValid(BlockPos masterCoords) {
		return masterCoords.getY() >= 0;
	}
	
	public void reset() {
		masterX = -1;
		masterY = -1;
		masterZ = -1;
		hasMaster = false;
		isMaster = false;
	}
	
	public boolean checkForMaster() {
		TileEntity tile = worldObj.getTileEntity(new BlockPos(new BlockPos(masterX, masterY, masterZ)));
		return (tile != null && isCorrectTileEntity(tile));
	}
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound data) {
		super.writeToNBT(data);
		return data;
	}
	
	@Override
	public void readFromNBT(NBTTagCompound data) {
		super.readFromNBT(data);
	}
	
	public boolean hasMaster() {
		return hasMaster || isMaster;
	}
	
	public boolean isMaster() {
		return isMaster;
	}
	
	public int getMasterX() {
		return masterX;
	}
	
	public int getMasterY() {
		return masterY;
	}
	
	public int getMasterZ() {
		return masterZ;
	}
	
	public void setHasMaster(boolean bool) {
		hasMaster = bool;
	}
	
	public void setIsMaster(boolean bool) {
		isMaster = bool;
	}
	
	public void setMasterCoords(int x, int y, int z) {
		masterX = x;
		masterY = y;
		masterZ = z;
	}
	
	public abstract void resetStructure();
	
	public abstract boolean isCorrectTileEntity(TileEntity tile);
	
	public abstract void tileBehavior();
	
	public abstract boolean checkMultiBlockForm();
	
	public abstract BlockPos getMasterCoords();
	
	public abstract void setupStructure(int xPos, int yPos, int zPos);
}
