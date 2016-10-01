package com.austindorff.mechanica.block.wire;

import com.austindorff.mechanica.block.BlockContainerBase;
import com.austindorff.mechanica.tileentity.machine.TileAdvancedFurnaceCasing;
import com.austindorff.mechanica.tileentity.wire.TileEntityWireBase;

import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockWireBase extends BlockContainerBase {
	
	public static final IProperty<Boolean>	UP		= PropertyBool.create("up");
	public static final IProperty<Boolean>	DOWN	= PropertyBool.create("down");
	public static final IProperty<Boolean>	NORTH	= PropertyBool.create("north");
	public static final IProperty<Boolean>	EAST	= PropertyBool.create("south");
	public static final IProperty<Boolean>	SOUTH	= PropertyBool.create("east");
	public static final IProperty<Boolean>	WEST	= PropertyBool.create("west");
													
	public BlockWireBase(String name, Material material, float hardness, float resistance) {
		super(name, material, hardness, resistance);
	}
	
	@Override
	public BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, new IProperty[] { UP, DOWN, NORTH, SOUTH, EAST, WEST });
	}
	
	public IBlockState getStateFromMeta(int meta) {
		return this.getDefaultState().withProperty(UP, false).withProperty(DOWN, false).withProperty(NORTH, false).withProperty(EAST, false).withProperty(SOUTH, false).withProperty(WEST, false);
	}
	
	public int getMetaFromState(IBlockState state) {
		return 0;
	}
	
	@Override
	public void onNeighborChange(IBlockAccess blockAccess, BlockPos coords, BlockPos neighbor) {
		super.onNeighborChange(blockAccess, coords, neighbor);
		TileEntityWireBase tile = ((TileEntityWireBase) blockAccess.getTileEntity(coords));
		tile.updateWireConnections();
	}
	
	@Override
	public boolean isOpaqueCube(IBlockState state) {
		return false;
	}
	
	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		return new TileEntityWireBase();
	}
	
	@Override
	public boolean isFullCube(IBlockState blockState) {
		return false;
	}
	
	@Override
	public boolean isVisuallyOpaque() {
		return false;
	}
	
	@Override
	public BlockRenderLayer getBlockLayer() {
		return BlockRenderLayer.TRANSLUCENT;
	}
	
}
