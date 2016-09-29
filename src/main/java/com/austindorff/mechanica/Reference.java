package com.austindorff.mechanica;

import java.util.HashMap;
import java.util.Map;

import com.austindorff.mechanica.block.MechanicaBlocks;
import com.austindorff.mechanica.network.packet.PacketHandler;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class Reference {
	
	public static void init() {
		int index = 0;
		for (String type : ORE_TYPES) {
			ORE_TYPES[index++] = normalizeName(type);
		}
		index = 0;
		for (String type : TREE_LOG_TYPES) {
			TREE_LOG_TYPES[index++] = normalizeName(type);
		}
		index = 0;
		for (String type : TREE_LEAVES_TYPES) {
			TREE_LEAVES_TYPES[index++] = normalizeName(type);
		}
		index = 0;
		for (String type : TREE_SAPLING_TYPES) {
			TREE_SAPLING_TYPES[index++] = normalizeName(type);
		}
		index = 0;
		for (String type : INGOT_TYPES) {
			INGOT_TYPES[index++] = normalizeName(type);
		}
	}
	
	// Mod Class
	public static final String			MOD_ID						= "mechanica";
	public static final String			MOD_NAME					= "Mechanica";
	public static final String			MOD_VERSION					= "0.0.1";
	public static SimpleNetworkWrapper	CHANNEL;
	public static PacketHandler			PACKET_HANDLER;
										
	// Proxies
	public static final String			COMMON_PROXY_LOCATION		= "com.austindorff.mechanica.network.proxy.CommonProxy";
	public static final String			SERVER_PROXY_LOCATION		= "com.austindorff.mechanica.network.proxy.ServerProxy";
	public static final String			CLIENT_PROXY_LOCATION		= "com.austindorff.mechanica.network.proxy.ClientProxy";
																	
	// Creative Tabs
	public static CreativeTabs			TAB_MECHANICA				= new CreativeTabs("Mechanica") {
																		@SideOnly(Side.CLIENT)
																		public Item getTabIconItem() {
																			return Item.getItemFromBlock(MechanicaBlocks.BLOCKS.get(Reference.ADVANCED_FURNACE_CASING));
																		}
																	};
																	
	// Blocks
	
	public static String				ADVANCED_FURNACE_CASING		= "Advanced Furnace Casing";
																	
	public static String				BLOCK_PREFIX				= "block";
																	
	public static String				COPPER_ORE_NAME				= "Copper Ore";
	public static String				TIN_ORE_NAME				= "Tin Ore";
	public static String				SILVER_ORE_NAME				= "Silver Ore";
	public static String				LEAD_ORE_NAME				= "Lead Ore";
																	
	public static String				RUBBER_TREE_LOG_NAME		= "Rubber Tree Log";
	public static String				RUBBER_TREE_LEAVES_NAME		= "Rubber Tree Leaves";
	public static String				RUBBER_TREE_SAPLING_NAME	= "Rubber Tree Sapling";
																	
	public static String[]				ORE_TYPES					= new String[] { COPPER_ORE_NAME, TIN_ORE_NAME, SILVER_ORE_NAME, LEAD_ORE_NAME };
																	
	public static String[]				TREE_LOG_TYPES				= new String[] { RUBBER_TREE_LOG_NAME };
	public static String[]				TREE_LEAVES_TYPES			= new String[] { RUBBER_TREE_LEAVES_NAME };
	public static String[]				TREE_SAPLING_TYPES			= new String[] { RUBBER_TREE_SAPLING_NAME };
																	
	public static Map<String, String>	BLOCK_UNLOCALIZED_NAMES		= new HashMap<String, String>();
																	
	public static Map<String, String>	BLOCK_REGISTRY_NAMES		= new HashMap<String, String>();
																	
	public static String addBlockUnlocalizedName(String name) {
		String formatted = getFormattedName(BLOCK_PREFIX, name);
		BLOCK_UNLOCALIZED_NAMES.put(name, formatted);
		return formatted;
	}
	
	public static String addBlockRegistryName(String name) {
		String formatted = getFormattedName(BLOCK_PREFIX, name);
		BLOCK_REGISTRY_NAMES.put(name, formatted);
		return formatted;
	}
	
	// Items
	
	public static String				ITEM_PREFIX				= "item";
																
	public static String				COPPER_INGOT_NAME		= "Copper Ingot";
	public static String				TIN_INGOT_NAME			= "Tin Ingot";
	public static String				SILVER_INGOT_NAME		= "Silver Ingot";
	public static String				LEAD_INGOT_NAME			= "Lead Ingot";
																
	public static String[]				INGOT_TYPES				= new String[] { COPPER_INGOT_NAME, TIN_INGOT_NAME, SILVER_INGOT_NAME, LEAD_INGOT_NAME };
																
	public static Map<String, String>	ITEM_UNLOCALIZED_NAMES	= new HashMap<String, String>();
																
	public static Map<String, String>	ITEM_REGISTRY_NAMES		= new HashMap<String, String>();
																
	public static String addItemUnlocalizedName(String name) {
		String formatted = getFormattedName(ITEM_PREFIX, name);
		ITEM_UNLOCALIZED_NAMES.put(name, formatted);
		return formatted;
	}
	
	public static String addItemRegistryName(String name) {
		String formatted = getFormattedName(ITEM_PREFIX, name);
		ITEM_REGISTRY_NAMES.put(name, formatted);
		return formatted;
	}
	
	// String Methods
	
	private static String getFormattedName(String prefix, String name) {
		return prefix + normalizeName(name);
	}
	
	private static String normalizeName(String name) {
		return name.replaceAll(" ", "");
	}
	
}
