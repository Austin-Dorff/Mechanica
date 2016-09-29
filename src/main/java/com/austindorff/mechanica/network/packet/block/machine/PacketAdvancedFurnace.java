package com.austindorff.mechanica.network.packet.block.machine;

import javax.annotation.Nullable;

import com.austindorff.mechanica.tileentity.machine.TileAdvancedFurnaceCasing;

import io.netty.buffer.ByteBuf;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class PacketAdvancedFurnace implements IMessage {
	
	private NBTTagCompound data;
	
	public PacketAdvancedFurnace() {
	}
	
	@Nullable
	public PacketAdvancedFurnace(TileAdvancedFurnaceCasing tile) {
		data = tile.writeToNBT(new NBTTagCompound());
	}
	
	@Override
	public void fromBytes(ByteBuf buf) {
		data = ByteBufUtils.readTag(buf);
	}
	
	@Override
	public void toBytes(ByteBuf buf) {
		ByteBufUtils.writeTag(buf, data);
	}
	
	public TileAdvancedFurnaceCasing getTile() {
		return ((TileAdvancedFurnaceCasing) Minecraft.getMinecraft().theWorld.getTileEntity(new TileAdvancedFurnaceCasing(data).getPos()));
	}
	
	public static class Handler implements IMessageHandler<PacketAdvancedFurnace, IMessage> {
		@Override
		public IMessage onMessage(PacketAdvancedFurnace message, MessageContext ctx) {
			FMLCommonHandler.instance().getWorldThread(ctx.netHandler).addScheduledTask(() -> handle(message, ctx));
			return null;
		}
		
		private void handle(PacketAdvancedFurnace message, MessageContext ctx) {
			EntityPlayerMP playerEntity = ctx.getServerHandler().playerEntity;
			World world = playerEntity.worldObj;
			TileAdvancedFurnaceCasing tile = message.getTile();	
			tile.toggleMode();
		}
	}
}
