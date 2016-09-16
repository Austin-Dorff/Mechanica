package com.austindorff.mechanica.blocks.world;

import java.util.List;

import com.austindorff.mechanica.Mechanica;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

public class BlockOres extends Block {
	
	@SideOnly(Side.CLIENT)
	private IIcon[] textures;
	
	public final static String[] ores = new String[] {"Copper", "Tin", "Silver", "Lead"};

	public BlockOres() {
		super(Material.rock);
		this.setHardness(3.0F);
		this.setResistance(5.0F);
		this.setCreativeTab(Mechanica.tabMechanica);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister iconRegister) {
		textures = new IIcon[ores.length];
		
		for (int i = 0; i < ores.length; i++) {
			textures[i] = iconRegister.registerIcon(Mechanica.MODID + ":" + ores[i] + "Ore");
		}
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void getSubBlocks(Item block, CreativeTabs creativeTabs, List list) {
		for (int i = 0; i < ores.length; i++) {
			list.add(new ItemStack(block, 1, i));
		}
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int meta) {
		return textures[meta];
	}
	
	@Override
	public int damageDropped(int meta) {
		return meta;
	}

}
