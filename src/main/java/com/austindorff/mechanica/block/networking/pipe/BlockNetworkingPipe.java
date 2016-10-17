package com.austindorff.mechanica.block.networking.pipe;

import com.austindorff.mechanica.Reference;
import com.austindorff.mechanica.block.BlockContainerBase;
import com.austindorff.mechanica.tileentity.networking.pipe.TileNetworkingPipeBase;
import com.austindorff.mechanica.tileentity.networking.pipe.stone.TileStoneNetworkingPipe;

import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockNetworkingPipe extends BlockContainerBase {
	
	public static final IProperty<Boolean>					UP						= PropertyBool.create("up");
	public static final IProperty<Boolean>					DOWN					= PropertyBool.create("down");
	public static final IProperty<Boolean>					NORTH					= PropertyBool.create("north");
	public static final IProperty<Boolean>					EAST					= PropertyBool.create("east");
	public static final IProperty<Boolean>					SOUTH					= PropertyBool.create("south");
	public static final IProperty<Boolean>					WEST					= PropertyBool.create("west");
	public static final IProperty<Boolean>					RENDER_CORE				= PropertyBool.create("render_core");
	public static final IProperty<EnumNetworkingPipeType>	NETWORKING_PIPE_TYPE	= PropertyEnum.create("type", BlockNetworkingPipe.EnumNetworkingPipeType.class);
																					
	private EnumNetworkingPipeType							type;
															
	public BlockNetworkingPipe(String regName, String unlocName, Material material, float hardness, float resistance, EnumNetworkingPipeType type) {
		super(regName, unlocName, material, hardness, resistance);
		this.setDefaultState(getDefaultState().withProperty(NETWORKING_PIPE_TYPE, EnumNetworkingPipeType.STONE).withProperty(UP, false).withProperty(DOWN, false).withProperty(NORTH, false).withProperty(EAST, false).withProperty(SOUTH, false).withProperty(WEST, false).withProperty(this.RENDER_CORE, true));
		this.type = type;
	}
	
	public enum EnumNetworkingPipeType implements IStringSerializable {
		STONE("stone");
		private String name;
		
		private EnumNetworkingPipeType(String name) {
			this.name = name.substring(0, 1).toUpperCase() + name.substring(1);
		}
		
		public static int networkingPipeTypeToMeta(EnumNetworkingPipeType enumType) {
			switch (enumType) {
				case STONE: {
					return 0;
				}
			}
			return 0;
		}
		
		public static EnumNetworkingPipeType metaToNetworkingPipeType(int meta) {
			switch (meta) {
				case 0: {
					return STONE;
				}
			}
			return STONE;
		}
		
		public String getRegistryName() {
			return Reference.MOD_ID + ":" + this.getUnlocalizedName();
		}
		
		public String getUnlocalizedName() {
			return "block" + this.name + "NetworkingPipe";
		}
		
		@Override
		public String getName() {
			return this.name.toLowerCase();
		}
	}
	
	@Override
	public BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, new IProperty[] { UP, DOWN, NORTH, SOUTH, EAST, WEST, NETWORKING_PIPE_TYPE, RENDER_CORE });
	}
	
	public IBlockState getStateFromMeta(int meta) {
		return this.getDefaultState().withProperty(NETWORKING_PIPE_TYPE, EnumNetworkingPipeType.metaToNetworkingPipeType(meta));
	}
	
	public int getMetaFromState(IBlockState state) {
		IProperty<EnumNetworkingPipeType> property = ((IProperty<EnumNetworkingPipeType>) getBlockState().getProperty("type"));
		switch (property.getName()) {
			case "stone": {
				return EnumNetworkingPipeType.networkingPipeTypeToMeta(EnumNetworkingPipeType.STONE);
			}
		}
		return EnumNetworkingPipeType.networkingPipeTypeToMeta(EnumNetworkingPipeType.STONE);
	}
	
	@Override
	public IBlockState getActualState(IBlockState blockState, IBlockAccess blockAccess, BlockPos coords) {
		IBlockState state = this.getDefaultState();
		TileNetworkingPipeBase tile = ((TileNetworkingPipeBase) blockAccess.getTileEntity(coords));
		state = state.withProperty(this.UP, tile.isConnectableNeighborUp());
		state = state.withProperty(this.DOWN, tile.isConnectableNeighborDown());
		state = state.withProperty(this.NORTH, tile.isConnectableNeighborNorth());
		state = state.withProperty(this.EAST, tile.isConnectableNeighborEast());
		state = state.withProperty(this.SOUTH, tile.isConnectableNeighborSouth());
		state = state.withProperty(this.WEST, tile.isConnectableNeighborWest());
		state = state.withProperty(this.RENDER_CORE, onlyOneDirectionConnection(tile));
		return state;
	}
	
	private boolean onlyOneDirectionConnection(TileNetworkingPipeBase tile) {
		if (tile.isConnectableNeighborDown() && tile.isConnectableNeighborUp()) {
			if (tile.isConnectableNeighborNorth() || tile.isConnectableNeighborEast() || tile.isConnectableNeighborSouth() || tile.isConnectableNeighborWest()) {
				return true;
			} else {
				return false;
			}
		} else if (tile.isConnectableNeighborNorth() && tile.isConnectableNeighborSouth()) {
			if (tile.isConnectableNeighborUp() || tile.isConnectableNeighborEast() || tile.isConnectableNeighborDown() || tile.isConnectableNeighborWest()) {
				return true;
			} else {
				return false;
			}
		} else if (tile.isConnectableNeighborEast() && tile.isConnectableNeighborWest()) {
			if (tile.isConnectableNeighborUp() || tile.isConnectableNeighborNorth() || tile.isConnectableNeighborDown() || tile.isConnectableNeighborSouth()) {
				return true;
			} else {
				return false;
			}
		}
		return true;
	}

	@Override
	public void onNeighborChange(IBlockAccess blockAccess, BlockPos coords, BlockPos neighbor) {
		super.onNeighborChange(blockAccess, coords, neighbor);
		((TileNetworkingPipeBase) blockAccess.getTileEntity(coords)).updateConnections();
	}
	
	@Override
	public boolean isOpaqueCube(IBlockState state) {
		return false;
	}
	
	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		switch (this.type) {
			case STONE: {
				return new TileStoneNetworkingPipe();
			}
		}
		return null;
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
	
	@Override
	public AxisAlignedBB getBoundingBox(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos) {
		return new AxisAlignedBB(new Vec3d(0.31, 0.31, 0.31), new Vec3d(0.685, 0.685, 0.685));
	}
	
	@Override
	public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, World world, BlockPos coords) {
		return new AxisAlignedBB(new Vec3d(0.31, 0.31, 0.31), new Vec3d(0.685, 0.685, 0.685));
	}
	
}
