package com.austindorff.mechanica.tileentity.machine;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;

public class TileMultiblockBasicSmelter extends TileEntity {
	
	private boolean	hasMaster, isMaster;
	private int		masterX, masterY, masterZ;
					
	public TileMultiblockBasicSmelter() {
	
	}
	
	@Override
	public void updateEntity() {
		super.updateEntity();
		if (!worldObj.isRemote) {
			if (hasMaster()) {
				if (isMaster()) {
				}
			} else {
				if (checkMultiBlockForm())
					setupStructure();
			}
		}
	}
	
	/** Check that structure is properly formed */
	public boolean checkMultiBlockForm() {
		int i = 0;
		int xMinChange = 0;
		int xMaxChange = 0;
		int yMinChange = 0;
		int yMaxChange = 0;
		int zMinChange = 0;
		int zMaxChange = 0;
		int xCoordMiddle = 0;
		int yCoordMiddle = 0;
		int zCoordMiddle = 0;
		int xDir = checkXDirectionOfMultiblock();
		int zDir = checkXDirectionOfMultiblock();
		int yDir = checkYDirectionOfMultiblock();
		switch (xDir) {
			// xNeg end
			case 0:
				xMinChange = 0;
				xMaxChange = 2;
				xCoordMiddle = xCoord + 1;
				break;
			case 1:
				xMinChange = -1;
				xMaxChange = 1;
				xCoordMiddle = xCoord;
				break;
			case 2:
				xMinChange = -2;
				xMaxChange = 0;
				xCoordMiddle = xCoord - 1;
				break;
		}
		switch (zDir) {
			case 0:
				zMinChange = 0;
				zMaxChange = 2;
				zCoordMiddle = zCoord + 1;
				break;
			case 1:
				zMinChange = -1;
				zMaxChange = 1;
				zCoordMiddle = zCoord;
				break;
			case 2:
				zMinChange = -2;
				zMaxChange = 0;
				zCoordMiddle = zCoord - 1;
				break;
		}
		switch (yDir) {
			case 0:
				yMinChange = 0;
				yMaxChange = 2;
				yCoordMiddle = yCoord + 1;
				break;
			case 1:
				yMinChange = -1;
				yMaxChange = 1;
				yCoordMiddle = yCoord;
				break;
			case 2:
				yMinChange = -2;
				yMaxChange = 0;
				yCoordMiddle = yCoord - 1;
				break;
				
		}
		for (int x = xCoord + xMinChange; x <= xCoord + xMaxChange; x++)
			for (int y = yCoord + yMinChange; y <= yCoord + yMaxChange; y++)
				for (int z = zCoord + zMinChange; z <= zCoord + zMaxChange; z++) {
					TileEntity tile = worldObj.getTileEntity(x, y, z);
					if (tile != null && (tile instanceof TileMultiblockBasicSmelter)) {
						i++;
					}
				}
		System.out.println(i);
		return i > 25 && worldObj.isAirBlock(xCoordMiddle, yCoordMiddle, zCoordMiddle);
	}
	
	private int checkYDirectionOfMultiblock() {
		if (worldObj.getTileEntity(xCoord, yCoord, zCoord) instanceof TileMultiblockBasicSmelter && worldObj.getTileEntity(xCoord, yCoord + 1, zCoord) instanceof TileMultiblockBasicSmelter && !(worldObj.getTileEntity(xCoord, yCoord - 1, zCoord) instanceof TileMultiblockBasicSmelter)) {
			return 0;
		} else if (worldObj.getTileEntity(xCoord, yCoord, zCoord) instanceof TileMultiblockBasicSmelter && worldObj.getTileEntity(xCoord, yCoord + 1, zCoord) instanceof TileMultiblockBasicSmelter && (worldObj.getTileEntity(xCoord, yCoord - 1, zCoord) instanceof TileMultiblockBasicSmelter)) {
			return 1;
		} else if (worldObj.getTileEntity(xCoord, yCoord, zCoord) instanceof TileMultiblockBasicSmelter && worldObj.getTileEntity(xCoord, yCoord - 1, zCoord) instanceof TileMultiblockBasicSmelter && !(worldObj.getTileEntity(xCoord, yCoord + 1, zCoord) instanceof TileMultiblockBasicSmelter)) {
			return 2;
		} else {
			return -1;
		}
	}
	
	private int checkXDirectionOfMultiblock() {
		
		if (worldObj.getTileEntity(xCoord, yCoord, zCoord) instanceof TileMultiblockBasicSmelter && worldObj.getTileEntity(xCoord + 1, yCoord, zCoord) instanceof TileMultiblockBasicSmelter && !(worldObj.getTileEntity(xCoord - 1, yCoord, zCoord) instanceof TileMultiblockBasicSmelter)) {
			return 0;
		} else if (worldObj.getTileEntity(xCoord, yCoord, zCoord) instanceof TileMultiblockBasicSmelter && worldObj.getTileEntity(xCoord + 1, yCoord, zCoord) instanceof TileMultiblockBasicSmelter && (worldObj.getTileEntity(xCoord - 1, yCoord, zCoord) instanceof TileMultiblockBasicSmelter)) {
			return 1;
		} else if (worldObj.getTileEntity(xCoord, yCoord, zCoord) instanceof TileMultiblockBasicSmelter && (worldObj.getTileEntity(xCoord - 1, yCoord, zCoord) instanceof TileMultiblockBasicSmelter) && !(worldObj.getTileEntity(xCoord + 1, yCoord, zCoord) instanceof TileMultiblockBasicSmelter)) {
			return 2;
		} else {
			return -1;
		}
	}
	
	private int checkZDirectionOfMultiblock() {
		
		if (worldObj.getTileEntity(xCoord, yCoord, zCoord) instanceof TileMultiblockBasicSmelter && worldObj.getTileEntity(xCoord, yCoord, zCoord + 1) instanceof TileMultiblockBasicSmelter && !(worldObj.getTileEntity(xCoord, yCoord, zCoord - 1) instanceof TileMultiblockBasicSmelter)) {
			return 0;
		} else if (worldObj.getTileEntity(xCoord, yCoord, zCoord) instanceof TileMultiblockBasicSmelter && worldObj.getTileEntity(xCoord, yCoord, zCoord + 1) instanceof TileMultiblockBasicSmelter && (worldObj.getTileEntity(xCoord, yCoord, zCoord - 1) instanceof TileMultiblockBasicSmelter)) {
			return 1;
		} else if (worldObj.getTileEntity(xCoord, yCoord, zCoord) instanceof TileMultiblockBasicSmelter && worldObj.getTileEntity(xCoord, yCoord, zCoord - 1) instanceof TileMultiblockBasicSmelter && !(worldObj.getTileEntity(xCoord, yCoord, zCoord + 1) instanceof TileMultiblockBasicSmelter)) {
			return 2;
		} 
		else {
			return -1;
		}
	}
	
	
	 	
	
	/** Setup all the blocks in the structure */
	public void setupStructure() {
		for (int x = xCoord - 1; x < xCoord + 2; x++)
			for (int y = yCoord; y < yCoord + 3; y++)
				for (int z = zCoord - 1; z < zCoord + 2; z++) {
					TileEntity tile = worldObj.getTileEntity(x, y, z);
					boolean master = (x == xCoord && y == yCoord && z == zCoord);
					if (tile != null && (tile instanceof TileMultiblockBasicSmelter)) {
						((TileMultiblockBasicSmelter) tile).setMasterCoords(xCoord, yCoord, zCoord);
						((TileMultiblockBasicSmelter) tile).setHasMaster(true);
						((TileMultiblockBasicSmelter) tile).setIsMaster(master);
					}
				}
	}
	
	public void reset() {
		masterX = 0;
		masterY = 0;
		masterZ = 0;
		hasMaster = false;
		isMaster = false;
	}
	
	public boolean checkForMaster() {
		TileEntity tile = worldObj.getTileEntity(masterX, masterY, masterZ);
		return (tile != null && (tile instanceof TileMultiblockBasicSmelter));
	}
	
	public void resetStructure() {
		for (int x = xCoord - 1; x < xCoord + 2; x++)
			for (int y = yCoord; y < yCoord + 3; y++)
				for (int z = zCoord - 1; z < zCoord + 2; z++) {
					TileEntity tile = worldObj.getTileEntity(x, y, z);
					if (tile != null && (tile instanceof TileMultiblockBasicSmelter))
						((TileMultiblockBasicSmelter) tile).reset();
				}
	}
	
	@Override
	public void writeToNBT(NBTTagCompound data) {
		super.writeToNBT(data);
		data.setInteger("masterX", masterX);
		data.setInteger("masterY", masterY);
		data.setInteger("masterZ", masterZ);
		data.setBoolean("hasMaster", hasMaster);
		data.setBoolean("isMaster", isMaster);
		if (hasMaster() && isMaster()) {
		}
	}
	
	@Override
	public void readFromNBT(NBTTagCompound data) {
		super.readFromNBT(data);
		masterX = data.getInteger("masterX");
		masterY = data.getInteger("masterY");
		masterZ = data.getInteger("masterZ");
		hasMaster = data.getBoolean("hasMaster");
		isMaster = data.getBoolean("isMaster");
		if (hasMaster() && isMaster()) {
		}
	}
	
	public boolean hasMaster() {
		return hasMaster;
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
}
