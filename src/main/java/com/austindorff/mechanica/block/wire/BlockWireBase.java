package com.austindorff.mechanica.block.wire;

import com.austindorff.mechanica.Reference;
import com.austindorff.mechanica.block.BlockContainerBase;
import com.austindorff.mechanica.energy.EnumResistance;
import com.austindorff.mechanica.energy.EnumVoltage;
import com.austindorff.mechanica.tileentity.energy.wire.TileEntityWire;

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

public class BlockWireBase extends BlockContainerBase {
	
	public static final IProperty<Boolean>		UP			= PropertyBool.create("up");
	public static final IProperty<Boolean>		DOWN		= PropertyBool.create("down");
	public static final IProperty<Boolean>		NORTH		= PropertyBool.create("north");
	public static final IProperty<Boolean>		EAST		= PropertyBool.create("east");
	public static final IProperty<Boolean>		SOUTH		= PropertyBool.create("south");
	public static final IProperty<Boolean>		WEST		= PropertyBool.create("west");
	public static final IProperty<EnumWireType>	WIRE_TYPE	= PropertyEnum.create("wire_type", BlockWireBase.EnumWireType.class);
																											
	public BlockWireBase(String regName, String unlocName, Material material, float hardness, float resistance) {
		super(regName, unlocName, material, hardness, resistance);
		this.setDefaultState(getDefaultState().withProperty(WIRE_TYPE, EnumWireType.COPPER).withProperty(UP, false).withProperty(DOWN, false).withProperty(NORTH, false).withProperty(EAST, false).withProperty(SOUTH, false).withProperty(WEST, false));
	}
	
	public enum EnumWireType implements IStringSerializable {
		COPPER("copper", false, true, EnumVoltage.TIER_ONE, EnumVoltage.TIER_TWO, EnumResistance.TIER_ONE);
		private EnumVoltage	minVoltage, maxVoltage;
		private EnumResistance resistance;
		private boolean		hasMinVoltage, hasMaxVoltage;
		private String		name;
							
		private EnumWireType(String name, boolean hasMinVoltage, boolean hasMaxVoltage, EnumVoltage minVoltage, EnumVoltage maxVoltage, EnumResistance resistance) {
			this.name = name.substring(0, 1).toUpperCase() + name.substring(1);
			this.hasMaxVoltage = hasMaxVoltage;
			this.hasMinVoltage = hasMinVoltage;
			this.maxVoltage = maxVoltage;
			this.minVoltage = minVoltage;
			this.resistance = resistance;
		}
		
		public static int wireTypeToMeta(EnumWireType enumType) {
			switch(enumType) {
				case COPPER: {
					return 0;
				}
			}
			return 0;
		}
		
		public static EnumWireType metaToWireType(int meta) {
			switch(meta) {
				case 0: {
					return COPPER;
				}
			}
			return COPPER;
		}
		
		public boolean hasMaxVoltage() {
			return this.hasMaxVoltage;
		}
		
		public boolean hasMinVoltage() {
			return this.hasMaxVoltage;
		}
		
		public EnumVoltage maxVoltage() {
			return this.maxVoltage;
		}
		
		public EnumVoltage minVoltage() {
			return this.minVoltage;
		}
		
		public EnumResistance resistance() {
			return this.resistance;
		}
		
		public String getRegistryName() {
			return Reference.MOD_ID + ":" + this.getUnlocalizedName();
		}
		
		public String getUnlocalizedName() {
			return "block" + this.name + "Wire";
		}
		
		@Override
		public String getName() {
			return this.name.toLowerCase();
		}
	}
	
	@Override
	public BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, new IProperty[] { UP, DOWN, NORTH, SOUTH, EAST, WEST, WIRE_TYPE });
	}
	
	public IBlockState getStateFromMeta(int meta) {
		return this.getDefaultState().withProperty(WIRE_TYPE, EnumWireType.metaToWireType(meta));
	}
	
	public int getMetaFromState(IBlockState state) {
		IProperty<EnumWireType> property = ((IProperty<EnumWireType>) getBlockState().getProperty("wire_type"));
		switch (property.getName()) {
			case "Copper": {
				return EnumWireType.wireTypeToMeta(EnumWireType.COPPER);
			}
		}
		return EnumWireType.wireTypeToMeta(EnumWireType.COPPER);
	}
	
	@Override
	public IBlockState getActualState(IBlockState blockState, IBlockAccess blockAccess, BlockPos coords) {
		IBlockState state = this.getDefaultState();
		TileEntityWire tile = ((TileEntityWire) blockAccess.getTileEntity(coords));
		state = state.withProperty(this.UP, tile.isNeighborUp());
		state = state.withProperty(this.DOWN, tile.isNeighborDown());
		state = state.withProperty(this.NORTH, tile.isNeighborNorth());
		state = state.withProperty(this.EAST, tile.isNeighborEast());
		state = state.withProperty(this.SOUTH, tile.isNeighborSouth());
		state = state.withProperty(this.WEST, tile.isNeighborWest());
		return state;
	}
	
	@Override
	public void onNeighborChange(IBlockAccess blockAccess, BlockPos coords, BlockPos neighbor) {
		super.onNeighborChange(blockAccess, coords, neighbor);
		((TileEntityWire) blockAccess.getTileEntity(coords)).updateConnections();
	}
	
	@Override
	public boolean isOpaqueCube(IBlockState state) {
		return false;
	}
	
	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		return new TileEntityWire();
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