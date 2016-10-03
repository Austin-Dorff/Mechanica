package com.austindorff.mechanica.item;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class MechanicaItems {
	
	public static Map<String, Item> ITEMS = new HashMap<String, Item>();
	
	public static void init() {
		initSubItemClasses();
		initSingletonItemClasses();
		registerItems();
	}
	
	public static void registerItems() {
		for (Item item : ITEMS.values()) {
			GameRegistry.register(item);
		}
	}
	
	public static void registerRenderers() {
		for (Item item : ITEMS.values()) {
			ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(item.getRegistryName(), "inventory"));
		}
	}
	
	private static void initSingletonItemClasses() {

	}

	private static void initSubItemClasses() {
		// Metal Ingots
		for (ItemIngot.EnumIngotType ingotType : ItemIngot.EnumIngotType.values()) {
			MechanicaItems.ITEMS.put(ingotType.getName(), new ItemIngot(ingotType));
		}
	}

}
