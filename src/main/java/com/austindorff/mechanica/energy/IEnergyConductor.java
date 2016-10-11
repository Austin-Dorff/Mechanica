package com.austindorff.mechanica.energy;

import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

public interface IEnergyConductor extends IEnergyCurrentRange {
	
	boolean hasLossOverDistance();
	
	int getLossPerUnitDistance();
	
	void setUnitDistanceForLoss(int distance);
	
}
