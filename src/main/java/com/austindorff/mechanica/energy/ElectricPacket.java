package com.austindorff.mechanica.energy;

public class ElectricPacket {
	
	private int	minecraftAmperes;
	private INetworkComponent sender;
					
	public ElectricPacket(INetworkComponent sender, int minecraftAmperes) {
		this.minecraftAmperes = minecraftAmperes;
		this.sender = sender;
	}
	
	public ElectricPacket() {
		this(null, 0);
	}
	
	public int getMinecraftAmperes() {
		return this.minecraftAmperes;
	}
	
	public INetworkComponent getSender() {
		return this.sender;
	}
	
}
