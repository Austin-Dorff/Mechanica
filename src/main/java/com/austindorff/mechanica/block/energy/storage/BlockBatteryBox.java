package com.austindorff.mechanica.block.energy.storage;

import com.austindorff.mechanica.Mechanica;
import com.austindorff.mechanica.Reference;
import com.austindorff.mechanica.block.BlockContainerBase;
import com.austindorff.mechanica.network.GuiHandler;
import com.austindorff.mechanica.tileentity.advancedfurnacecasing.TileEntityAdvancedFurnaceCasing;
import com.austindorff.mechanica.tileentity.energy.storage.batterybox.TileEntityBatteryBox;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockBatteryBox extends BlockContainerBase {

	public static String UNLOC_NAME = "blockBatteryBox";
	public static String REG_NAME = Reference.MOD_ID + ":" + BlockBatteryBox.UNLOC_NAME;
	
	public BlockBatteryBox(String regName, String unlocName) {
		super(regName, unlocName, Material.WOOD, 9.0F, 9.0F);
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		return new TileEntityBatteryBox();
	}

	@Override
	public boolean isOpaqueCube(IBlockState state) {
		return false;
	}
	
	@Override
	public boolean onBlockActivated(World world, BlockPos coords, IBlockState blockState, EntityPlayer player, EnumHand hand, ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ) {
		super.onBlockActivated(world, coords, blockState, player, hand, heldItem, side, hitX, hitY, hitZ);
		if (!world.isRemote) {
			System.out.println(((TileEntityBatteryBox) world.getTileEntity(coords)).getEnergyStored());
			return true;
		}
		return false;
	}

}
