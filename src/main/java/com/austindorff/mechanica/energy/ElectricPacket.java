package com.austindorff.mechanica.energy;

public class ElectricPacket {
	
	private float	minecraftAmperes;
					
	public ElectricPacket(float minecraftAmperes) {
		this.minecraftAmperes = minecraftAmperes;
	}
	
	public ElectricPacket() {
		this(0);
	}
	
	public float getMinecraftAmperes() {
		return this.minecraftAmperes;
	}
	
	public ElectricPacket join(ElectricPacket[] packets) {
		
		float totalMinecraftAmperes = 0;
		for (ElectricPacket packet : packets) {
			totalMinecraftAmperes += packet.getMinecraftAmperes();
		}
		return new ElectricPacket(totalMinecraftAmperes);
	}
	
}
