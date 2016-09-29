package com.austindorff.mechanica.network.proxy;

import com.austindorff.mechanica.Reference;
import com.austindorff.mechanica.network.packet.PacketHandler;

import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class CommonProxy {
	
	public void preInit(FMLPreInitializationEvent e) {
		PacketHandler.registerMessages(Reference.MOD_ID);
	}
	
	public void init(FMLInitializationEvent e) {
	}
	
	public void postInit(FMLPostInitializationEvent e) {
	}
}
