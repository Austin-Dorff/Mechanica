package com.austindorff.mechanica;

import com.austindorff.mechanica.blocks.MechanicaBlockDeclaration;
import com.austindorff.mechanica.blocks.MechanicaBlocks;
import com.austindorff.mechanica.items.MechanicaItems;
import com.austindorff.mechanica.network.GuiHandler;
import com.austindorff.mechanica.tileentity.MechanicaTileEntities;
import com.austindorff.mechanica.world.gen.MechanicaWorldGeneration;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

@Mod(modid = Mechanica.MODID, version = Mechanica.VERSION, name = Mechanica.MODNAME)
public class Mechanica
{
	@SidedProxy(clientSide="com.austindorff.mechanica.ClientProxy", serverSide="com.austindorff.mechanica.ServerProxy")
	public static CommonProxy proxy;
	
	@Instance
    public static Mechanica instance = new Mechanica();
	
    public static final String MODID = "mechanica";
    public static final String MODNAME = "Mechanica";
    public static final String VERSION = "1.0";
    
    public static CreativeTabs tabMechanica = new CreativeTabs("Mechanica") {
    	@SideOnly(Side.CLIENT)
    	public Item getTabIconItem() {
    		return Item.getItemFromBlock(MechanicaBlockDeclaration.blockBasicMachineCasing);
    	}
    };
    
    MechanicaWorldGeneration eventWorldGen = new MechanicaWorldGeneration();
    
    @EventHandler
    public void preInit(FMLPreInitializationEvent preEvent)
    {
    	MechanicaBlocks.init();
    	MechanicaItems.init();
    	MechanicaTileEntities.init();
    	GameRegistry.registerWorldGenerator(eventWorldGen, 0);
    }
    
    @EventHandler
    public void init(FMLInitializationEvent event)
    {
    	NetworkRegistry.INSTANCE.registerGuiHandler(Mechanica.instance, new GuiHandler());
    }
    
    @EventHandler
    public void postInit(FMLPostInitializationEvent postEvent)
    {
    	
    }
}
