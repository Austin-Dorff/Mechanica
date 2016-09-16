package com.austindorff.mechanica.items;

import static com.austindorff.mechanica.items.MechanicaItemDeclaration.itemCopperIngot;
import static com.austindorff.mechanica.items.MechanicaItemDeclaration.itemLeadIngot;
import static com.austindorff.mechanica.items.MechanicaItemDeclaration.itemSilverIngot;
import static com.austindorff.mechanica.items.MechanicaItemDeclaration.itemTinIngot;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.item.Item;

public class MechanicaItems {
	
	public static void init() {
		registerItems();
	}
	
	public static void registerItems() {
		itemCopperIngot = registerItem(new MechanicaSimpleItem().setUnlocalizedName("CopperIngot"));
		itemTinIngot = registerItem(new MechanicaSimpleItem().setUnlocalizedName("TinIngot"));
		itemSilverIngot = registerItem(new MechanicaSimpleItem().setUnlocalizedName("SilverIngot"));
		itemLeadIngot = registerItem(new MechanicaSimpleItem().setUnlocalizedName("LeadIngot"));
	}
	
	public static Item registerItem(Item item) {
		GameRegistry.registerItem(item, item.getUnlocalizedName().substring(5));
		return item;
	}

}
