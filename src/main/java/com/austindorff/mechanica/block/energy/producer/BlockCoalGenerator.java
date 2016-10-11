package com.austindorff.mechanica.block.energy.producer;

import com.austindorff.mechanica.Mechanica;
import com.austindorff.mechanica.Reference;
import com.austindorff.mechanica.block.BlockContainerBase;
import com.austindorff.mechanica.network.GuiHandler;
import com.austindorff.mechanica.tileentity.advancedfurnacecasing.TileEntityAdvancedFurnaceCasing;
import com.austindorff.mechanica.tileentity.energy.producer.coalgenerator.TileEntityCoalGenerator;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockCoalGenerator extends BlockContainerBase {
	
	public static String UNLOC_NAME = "blockCoalGenerator";
	public static String REG_NAME = Reference.MOD_ID + ":" + BlockCoalGenerator.UNLOC_NAME;

	public BlockCoalGenerator(String regName, String unlocName) {
		super(regName, unlocName, Material.IRON, 9.0F, 9.0F);
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		return new TileEntityCoalGenerator();
	}

	@Override
	public boolean isOpaqueCube(IBlockState state) {
		return false;
	}
	
	@Override
	public boolean onBlockActivated(World world, BlockPos coords, IBlockState blockState, EntityPlayer player, EnumHand hand, ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ) {
		super.onBlockActivated(world, coords, blockState, player, hand, heldItem, side, hitX, hitY, hitZ);
		if (!world.isRemote) {
			System.out.println(((TileEntityCoalGenerator) world.getTileEntity(coords)).getField(2));
			player.openGui(Mechanica.instance, GuiHandler.COAL_GENERATOR, world, coords.getX(), coords.getY(), coords.getZ());
			return true;
		}
		return false;
	}

}
