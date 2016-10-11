package com.austindorff.mechanica.energy;

import net.minecraft.util.math.BlockPos;

public enum EnumDirection {
	DOWN("down", 0), UP("up", 1), NORTH("north", 2), EAST("east", 3), SOUTH("south", 4), WEST("west", 5), ALL("wire", 0), NORTHEAST("northeast", 0), NORTHWEST("northwest", 0), SOUTHEAST("southeast", 0), SOUTHWEST("southwest", 0), SAME_LOCATION("same_location", 0);
	
	private int		blockFace;
	private String	directionName;
					
	private EnumDirection(String directionName, int blockFace) {
		this.blockFace = blockFace;
		this.directionName = directionName;
	}
	
	public int getBlockFace() {
		return this.blockFace;
	}
	
	public String getDirectionName() {
		return this.directionName;
	}
	
	public static EnumDirection fromFaceOpposite(int face) {
		switch (face) {
			case 0: {
				return EnumDirection.UP;
			}
			case 1: {
				return EnumDirection.DOWN;
			}
			case 2: {
				return EnumDirection.SOUTH;
			}
			case 3: {
				return EnumDirection.WEST;
			}
			case 4: {
				return EnumDirection.NORTH;
			}
			case 5: {
				return EnumDirection.EAST;
			}
		}
		return null;
	}
	
	public static EnumDirection fromFace(int face) {
		switch (face) {
			case 0: {
				return EnumDirection.DOWN;
			}
			case 1: {
				return EnumDirection.UP;
			}
			case 2: {
				return EnumDirection.NORTH;
			}
			case 3: {
				return EnumDirection.EAST;
			}
			case 4: {
				return EnumDirection.SOUTH;
			}
			case 5: {
				return EnumDirection.WEST;
			}
		}
		return null;
	}
	
	public static boolean areEqual(EnumDirection dirOne, EnumDirection dirTwo) {
		if (!(dirOne instanceof EnumDirection) || !(dirTwo instanceof EnumDirection)) {
			return false;
		} else {
			return dirOne.getBlockFace() == dirTwo.getBlockFace();
		}
	}
	
	public EnumDirection getLateralGeneralDirectionBetweenFromBlockToBlock(BlockPos origin, BlockPos end) {
		boolean xNeutral = origin.getX() == end.getX();
		boolean xPositive = !xNeutral && origin.getX() > end.getX();
		boolean zNeutral = origin.getZ() == end.getZ();
		boolean zPositive = !zNeutral && origin.getZ() > end.getZ();
		
		if (xNeutral && zNeutral) {
			return EnumDirection.SAME_LOCATION;
		}
		if (xPositive) {
			if (zNeutral) {
				return EnumDirection.EAST;
			} else if (zPositive) {
				return EnumDirection.SOUTHEAST;
			} else {
				return EnumDirection.NORTHEAST;
			}
		} else if (xNeutral) {
			if (zNeutral) {
				return EnumDirection.SAME_LOCATION;
			} else if (zPositive) {
				return EnumDirection.SOUTH;
			} else {
				return EnumDirection.NORTH;
			}
		} else {
			if (zNeutral) {
				return EnumDirection.WEST;
			} else if (zPositive) {
				return EnumDirection.SOUTHWEST;
			} else {
				return EnumDirection.NORTHWEST;
			}
		}
		
	}
	
}
