package com.austindorff.mechanica.container.machine;

import javax.annotation.Nullable;

import com.austindorff.mechanica.tileentity.advancedfurnace.TileEntityAdvancedFurnaceCasing;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IContainerListener;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ContainerAdvancedFurnaceCasing extends Container {
	
	TileEntityAdvancedFurnaceCasing	tile;
	private int					cookTime;
	private int					totalCookTime;
	private int					fuelLevel;
	private int					mode;
								
	public ContainerAdvancedFurnaceCasing(IInventory playerInv, TileEntityAdvancedFurnaceCasing tile) {
		this.tile = tile;
		this.addSlotToContainer(new Slot(tile, 0, 8, 17));
		this.addSlotToContainer(new Slot(tile, 1, 8, 53));
		this.addSlotToContainer(new Slot(tile, 2, 80, 35));
		this.addSlotToContainer(new Slot(tile, 3, 130, 24));
		this.addSlotToContainer(new Slot(tile, 4, 130, 46));
		int counter = 0;
		for (int x = 0; x < 9; x++) {
			this.addSlotToContainer(new Slot(playerInv, counter++, 8 + x * 18, 142));
		}
		for (int y = 0; y < 3; y++) {
			for (int x = 0; x < 9; x++) {
				this.addSlotToContainer(new Slot(playerInv, counter++, 8 + x * 18, 84 + y * 18));
			}
		}
	}
	
	public void addListener(IContainerListener listener) {
		if (tile != null && tile.checkForMaster()) {
			super.addListener(listener);
			listener.sendAllWindowProperties(this, this.tile);
		}
	}
	
	public void detectAndSendChanges() {
		if (tile != null && tile.isMaster()) {
			super.detectAndSendChanges();
			for (int i = 0; i < this.listeners.size(); ++i) {
				IContainerListener icontainerlistener = (IContainerListener) this.listeners.get(i);
				if (this.tile.getField(1) == 1) {
					icontainerlistener.sendProgressBarUpdate(this, 1, this.tile.getField(1));
				}
				if (this.cookTime != this.tile.getField(2)) {
					icontainerlistener.sendProgressBarUpdate(this, 2, this.tile.getField(2));
				}				
				if (this.totalCookTime != this.tile.getField(3)) {
					icontainerlistener.sendProgressBarUpdate(this, 3, this.tile.getField(3));
				}
				
				if (this.fuelLevel != this.tile.getField(4)) {
					icontainerlistener.sendProgressBarUpdate(this, 4, this.tile.getField(4));
				}
				if (this.mode != this.tile.getField(5)) {
					icontainerlistener.sendProgressBarUpdate(this, 5, this.tile.getField(5));
				}
			}			
			this.mode = this.tile.getField(5);
			this.fuelLevel = this.tile.getField(4);
			this.cookTime = this.tile.getField(2);
			this.totalCookTime = this.tile.getField(3);
		}
	}
	
	@SideOnly(Side.CLIENT)
	public void updateProgressBar(int id, int data) {
		this.tile.setField(id, data);
	}
	
	/**
	 * Take a stack from the specified inventory slot.
	 */
	@Nullable
	public ItemStack transferStackInSlot(EntityPlayer playerIn, int index) {
		ItemStack itemstack = null;
		Slot slot = (Slot) this.inventorySlots.get(index);
		
		if (slot != null && slot.getHasStack()) {
			ItemStack itemstack1 = slot.getStack();
			itemstack = itemstack1.copy();
			
			if (index == 2) {
				if (!this.mergeItemStack(itemstack1, 3, 39, true)) {
					return null;
				}
				
				slot.onSlotChange(itemstack1, itemstack);
			} else if (index != 1 && index != 0) {
				if (FurnaceRecipes.instance().getSmeltingResult(itemstack1) != null) {
					if (!this.mergeItemStack(itemstack1, 2, 3, false)) {
						return null;
					}
				} else if (tile.isItemFuel(itemstack1)) {
					if (!this.mergeItemStack(itemstack1, 0, 1, false)) {
						return null;
					}
				} else if (index == 3 || index == 4) {
					if (!this.mergeItemStack(itemstack1, 5, 41, false)) {
						return null;
					}
				} else if (index > 14 && index < 42) {
					if (!this.mergeItemStack(itemstack1, 5, 15, false)) {
						return null;
					}
				} else if (index >= 5 && index < 15 && !this.mergeItemStack(itemstack1, 15, 41, false)) {
					return null;
				}
			} else if (!this.mergeItemStack(itemstack1, 15, 41, false)) {
				return null;
			}
			
			if (itemstack1.stackSize == 0) {
				slot.putStack((ItemStack) null);
			} else {
				slot.onSlotChanged();
			}
			
			if (itemstack1.stackSize == itemstack.stackSize) {
				return null;
			}
			
			slot.onPickupFromSlot(playerIn, itemstack1);
		}
		
		return itemstack;
	}
	
	@Override
	public boolean canInteractWith(EntityPlayer player) {
		return tile != null && this.tile.isUseableByPlayer(player);
	}
	
}
