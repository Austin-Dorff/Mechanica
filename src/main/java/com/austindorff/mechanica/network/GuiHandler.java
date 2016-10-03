package com.austindorff.mechanica.network;

import com.austindorff.mechanica.client.gui.energy.producer.GuiCoalGenerator;
import com.austindorff.mechanica.client.gui.machine.GuiAdvancedFurnaceCasing;
import com.austindorff.mechanica.container.energy.producer.ContainerCoalGenerator;
import com.austindorff.mechanica.container.machine.ContainerAdvancedFurnaceCasing;
import com.austindorff.mechanica.tileentity.energy.producer.coalgenerator.TileEntityCoalGenerator;
import com.austindorff.mechanica.tileentity.machine.TileEntityAdvancedFurnaceCasing;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

public class GuiHandler implements IGuiHandler {
	
	public static final int ADVANCED_FURNACE_CASING = 0;
	public static final int COAL_GENERATOR = 1;
	
	@Override
	public Object getServerGuiElement(int id, EntityPlayer player, World world, int x, int y, int z) {
		if (id == ADVANCED_FURNACE_CASING) {
			return new ContainerAdvancedFurnaceCasing(player.inventory, ((TileEntityAdvancedFurnaceCasing) world.getTileEntity(new BlockPos(x, y, z))));
		} else if (id == COAL_GENERATOR) {
			return new ContainerCoalGenerator(player.inventory, ((TileEntityCoalGenerator) world.getTileEntity(new BlockPos(x, y, z))));
		}		
		return null;
	}
	
	@Override
	public Object getClientGuiElement(int id, EntityPlayer player, World world, int x, int y, int z) {
	    if (id == ADVANCED_FURNACE_CASING) {
	    	TileEntityAdvancedFurnaceCasing tile = ((TileEntityAdvancedFurnaceCasing) world.getTileEntity(new BlockPos(x, y, z)));
	        return new GuiAdvancedFurnaceCasing(player.inventory, tile, new ContainerAdvancedFurnaceCasing(player.inventory, tile)); 
	    } else if (id == COAL_GENERATOR) {
	    	TileEntityCoalGenerator tile = ((TileEntityCoalGenerator) world.getTileEntity(new BlockPos(x, y, z)));
	        return new GuiCoalGenerator(player.inventory, tile, new ContainerCoalGenerator(player.inventory, tile));
		}	
	    return null;
	}
	
}
