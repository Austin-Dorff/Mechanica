package com.austindorff.mechanica.block.worldgen;

import com.austindorff.mechanica.Reference;
import com.austindorff.mechanica.block.BlockBase;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;

public class BlockOre extends BlockBase {
	
	public BlockOre(EnumOreType oreType) {
		super(oreType.getRegistryName(), oreType.getUnlocalizedName(), oreType.material, oreType.hardness, oreType.resistance);
	}
	
	@Override
	public boolean isOpaqueCube(IBlockState state) {
		return false;
	}
	
	public enum EnumOreType {
		COPPER("Copper"), TIN("Tin"), SILVER("Silver"), LEAD("Lead");
		
		private String  name;
		
		public float hardness = 8.0F;
		public float resistance = 5.0F;
		public Material material = Material.ROCK;
						
		private EnumOreType(String name) {
			this.name = name;
		}
		
		public String getRegistryName() {
			return Reference.MOD_ID + ":" + this.getUnlocalizedName();
		}
		
		public String getUnlocalizedName() {
			return "block" + this.name + "Ore";
		}
	}
	
}
