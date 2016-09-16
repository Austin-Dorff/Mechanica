package com.austindorff.mechanica.blocks.world;

import java.util.List;
import java.util.Random;

import com.austindorff.mechanica.Mechanica;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLeaves;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

public class BlockMechanicaLeaves extends BlockLeaves {
	int[]							field_150128_a;
	@SideOnly(Side.CLIENT)
	private IIcon[]					textures;
									
	public final static String[]	leaves	= new String[] { "Rubber" };
											
	public BlockMechanicaLeaves() {
		this.setTickRandomly(true);
		this.setCreativeTab(Mechanica.tabMechanica);
		this.setHardness(0.2F);
		this.setLightOpacity(1);
		this.setStepSound(soundTypeGrass);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister iconRegister) {
		textures = new IIcon[leaves.length];
		
		for (int i = 0; i < leaves.length; i++) {
			textures[i] = iconRegister.registerIcon(Mechanica.MODID + ":" + leaves[i] + "TreeLeaves");
		}
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void getSubBlocks(Item block, CreativeTabs creativeTabs, List list) {
		for (int i = 0; i < leaves.length; i++) {
			list.add(new ItemStack(block, 1, i));
		}
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int meta) {
		if (meta < leaves.length) {
			return textures[meta];
		} else {
			return null;
		}
	}
	
	@Override
	public int damageDropped(int meta) {
		return meta;
	}
	
	@Override
	public int quantityDropped(Random rand) {
		return rand.nextInt(20) == 0 ? 1 : 0;
	}
	
	@Override
	public String[] func_150125_e() {
		return leaves;
	}
	
	@Override
	public boolean isOpaqueCube() {
		return false;
	}
	
    @Override
    public void beginLeavesDecay(World world, int x, int y, int z)
    {
    }
}