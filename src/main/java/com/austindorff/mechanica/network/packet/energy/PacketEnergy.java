package com.austindorff.mechanica.network.packet.energy;

import com.austindorff.mechanica.tileentity.energy.storage.TileEntityEnergyStorageBlockBase;
import com.austindorff.mechanica.tileentity.energy.wire.TileEntityWire;

import io.netty.buffer.ByteBuf;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class PacketEnergy implements IMessage {
	
	private int			voltage, resistance;
	private BlockPos	pos;
	private boolean sending;
						
	public PacketEnergy(TileEntity tile, int voltage, int resistance, BlockPos pos, boolean sending) {
		this.pos = pos;
		this.voltage = voltage;
		this.resistance = resistance;
		this.sending = sending;
	}
	
	@Override
	public void fromBytes(ByteBuf buf) {
		this.voltage = buf.readInt();
		this.resistance = buf.readInt();
		this.pos = new BlockPos(buf.readInt(), buf.readInt(), buf.readInt());
		this.sending = buf.readBoolean();
	}
	
	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeInt(this.voltage);
		buf.writeInt(this.resistance);
		buf.writeInt(this.pos.getX());
		buf.writeInt(this.pos.getY());
		buf.writeInt(this.pos.getZ());
		buf.writeBoolean(this.sending);
	}
	
	public World getWorld(MessageContext ctx) {
		return ctx.getServerHandler().playerEntity.getEntityWorld();
	}
	
	public TileEntity getTileEntity(World world) {
		return world.getTileEntity(pos);
	}
	
	public static class Handler implements IMessageHandler<PacketEnergy, IMessage> {
		@Override
		public IMessage onMessage(PacketEnergy message, MessageContext ctx) {
			FMLCommonHandler.instance().getWorldThread(ctx.netHandler).addScheduledTask(() -> handle(message, ctx));
			return null;
		}
		
		private void handle(PacketEnergy packet, MessageContext ctx) {
			TileEntity tile = packet.getTileEntity(packet.getWorld(ctx));
			if (packet.sending) {
				if (tile instanceof TileEntityEnergyStorageBlockBase) {
					((TileEntityEnergyStorageBlockBase) tile).recieveEnergyPacket(packet);
				} else if (tile instanceof TileEntityWire) {
					((TileEntityWire) tile).recieveEnergyPacket(packet);
				}
			}
		}
	}
	
	public int getVoltage() {
		return this.voltage;
	}
	
	public int getResistance() {
		return this.resistance;
	}
	
	public int getEnergyValue() {
		return this.voltage / this.resistance;
	}
	
	public BlockPos getBlockPos() {
		return this.pos;
	}
	
}
