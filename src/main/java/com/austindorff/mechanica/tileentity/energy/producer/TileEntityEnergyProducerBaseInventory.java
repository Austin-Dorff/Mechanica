package com.austindorff.mechanica.tileentity.energy.producer;

import com.austindorff.mechanica.energy.ElectricPacket;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;

public abstract class TileEntityEnergyProducerBaseInventory extends TileEntityEnergyProducerBase implements IInventory {

	private String name;
	private ItemStack[] inventory;
	
	public TileEntityEnergyProducerBaseInventory(String name, int sizeInventory) {
		this.inventory = new ItemStack[sizeInventory];
		this.name = name;
	}
	
	@Override
	public String getName() {
		return name;
	}

	@Override
	public boolean hasCustomName() {
		return true;
	}

	@Override
	public int getSizeInventory() {
		return this.inventory.length;
	}

	@Override
	public ItemStack getStackInSlot(int index) {
		if (index < 0 || index >= this.getSizeInventory()) {
			return null;
		}
		return this.inventory[index];
	}
	
	@Override
	public ItemStack decrStackSize(int index, int count) {
		if (this.getStackInSlot(index) != null) {
			ItemStack itemStack;
			if (this.getStackInSlot(index).stackSize <= count) {
				itemStack = this.getStackInSlot(index);
				this.setInventorySlotContents(index, null);
				this.markDirty();
				return itemStack;
			} else {
				itemStack = this.getStackInSlot(index).splitStack(count);
				
				if (this.getStackInSlot(index).stackSize <= 0) {
					this.setInventorySlotContents(index, null);
				} else {
					this.setInventorySlotContents(index, this.getStackInSlot(index));
				}
				this.markDirty();
				return itemStack;
			}
		}
		return null;
	}
	
	@Override
	public ItemStack removeStackFromSlot(int index) {
		ItemStack itemStack = this.getStackInSlot(index);
		this.setInventorySlotContents(index, null);
		return itemStack;
	}
	
	@Override
	public void setInventorySlotContents(int index, ItemStack stack) {
		if (!(index < 0 || index >= this.getSizeInventory())) {
			if (stack != null && stack.stackSize > this.getInventoryStackLimit()) {
				stack.stackSize = this.getInventoryStackLimit();
			}
			if (stack != null && stack.stackSize == 0) {
				stack = null;
			}
			if (stack != null) {
				this.inventory[index] = stack;
			} else {
				this.inventory[index] = null;
			}
			this.markDirty();
		}
	}
	
	@Override
	public int getInventoryStackLimit() {
		return 64;
	}
	
	public ItemStack[] getInventory() {
		return inventory;
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer player) {
		return this.worldObj.getTileEntity(this.getPos()) == this && player.getDistanceSq(this.pos.add(0.5, 0.5, 0.5)) <= 64;
	}
	
	@Override
	public void openInventory(EntityPlayer player) {
	
	}
	
	@Override
	public void closeInventory(EntityPlayer player) {
	
	}
	
	@Override
	public abstract boolean isItemValidForSlot(int slot, ItemStack stack);
	
	public abstract int getField(int id);
	
	public abstract void setField(int id, int value);
	
	@Override
	public ITextComponent getDisplayName() {
		return (ITextComponent) (this.hasCustomName() ? new TextComponentString(this.getName()) : new TextComponentTranslation(this.getName(), new Object[0]));
	}
	
	@Override
	public abstract int getFieldCount();
	
	@Override
	public void clear() {
		for (int i = 0; i < this.inventory.length; ++i) {
			this.inventory[i] = null;
		}
	}

	@Override
	public abstract float getMinecraftAmperesProduced();

	@Override
	public abstract boolean isCorrectTileEntity(TileEntity tile);

	@Override
	public abstract void sendElectricPacket(ElectricPacket packet);

	@Override
	public abstract void updateBlockState(BlockPos coords);

	@Override
	public abstract boolean doesStoreEnergy();

}
