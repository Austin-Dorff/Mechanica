package com.austindorff.mechanica;

import java.util.HashMap;
import java.util.Map;

import com.austindorff.mechanica.block.MechanicaBlocks;
import com.austindorff.mechanica.block.machine.BlockAdvancedFurnaceCasing;
import com.austindorff.mechanica.network.packet.PacketHandler;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class Reference {
	
	// Mod Class
	public static final String			MOD_ID							= "mechanica";
	public static final String			MOD_NAME						= "Mechanica";
	public static final String			MOD_VERSION						= "0.0.1";
	public static SimpleNetworkWrapper	CHANNEL;
	public static PacketHandler			PACKET_HANDLER;
										
	// Proxies
	public static final String			COMMON_PROXY_LOCATION			= "com.austindorff.mechanica.network.proxy.CommonProxy";
	public static final String			SERVER_PROXY_LOCATION			= "com.austindorff.mechanica.network.proxy.ServerProxy";
	public static final String			CLIENT_PROXY_LOCATION			= "com.austindorff.mechanica.network.proxy.ClientProxy";
																		
	// Creative Tabs
	public static CreativeTabs			TAB_MECHANICA					= new CreativeTabs("Mechanica") {
																			@SideOnly(Side.CLIENT)
																			public Item getTabIconItem() {
																				return Item.getItemFromBlock(MechanicaBlocks.BLOCKS.get(BlockAdvancedFurnaceCasing.NAME));
																			}
																		};
												
	
}
