package com.austindorff.mechanica.network.proxy;

import com.austindorff.mechanica.block.MechanicaBlocks;
import com.austindorff.mechanica.item.MechanicaItems;

import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class ClientProxy extends CommonProxy {
	
	@Override
	public void preInit(FMLPreInitializationEvent e) {
		super.preInit(e);
		MechanicaBlocks.registerRenderers();
		MechanicaItems.registerRenderers();
	}
	
	@Override
	public void init(FMLInitializationEvent e) {
		super.init(e);
	}
	
	@Override
	public void postInit(FMLPostInitializationEvent e) {
		super.postInit(e);
	}
	
}
