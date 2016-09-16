package com.austindorff.mechanica.items;

import com.austindorff.mechanica.Mechanica;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.Item;

public class MechanicaSimpleItem extends Item {
	
	public MechanicaSimpleItem() {
		this.setCreativeTab(Mechanica.tabMechanica);
	}
	
	
	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister iconRegister) {
		this.itemIcon = iconRegister.registerIcon(Mechanica.MODID + ":" + this.getUnlocalizedName().substring(5));
	}

}
