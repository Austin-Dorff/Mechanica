package com.austindorff.mechanica.network.packet.block.machine;

import javax.annotation.Nullable;

import com.austindorff.mechanica.tileentity.advancedfurnace.TileEntityAdvancedFurnaceCasing;

import io.netty.buffer.ByteBuf;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class PacketAdvancedFurnace implements IMessage {
	
	private BlockPos pos;
	
	public PacketAdvancedFurnace() {
	}
	
	@Nullable
	public PacketAdvancedFurnace(BlockPos pos) {
		this.pos = pos;
	}
	
	@Override
	public void fromBytes(ByteBuf buf) {
		this.pos = new BlockPos(buf.readInt(), buf.readInt(), buf.readInt());
	}
	
	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeInt(this.pos.getX());
		buf.writeInt(this.pos.getY());
		buf.writeInt(this.pos.getZ());
	}
	
	public World getWorld(MessageContext ctx) {
		return ctx.getServerHandler().playerEntity.getEntityWorld();
	}
	
	public TileEntityAdvancedFurnaceCasing getTile(MessageContext ctx) {
		return ((TileEntityAdvancedFurnaceCasing) getWorld(ctx).getTileEntity(this.pos));
	}
	
	public static class Handler implements IMessageHandler<PacketAdvancedFurnace, IMessage> {
		@Override
		public IMessage onMessage(PacketAdvancedFurnace message, MessageContext ctx) {
			FMLCommonHandler.instance().getWorldThread(ctx.netHandler).addScheduledTask(() -> handle(message, ctx));
			return null;
		}
		
		private void handle(PacketAdvancedFurnace message, MessageContext ctx) {
			TileEntityAdvancedFurnaceCasing tile = message.getTile(ctx);	
			tile.toggleMode();
			tile.markDirty();
		}
	}
}
