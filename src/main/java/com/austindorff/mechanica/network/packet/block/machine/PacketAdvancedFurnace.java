package com.austindorff.mechanica.network.packet.block.machine;

import javax.annotation.Nullable;

import com.austindorff.mechanica.tileentity.machine.TileAdvancedFurnaceCasing;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class PacketAdvancedFurnace implements IMessage {
	
	private NBTTagCompound data;
	private BlockPos pos;
	
	public PacketAdvancedFurnace() {
	}
	
	@Nullable
	public PacketAdvancedFurnace(BlockPos pos, NBTTagCompound tag) {
		this.data = tag;
		this.pos = pos;
	}
	
	@Override
	public void fromBytes(ByteBuf buf) {
		this.pos = new BlockPos(buf.readInt(), buf.readInt(), buf.readInt());
		this.data = ByteBufUtils.readTag(buf);
	}
	
	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeInt(this.pos.getX());
		buf.writeInt(this.pos.getY());
		buf.writeInt(this.pos.getZ());
		ByteBufUtils.writeTag(buf, data);
	}
	
	public TileAdvancedFurnaceCasing getTile() {
		return ((TileAdvancedFurnaceCasing) Minecraft.getMinecraft().theWorld.getTileEntity(this.pos));
	}
	
	public static class Handler implements IMessageHandler<PacketAdvancedFurnace, IMessage> {
		@Override
		public IMessage onMessage(PacketAdvancedFurnace message, MessageContext ctx) {
			FMLCommonHandler.instance().getWorldThread(ctx.netHandler).addScheduledTask(() -> handle(message, ctx));
			return null;
		}
		
		private void handle(PacketAdvancedFurnace message, MessageContext ctx) {
			TileAdvancedFurnaceCasing tile = message.getTile();	
			tile.toggleMode();
			tile.markDirty();
		}
	}
}
