package com.austindorff.mechanica.tileentity.connectable;

import com.austindorff.mechanica.tileentity.TileBase;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;

public abstract class TileEntityConnectable extends TileBase {
		
	@Override
	public abstract boolean isCorrectTileEntity(TileEntity tile);
	
	public abstract void updateBlockState(BlockPos coords);
	
	public void updateConnections() {
		updateBlockState(pos);
		if (isNeighborUp()) {
			updateBlockState(getNeighborUp().getPos());
		}
		if (isNeighborDown()) {
			updateBlockState(getNeighborDown().getPos());
		}
		if (isNeighborNorth()) {
			updateBlockState(getNeighborNorth().getPos());
		}
		if (isNeighborEast()) {
			updateBlockState(getNeighborEast().getPos());
		}
		if (isNeighborSouth()) {
			updateBlockState(getNeighborSouth().getPos());
		}
		if (isNeighborWest()) {
			updateBlockState(getNeighborWest().getPos());
		}
	}

}
