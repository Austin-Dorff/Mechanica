package com.austindorff.mechanica.block.machine;

import com.austindorff.mechanica.Mechanica;
import com.austindorff.mechanica.block.BlockContainerBase;
import com.austindorff.mechanica.network.GuiHandler;
import com.austindorff.mechanica.tileentity.machine.TileAdvancedFurnaceCasing;

import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class AdvancedFurnaceCasing extends BlockContainerBase {
	
	public static final IProperty<EnumType>	LOCATION	= PropertyEnum.create("location", AdvancedFurnaceCasing.EnumType.class);
	public static final IProperty<Boolean>	IS_ACTIVE	= PropertyBool.create("is_active");
														
	public AdvancedFurnaceCasing(String name) {
		super(name, Material.IRON, 0.9F, 0.5F);
		this.setDefaultState(this.blockState.getBaseState().withProperty(LOCATION, EnumType.DEFAULT).withProperty(AdvancedFurnaceCasing.IS_ACTIVE, false));
	}
	
	@Override
	public BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, new IProperty[] { LOCATION, IS_ACTIVE });
	}
	
	static final class SwitchEnumType {
		static final int[] LOCATION_LOOKUP = new int[AdvancedFurnaceCasing.EnumType.values().length];
		
		static {
			try {
				LOCATION_LOOKUP[AdvancedFurnaceCasing.EnumType.DEFAULT.ordinal()] = 1;
			} catch (NoSuchFieldError var6) {
				;
			}
			try {
				LOCATION_LOOKUP[AdvancedFurnaceCasing.EnumType.EDGE.ordinal()] = 2;
			} catch (NoSuchFieldError var6) {
				;
			}
			try {
				LOCATION_LOOKUP[AdvancedFurnaceCasing.EnumType.FURNACE.ordinal()] = 3;
			} catch (NoSuchFieldError var6) {
				;
			}
		}
	}
	
	@Override
	public int damageDropped(IBlockState state) {
		return getMetaFromState(state);
	}
	
	public static enum EnumType implements IStringSerializable {
		DEFAULT(0, "default"), EDGE(1, "edge"), FURNACE(2, "furnace");
		private static final AdvancedFurnaceCasing.EnumType[]	META_LOOKUP	= new AdvancedFurnaceCasing.EnumType[values().length];
		private final int										meta;
		private final String									name;
		private final String									unlocalizedName;
																
		private EnumType(int meta, String name) {
			this(meta, name, name);
		}
		
		private EnumType(int meta, String name, String unlocalizedName) {
			this.meta = meta;
			this.name = name;
			this.unlocalizedName = unlocalizedName;
		}
		
		public int getMetadata() {
			return this.meta;
		}
		
		public String toString() {
			return this.name;
		}
		
		public static AdvancedFurnaceCasing.EnumType byMetadata(int meta) {
			if (meta < 0 || meta >= META_LOOKUP.length) {
				meta = 0;
			}
			
			return META_LOOKUP[meta];
		}
		
		public String getName() {
			return this.name;
		}
		
		public String getUnlocalizedName() {
			return this.unlocalizedName;
		}
		
		static {
			AdvancedFurnaceCasing.EnumType[] var0 = values();
			int var1 = var0.length;
			
			for (int var2 = 0; var2 < var1; ++var2) {
				AdvancedFurnaceCasing.EnumType var3 = var0[var2];
				META_LOOKUP[var3.getMetadata()] = var3;
			}
		}
	}
	
	public IBlockState getStateFromMeta(int meta) {
		return this.getDefaultState().withProperty(LOCATION, EnumType.byMetadata(meta));
	}
	
	public int getMetaFromState(IBlockState state) {
		return ((EnumType) state.getValue(LOCATION)).getMetadata();
	}
	
	@Override
	public void onNeighborChange(IBlockAccess blockAccess, BlockPos coords, BlockPos neighbor) {
		super.onNeighborChange(blockAccess, coords, neighbor);
		TileAdvancedFurnaceCasing tile = ((TileAdvancedFurnaceCasing) blockAccess.getTileEntity(coords));
		if (!tile.checkMultiBlockForm()) {
			tile.resetStructure();
		}
	}
	
	@Override
	public void breakBlock(World world, BlockPos coords, IBlockState blockState) {
		TileAdvancedFurnaceCasing tile = ((TileAdvancedFurnaceCasing) world.getTileEntity(coords));
		super.breakBlock(world, coords, blockState);
		if (tile != null && !tile.checkMultiBlockForm()) {
			BlockPos pos = new BlockPos(tile.getMasterX(), tile.getMasterY(), tile.getMasterZ());
			TileAdvancedFurnaceCasing master = ((TileAdvancedFurnaceCasing) world.getTileEntity(pos));
			master.unloadFuel();
			InventoryHelper.dropInventoryItems(world, pos, master);
			master.resetStructure();
			
		}
	}
	
	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		return new TileAdvancedFurnaceCasing();
	}
	
	@Override
	public boolean onBlockActivated(World world, BlockPos coords, IBlockState blockState, EntityPlayer player, EnumHand hand, ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ) {
		super.onBlockActivated(world, coords, blockState, player, hand, heldItem, side, hitX, hitY, hitZ);
		if (!world.isRemote && ((TileAdvancedFurnaceCasing) world.getTileEntity(coords)).checkMultiBlockForm()) {
			BlockPos masterPos = ((TileAdvancedFurnaceCasing) world.getTileEntity(coords)).getMasterCoords();
			player.openGui(Mechanica.instance, GuiHandler.ADVANCED_FURNACE_CASING, world, masterPos.getX(), masterPos.getY(), masterPos.getZ());
			return true;
		}
		return false;
	}
	
	@Override
	public boolean isOpaqueCube(IBlockState state) {
		return false;
	}
	
}
