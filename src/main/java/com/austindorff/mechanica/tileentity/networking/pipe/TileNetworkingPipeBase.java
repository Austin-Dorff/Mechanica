package com.austindorff.mechanica.tileentity.networking.pipe;

import java.util.ArrayList;

import com.austindorff.mechanica.block.networking.pipe.BlockNetworkingPipe;
import com.austindorff.mechanica.tileentity.networking.TileNetworkingBlockBase;

import net.minecraft.block.Block;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;

public class TileNetworkingPipeBase extends TileNetworkingBlockBase {

	public TileNetworkingPipeBase() {
	}
	
	@Override
	public boolean isCorrectTileEntity(TileEntity tile) {
		return tile instanceof TileNetworkingPipeBase;
	}
	
	private BlockStateContainer getBlockStateContainer() {
		return this.worldObj.getBlockState(this.pos).getBlock().getBlockState();
	}
	
	@Override
	public void updateBlockState(BlockPos coords) {
		IBlockState state = this.worldObj.getBlockState(coords);
		Block block = state.getBlock();
		if (block instanceof BlockNetworkingPipe) {
			this.worldObj.notifyBlockUpdate(coords, state, ((BlockNetworkingPipe) block).getActualState(state, this.worldObj, coords), 3);
		}
	}

	public ArrayList<TileNetworkingPipeBase> getNeighboringHydraulicPipes() {
		ArrayList<TileNetworkingPipeBase> neighbors = new ArrayList<TileNetworkingPipeBase>();
		TileEntity[] neighborBaseTiles = this.getAllNeighbors();
		for (TileEntity tile : neighborBaseTiles) {
			if (tile instanceof TileNetworkingPipeBase) {
				neighbors.add(((TileNetworkingPipeBase) tile));
			} else {
				neighbors.add(null);
			}
		}
		return neighbors;
	}
	
}
