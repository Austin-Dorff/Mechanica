package com.austindorff.mechanica.blocks.machine;

import com.austindorff.mechanica.Mechanica;
import com.austindorff.mechanica.network.GuiHandler;
import com.austindorff.mechanica.tileentity.machine.TileMultiblockBasicSmelter;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

public class BasicSmelter extends BlockContainer {
	
	@SideOnly(Side.CLIENT)
	private IIcon texture;
	
	public BasicSmelter(Material material) {
		super(material);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int meta) {
		return texture;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister iconRegister) {
		texture = iconRegister.registerIcon(Mechanica.MODID + ":" + "BasicMachineCasing");
	}
	
	@Override
	public void onNeighborBlockChange(World world, int x, int y, int z, Block block) {
		TileEntity tile = world.getTileEntity(x, y, z);
		if (tile != null && tile instanceof TileMultiblockBasicSmelter) {
			System.out.println("TEST-1");
			TileMultiblockBasicSmelter multiBlock = (TileMultiblockBasicSmelter) tile;
			if (multiBlock.hasMaster()) {
				System.out.println("TEST0");
				if (multiBlock.isMaster()) {
					System.out.println("TEST1");
					if (!multiBlock.checkMultiBlockForm()) {
						System.out.println("TEST2");
						multiBlock.resetStructure();
					} else {
						System.out.println("TEST3");
						multiBlock.setupStructure();
					}
				} else {
					System.out.println("TEST4");
					if (!multiBlock.checkMultiBlockForm()) {
						System.out.println("TEST5");
						multiBlock.resetStructure();
					} else {
						System.out.println("TEST6");
						multiBlock.setupStructure();
					}
				}
			}
			else {
				System.out.println("TEST7");
				if (!multiBlock.checkMultiBlockForm()) {
					System.out.println("TEST8");
					multiBlock.resetStructure();
				} else {
					System.out.println("TEST9");
					multiBlock.setupStructure();
				}
			}
		}
		super.onNeighborBlockChange(world, x, y, z, block);
	}
	
	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		return new TileMultiblockBasicSmelter();
	}
	
	@Override
	public boolean onBlockActivated(World world, int posX, int posY, int posZ, EntityPlayer player, int p_149727_6_, float p_149727_7_, float p_149727_8_, float p_149727_9_) {
		if (!world.isRemote && ((TileMultiblockBasicSmelter) world.getTileEntity(posX, posY, posZ)).checkMultiBlockForm()) {
			player.openGui(Mechanica.instance, GuiHandler.BASIC_MACHINE_CASING_MULTIBLOCK, world, posX, posY, posZ);
			return true;
		} else {
			return false;
		}
		
	}
	
}
