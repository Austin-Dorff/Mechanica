package com.austindorff.mechanica.energy;

public enum EnumVoltage {
	INVALID(0), TIER_ONE(16), TIER_TWO(32), TIER_THREE(64), TIER_FOUR(128), TIER_FIVE(256), TIER_SIX(512), TIER_SEVEN(1024), TIER_EIGHT(2048);
	
	private int voltage;
	
	private EnumVoltage(int voltage) {
		this.voltage = voltage;
	}
	
	public int getVoltage() {
		return this.voltage;
	}
	
	public static EnumVoltage getEnumFromVoltage(int voltageInput) {
		switch(voltageInput) {
			case 16: {
				return EnumVoltage.TIER_ONE;
			}
			case 32: {
				return EnumVoltage.TIER_TWO;
			}
			case 64: {
				return EnumVoltage.TIER_THREE;
			}
			case 128: {
				return EnumVoltage.TIER_FOUR;
			}
			case 256: {
				return EnumVoltage.TIER_FIVE;
			}
			case 512: {
				return EnumVoltage.TIER_SIX;
			}
			case 1024: {
				return EnumVoltage.TIER_SEVEN;
			}
			case 2048: {
				return EnumVoltage.TIER_EIGHT;
			}
			default: {
				return EnumVoltage.INVALID;
			}
		}
	}
}
