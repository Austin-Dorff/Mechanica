package com.austindorff.mechanica;

import com.austindorff.mechanica.block.MechanicaBlocks;
import com.austindorff.mechanica.item.MechanicaItems;
import com.austindorff.mechanica.network.GuiHandler;
import com.austindorff.mechanica.network.proxy.CommonProxy;
import com.austindorff.mechanica.tileentity.TileDeclaration;
import com.austindorff.mechanica.world.gen.MechanicaWorldGen;

import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.items.IItemHandler;

@Mod(modid = Reference.MOD_ID, version = Reference.MOD_VERSION, name = Reference.MOD_NAME)

public class Mechanica
{
	@SidedProxy(clientSide = Reference.CLIENT_PROXY_LOCATION, serverSide = Reference.SERVER_PROXY_LOCATION)
	public static CommonProxy proxy;
	
	@Instance
    public static Mechanica instance = new Mechanica();
	
	@CapabilityInject(IItemHandler.class)
	public static Capability<IItemHandler> ITEM_CAPABILITIES;
    
    MechanicaWorldGen eventWorldGen = new MechanicaWorldGen();
    
    @EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
    	MechanicaBlocks.init();
    	MechanicaItems.init();
    	TileDeclaration.init();
    	GameRegistry.registerWorldGenerator(eventWorldGen, 0);
    	proxy.preInit(event);
    }
    
    @EventHandler
    public void init(FMLInitializationEvent event)
    {
    	NetworkRegistry.INSTANCE.registerGuiHandler(Mechanica.instance, new GuiHandler());
    	proxy.init(event);
    }
    
    @EventHandler
    public void postInit(FMLPostInitializationEvent event)
    {
    	proxy.postInit(event);
    }
}
