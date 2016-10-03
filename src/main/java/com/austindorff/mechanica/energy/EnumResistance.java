package com.austindorff.mechanica.energy;

public enum EnumResistance {
	INVALID(0), TIER_ONE(1), TIER_TWO(2), TIER_THREE(4), TIER_FOUR(8), TIER_FIVE(16);
	
	private int resistance;
	
	private EnumResistance(int resistance) {
		this.resistance = resistance;
	}
	
	public int getResistance() {
		return this.resistance;
	}
	
	public static EnumResistance getEnumFromResistance(int resistanceInput) {
		switch (resistanceInput) {
			case 1: {
				return EnumResistance.TIER_ONE;
			}
			case 2: {
				return EnumResistance.TIER_TWO;
			}
			case 4: {
				return EnumResistance.TIER_THREE;
			}
			case 8: {
				return EnumResistance.TIER_FOUR;
			}
			case 16: {
				return EnumResistance.TIER_FIVE;
			}
			default: {
				return EnumResistance.INVALID;
			}
		}
	}
	
}
