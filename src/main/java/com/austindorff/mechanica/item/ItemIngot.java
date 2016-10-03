package com.austindorff.mechanica.item;

import com.austindorff.mechanica.Reference;

import net.minecraft.block.material.Material;

public class ItemIngot extends ItemBase {
	
	public ItemIngot(EnumIngotType ingotType) {
		super(ingotType.getRegistryName(), ingotType.getName());
	}
	
	public enum EnumIngotType {
		COPPER("Copper"), TIN("Tin"), SILVER("Silver"), LEAD("Lead");
		
		private String	registryName;
						
		public float	hardness	= 8.0F;
		public float	resistance	= 5.0F;
		public Material	material	= Material.ROCK;
									
		private EnumIngotType(String registryName) {
			this.registryName = registryName;
		}
		
		public String getRegistryName() {
			return Reference.MOD_ID + ":item" + this.registryName + "Ingot";
		}
		
		public String getName() {
			return "item" + this.registryName + "Ingot";
		}
	}
	
}
