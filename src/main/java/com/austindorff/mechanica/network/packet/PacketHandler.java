package com.austindorff.mechanica.network.packet;

import com.austindorff.mechanica.Reference;
import com.austindorff.mechanica.network.packet.block.machine.PacketAdvancedFurnace;

import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.relauncher.Side;

public class PacketHandler {
	private static int					packetId	= 0;
													
	public PacketHandler() {
	}
	
	public static int nextID() {
		return packetId++;
	}
	
	public static void registerMessages(String channelName) {
		Reference.CHANNEL = NetworkRegistry.INSTANCE.newSimpleChannel(channelName);
		registerMessages();
	}
	
	public static void registerMessages() {
		Reference.CHANNEL.registerMessage(PacketAdvancedFurnace.Handler.class, PacketAdvancedFurnace.class, nextID(), Side.SERVER);
	}
}
