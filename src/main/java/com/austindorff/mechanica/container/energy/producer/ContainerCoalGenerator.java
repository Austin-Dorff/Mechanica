package com.austindorff.mechanica.container.energy.producer;

import javax.annotation.Nullable;

import com.austindorff.mechanica.tileentity.energy.producer.coalgenerator.TileEntityCoalGenerator;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IContainerListener;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ContainerCoalGenerator extends Container {
	
	private TileEntityCoalGenerator tile;
	private int currentBurnTime;
	private int internalStorage;
	private boolean isBurning;
	
	public ContainerCoalGenerator(IInventory playerInv, TileEntityCoalGenerator tile) {
		this.tile = tile;
		this.addSlotToContainer(new Slot(tile, 0, 80, 54));
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
	
	@Override
	public boolean canInteractWith(EntityPlayer playerIn) {
		return tile.isUseableByPlayer(playerIn);
	}
	
	public void addListener(IContainerListener listener) {
		if (tile != null) {
			super.addListener(listener);
			listener.sendAllWindowProperties(this, this.tile);
		}
	}
	
	public void detectAndSendChanges() {
		if (tile != null) {
			super.detectAndSendChanges();
			for (int i = 0; i < this.listeners.size(); ++i) {
				IContainerListener icontainerlistener = (IContainerListener) this.listeners.get(i);
				if (this.tile.getField(0) == 1) {
					icontainerlistener.sendProgressBarUpdate(this, 0, this.tile.getField(0));
				}
				if (this.currentBurnTime != this.tile.getField(1)) {
					icontainerlistener.sendProgressBarUpdate(this, 1, this.tile.getField(1));
				}
				if (this.internalStorage != this.tile.getField(2)) {
					icontainerlistener.sendProgressBarUpdate(this, 2, this.tile.getField(2));
				}
			}
			this.isBurning = this.tile.getField(0) == 1 ? true : false;
			this.currentBurnTime = this.tile.getField(1);
			this.internalStorage = this.tile.getField(2);
		}
	}
	
	@SideOnly(Side.CLIENT)
	public void updateProgressBar(int id, int data) {
		this.tile.setField(id, data);
	}
	
	@Nullable
	public ItemStack transferStackInSlot(EntityPlayer playerIn, int index) {
		ItemStack itemstack = null;
		Slot slot = (Slot) this.inventorySlots.get(index);
		
		if (slot != null && slot.getHasStack()) {
			ItemStack itemstack1 = slot.getStack();
			itemstack = itemstack1.copy();
			
			if (index == 0) {
				if (!this.mergeItemStack(itemstack1, 3, 39, true)) {
					return null;
				}
				slot.onSlotChange(itemstack1, itemstack);
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
	
}
