package com.austindorff.mechanica.tileentity.machine;

import com.austindorff.mechanica.Reference;
import com.austindorff.mechanica.block.MechanicaBlocks;
import com.austindorff.mechanica.block.machine.AdvancedFurnaceCasing;
import com.austindorff.mechanica.tileentity.TileMultiblockBase;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;

public class TileAdvancedFurnaceCasing extends TileMultiblockBase implements IInventory {
	
	private ItemStack[]	inventory;
	private boolean		isActive;
	private int			burnTime;
	private int			cookTime;
	private int			totalCookTime;
	private String		customName	= "Advanced Furnace";
	private int			fuelLevel;
	public int			maxFuelLevel;
	private String		modeString;
	private int			mode;
						
	public TileAdvancedFurnaceCasing() {
		this.inventory = new ItemStack[this.getSizeInventory()];
		reset();
	}
	
	public TileAdvancedFurnaceCasing(NBTTagCompound tag) {
		this.readFromNBT(tag);
	}
	
	@Override
	public void reset() {
		super.reset();
		this.isActive = false;
		this.cookTime = 0;
		this.totalCookTime = 75;
		this.mode = 0;
		this.maxFuelLevel = 320;
		this.fuelLevel = 0;
		this.modeString = "x1";
	}
	
	public void unloadInventory() {
		if (this.isMaster()) {
			if (this.fuelLevel >= 64) {
				if (this.inventory[1] != null) {
					InventoryHelper.spawnItemStack(this.worldObj, this.pos.getX(), this.pos.getY() + 1, this.pos.getZ(), this.inventory[1]);
					this.inventory[1] = null;
				}
				while (this.fuelLevel >= 64) {
					this.fuelLevel -= 64;
					InventoryHelper.spawnItemStack(this.worldObj, this.pos.getX(), this.pos.getY() + 1, this.pos.getZ(), new ItemStack(Items.LAVA_BUCKET));
				}
			}
			if (this.inventory[2] != null) {
				InventoryHelper.spawnItemStack(this.worldObj, this.pos.getX(), this.pos.getY() + 1, this.pos.getZ(), this.inventory[2]);
			}
			if (this.inventory[3] != null) {
				InventoryHelper.spawnItemStack(this.worldObj, this.pos.getX(), this.pos.getY() + 1, this.pos.getZ(), this.inventory[3]);
			}
			if (this.inventory[4] != null) {
				InventoryHelper.spawnItemStack(this.worldObj, this.pos.getX(), this.pos.getY() + 1, this.pos.getZ(), this.inventory[4]);
			}
		}
	}
	
	public boolean isCorrectTileEntity(TileEntity tile) {
		return tile instanceof TileAdvancedFurnaceCasing;
	}
	
	public boolean checkMultiBlockForm() {
		int i = 0;
		BlockPos coords = getMasterCoords();
		if (!isMasterYCoordValid(coords)) {
			return false;
		}
		for (int x = coords.getX() - 1; x < coords.getX() + 2; x++) {
			for (int y = coords.getY(); y < coords.getY() + 3; y++) {
				for (int z = coords.getZ() - 1; z < coords.getZ() + 2; z++) {
					TileEntity tile = worldObj.getTileEntity(new BlockPos(x, y, z));
					if (tile != null && isCorrectTileEntity(tile)) {
						i++;
					}
				}
			}
		}
		boolean valid = i > 25 && worldObj.isAirBlock(new BlockPos(coords.getX(), coords.getY() + 1, coords.getZ()));
		if (valid && hasMaster() == false) {
			setupStructure(coords.getX(), coords.getY(), coords.getZ());
		}
		return valid;
	}
	
	public void toggleActiveState() {
		for (int x = getMasterX() - 1; x < getMasterX() + 2; x++) {
			for (int y = getMasterY(); y < getMasterY() + 3; y++) {
				for (int z = getMasterZ() - 1; z < getMasterZ() + 2; z++) {
					BlockPos pos = new BlockPos(x, y, z);
					TileEntity tile = worldObj.getTileEntity(pos);
					if (tile != null && (tile instanceof TileAdvancedFurnaceCasing)) {
						if (((x == getMasterX() && z != getMasterZ()) || (z == getMasterZ() && x != getMasterX())) && (y == getMasterY())) {
							worldObj.setBlockState(pos, MechanicaBlocks.BLOCKS.get(Reference.ADVANCED_FURNACE_CASING_NAME).getDefaultState().withProperty(AdvancedFurnaceCasing.LOCATION, AdvancedFurnaceCasing.EnumType.FURNACE).withProperty(AdvancedFurnaceCasing.IS_ACTIVE, this.isActive), 3);
						}
					}
				}
			}
		}
	}
	
	public void toggleMode() {
		if (this.isMaster()) {
			if (this.mode == 1) {
				this.mode = 0;
				this.modeString = "x1";
			} else {
				this.mode = 1;
				this.modeString = "x2";
			}
		}
	}
	
	public String getModeString() {
		return this.modeString;
	}
	
	public BlockPos getMasterCoords() {
		int[] coords = new int[] { -1, -1, -1 };
		int level = -3;
		// Get multi-block Level
		if (isNeighborY(-1) && isNeighborY(1)) {
			level = -1;
		} else if (isNeighborY(-2) && !isNeighborUp()) {
			level = -2;
		} else if (isNeighborY(2) && !isNeighborDown()) {
			level = 0;
		}
		// Get Master block coords
		if (level != -3) {
			coords[1] = this.pos.getY() + level;
			int counter = 0;
			TileAdvancedFurnaceCasing tile = ((TileAdvancedFurnaceCasing) this.worldObj.getTileEntity(new BlockPos(pos.getX(), coords[1], pos.getZ())));
			while (counter < 3 && tile != null && !isMiddle(tile)) {
				counter++;
				if (tile.isNeighborNorth() && tile.isNeighborEast() && !tile.isNeighborSouth() && tile.isNeighborWest()) {
					tile = ((TileAdvancedFurnaceCasing) this.worldObj.getTileEntity(new BlockPos(tile.pos.getX(), tile.pos.getY(), tile.pos.getZ() - 1)));
				} else if (tile.isNeighborNorth() && !tile.isNeighborEast() && !tile.isNeighborSouth() && tile.isNeighborWest()) {
					tile = ((TileAdvancedFurnaceCasing) this.worldObj.getTileEntity(new BlockPos(tile.pos.getX() - 1, tile.pos.getY(), tile.pos.getZ() - 1)));
				} else if (tile.isNeighborNorth() && tile.isNeighborEast() && !tile.isNeighborSouth() && !tile.isNeighborWest()) {
					tile = ((TileAdvancedFurnaceCasing) this.worldObj.getTileEntity(new BlockPos(tile.pos.getX() + 1, tile.pos.getY(), tile.pos.getZ() - 1)));
				} else if (!tile.isNeighborNorth() && tile.isNeighborEast() && tile.isNeighborSouth() && tile.isNeighborWest()) {
					tile = ((TileAdvancedFurnaceCasing) this.worldObj.getTileEntity(new BlockPos(tile.pos.getX(), tile.pos.getY(), tile.pos.getZ() + 1)));
				} else if (!tile.isNeighborNorth() && !tile.isNeighborEast() && tile.isNeighborSouth() && tile.isNeighborWest()) {
					tile = ((TileAdvancedFurnaceCasing) this.worldObj.getTileEntity(new BlockPos(tile.pos.getX() - 1, tile.pos.getY(), tile.pos.getZ() + 1)));
				} else if (!tile.isNeighborNorth() && tile.isNeighborEast() && tile.isNeighborSouth() && !tile.isNeighborWest()) {
					tile = ((TileAdvancedFurnaceCasing) this.worldObj.getTileEntity(new BlockPos(tile.pos.getX() + 1, tile.pos.getY(), tile.pos.getZ() + 1)));
				} else if (tile.isNeighborNorth() && tile.isNeighborSouth() && !tile.isNeighborEast() && tile.isNeighborWest()) {
					tile = ((TileAdvancedFurnaceCasing) this.worldObj.getTileEntity(new BlockPos(tile.pos.getX() - 1, tile.pos.getY(), tile.pos.getZ())));
				} else if (tile.isNeighborNorth() && tile.isNeighborSouth() && tile.isNeighborEast() && !tile.isNeighborWest()) {
					tile = ((TileAdvancedFurnaceCasing) this.worldObj.getTileEntity(new BlockPos(tile.pos.getX() + 1, tile.pos.getY(), tile.pos.getZ())));
				}
			}
			if (tile != null && isMiddle(tile)) {
				coords[0] = tile.pos.getX();
				coords[2] = tile.pos.getZ();
			}
		}
		return new BlockPos(coords[0], coords[1], coords[2]);
	}
	
	public boolean isMiddle(TileAdvancedFurnaceCasing tile) {
		if (tile.isNeighborNorth() && tile.isNeighborEast() && tile.isNeighborSouth() && tile.isNeighborWest() && tile.isNeighborNorthEast() && tile.isNeighborNorthWest() && tile.isNeighborSouthEast() && tile.isNeighborSouthWest()) {
			return true;
		}
		return false;
	}
	
	public void setupStructure(int xPos, int yPos, int zPos) {
		for (int x = xPos - 1; x < xPos + 2; x++) {
			for (int y = yPos; y < yPos + 3; y++) {
				for (int z = zPos - 1; z < zPos + 2; z++) {
					BlockPos pos = new BlockPos(x, y, z);
					TileEntity tile = worldObj.getTileEntity(pos);
					boolean master = (x == xPos && y == yPos && z == zPos);
					if (tile != null && isCorrectTileEntity(tile)) {
						((TileAdvancedFurnaceCasing) tile).setMasterCoords(xPos, yPos, zPos);
						((TileAdvancedFurnaceCasing) tile).setHasMaster(true);
						((TileAdvancedFurnaceCasing) tile).setIsMaster(master);
						if (((x == xPos && z != zPos) || (z == zPos && x != xPos)) && (y == yPos)) {
							worldObj.setBlockState(pos, MechanicaBlocks.BLOCKS.get(Reference.ADVANCED_FURNACE_CASING_NAME).getDefaultState().withProperty(AdvancedFurnaceCasing.LOCATION, AdvancedFurnaceCasing.EnumType.FURNACE).withProperty(AdvancedFurnaceCasing.IS_ACTIVE, false), 3);
						} else {
							worldObj.setBlockState(pos, MechanicaBlocks.BLOCKS.get(Reference.ADVANCED_FURNACE_CASING_NAME).getDefaultState().withProperty(AdvancedFurnaceCasing.LOCATION, AdvancedFurnaceCasing.EnumType.EDGE).withProperty(AdvancedFurnaceCasing.IS_ACTIVE, false), 3);
						}
					}
				}
			}
		}
	}
	
	public void resetStructure() {
		for (int x = getMasterX() - 1; x <= getMasterX() + 1; x++) {
			for (int y = getMasterY(); y <= getMasterY() + 2; y++) {
				for (int z = getMasterZ() - 1; z <= getMasterZ() + 1; z++) {
					BlockPos blockPos = new BlockPos(x, y, z);
					TileEntity tile = worldObj.getTileEntity(blockPos);
					if (tile != null && isCorrectTileEntity(tile)) {
						((TileAdvancedFurnaceCasing) tile).reset();
						worldObj.setBlockState(blockPos, MechanicaBlocks.BLOCKS.get(Reference.ADVANCED_FURNACE_CASING_NAME).getDefaultState().withProperty(AdvancedFurnaceCasing.LOCATION, AdvancedFurnaceCasing.EnumType.DEFAULT).withProperty(AdvancedFurnaceCasing.IS_ACTIVE, false), 3);
					}
				}
			}
		}
	}
	
	public boolean shouldBeActive() {
		return this.isMaster() && this.canSmelt() && this.hasEnoughFuel();
	}
	
	@Override
	public void tileBehavior() {
		if (this.isMaster()) {
			boolean didUpdate = false;
			if (!this.worldObj.isRemote) {
				if (fuelLevel <= this.maxFuelLevel - 64) {
					if (this.inventory[0] != null && (this.inventory[1] == null || (this.inventory[1].getItem() == Items.BUCKET && this.inventory[1].stackSize < 16)) && this.inventory[0].getItem() == Items.LAVA_BUCKET) {
						if (this.inventory[1] == null) {
							this.inventory[1] = new ItemStack(Items.BUCKET);
						} else {
							this.inventory[1].stackSize++;
						}
						this.inventory[0] = null;
						this.fuelLevel += 64;
						didUpdate = true;
					}
				}
				if (this.inventory[0] != null && this.isMaster() && this.fuelLevel >= 64 && this.inventory[0].getItem() == Items.BUCKET && this.inventory[1] == null) {
					this.inventory[1] = new ItemStack(Items.LAVA_BUCKET);
					this.fuelLevel -= 64;
					this.inventory[0] = null;
					didUpdate = true;
				}
				if (hasEnoughFuel() && canSmelt()) {
					++this.cookTime;
					if (this.cookTime == this.getCookingTime()) {
						this.cookTime = 0;
						this.smeltItem();
					}
					didUpdate = true;
				}
				if (this.isActive != shouldBeActive()) {
					this.isActive = !this.isActive;
					didUpdate = true;
					this.toggleActiveState();
				}
				if (this.cookTime != 0 && !canSmelt()) {
					this.cookTime = 0;
					didUpdate = true;
				}
			}
			if (didUpdate) {
				this.markDirty();
			}
		}
	}
	
	private void smeltItem() {
		ItemStack itemstack = FurnaceRecipes.instance().getSmeltingResult(this.inventory[2]);
		int amount = this.mode == 1 ? 2 : 1;
		itemstack = new ItemStack(itemstack.getItem(), amount);
		if (this.inventory[3] == null) {
			this.inventory[3] = itemstack.copy();
		} else if (this.inventory[3].getItem() == itemstack.getItem() && (this.inventory[3].stackSize + itemstack.stackSize) <= this.getInventoryStackLimit()) {
			this.inventory[3].stackSize += itemstack.stackSize;
		} else if (this.inventory[4] == null) {
			this.inventory[4] = itemstack.copy();
		} else if (this.inventory[4].getItem() == itemstack.getItem() && (this.inventory[4].stackSize + itemstack.stackSize) <= this.getInventoryStackLimit()) {
			this.inventory[4].stackSize += itemstack.stackSize;
		}
		fuelLevel -= getSmeltFuelCost();
		if (fuelLevel < 0) {
			fuelLevel = 0;
		}
		--this.inventory[2].stackSize;
		
		if (this.inventory[2].stackSize <= 0) {
			this.inventory[2] = null;
		}
	}
	
	private boolean hasEnoughFuel() {
		return this.fuelLevel >= getSmeltFuelCost();
	}
	
	@Override
	public String getName() {
		return this.customName;
	}
	
	public void setName(String name) {
		this.customName = name;
	}
	
	@Override
	public boolean hasCustomName() {
		return getName() != null;
	}
	
	@Override
	public int getSizeInventory() {
		return 5;
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
	public boolean isItemValidForSlot(int slot, ItemStack stack) {
		switch (slot) {
			case 0: {
				if (isItemFuel(stack) || stack.getItem() == Items.BUCKET) {
					return true;
				} else {
					return false;
				}
			}
			case 1: {
				if (stack.getItem() == Items.BUCKET || stack.getItem() == Items.LAVA_BUCKET) {
					return true;
				} else {
					return false;
				}
			}
			case 2: {
				if (FurnaceRecipes.instance().getSmeltingResult(stack) != null) {
					return true;
				} else {
					return false;
				}
			}
			default: {
				return true;
			}
		}
	}
	
	public boolean isBurning() {
		return this.shouldBeActive();
	}
	
	public int getCookingTime() {
		return this.mode == 1 ? this.totalCookTime * 5 : this.totalCookTime;
	}
	
	public int getSmeltFuelCost() {
		int regularAmount = 1;
		return this.mode == 1 ? regularAmount * 4 : regularAmount;
	}
	
	private boolean canSmelt() {
		if (inventory[2] == null) {
			return false;
		}
		ItemStack result = FurnaceRecipes.instance().getSmeltingResult(this.inventory[2]);
		return result != null && (inventory[3] == null || inventory[4] == null || (ItemStack.areItemsEqual(inventory[3], result) && this.inventory[3].stackSize < this.getInventoryStackLimit()) || (ItemStack.areItemsEqual(inventory[4], result) && this.inventory[4].stackSize < this.getInventoryStackLimit()));
	}
	
	public boolean isItemFuel(ItemStack stack) {
		return stack.getItem() == Items.LAVA_BUCKET;
	}
	
	public int getField(int id) {
		switch (id) {
			case 1:
				return shouldBeActive() ? 1 : 0;
			case 2:
				return this.cookTime;
			case 3:
				return this.totalCookTime;
			case 4:
				return this.fuelLevel;
			case 5:
				return this.mode;
			default:
				return 0;
		}
	}
	
	public void setField(int id, int value) {
		switch (id) {
			case 1:
				this.isActive = (value == 1) ? true : false;
				break;
			case 2:
				this.cookTime = value;
				break;
			case 3:
				this.totalCookTime = value;
				break;
			case 4:
				this.fuelLevel = value;
				break;
			case 5:
				this.mode = value;
				break;
		}
	}
	
	@Override
	public ITextComponent getDisplayName() {
		return (ITextComponent) (this.hasCustomName() ? new TextComponentString(this.getName()) : new TextComponentTranslation(this.getName(), new Object[0]));
	}
	
	@Override
	public int getFieldCount() {
		return 6;
	}
	
	public int getFuelLevel() {
		return this.fuelLevel;
	}
	
	@Override
	public void clear() {
		for (int i = 0; i < this.inventory.length; ++i) {
			this.inventory[i] = null;
		}
	}
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		super.writeToNBT(compound);
		compound.setInteger("masterX", this.getMasterX());
		compound.setInteger("masterY", this.getMasterY());
		compound.setInteger("masterZ", this.getMasterZ());
		compound.setBoolean("hasMaster", this.hasMaster());
		compound.setBoolean("isMaster", this.isMaster());
		if (this.isMaster()) {
			compound.setInteger("mode", this.mode);
			compound.setString("modeString", this.modeString);
			compound.setBoolean("isActive", this.isActive);
			compound.setInteger("fuelLevel", fuelLevel);
			compound.setInteger("BurnTime", this.burnTime);
			compound.setInteger("CookTime", this.cookTime);
			compound.setInteger("CookTimeTotal", this.totalCookTime);
			NBTTagList nbttaglist = new NBTTagList();
			
			for (int i = 0; i < this.inventory.length; ++i) {
				if (this.inventory[i] != null) {
					NBTTagCompound nbttagcompound = new NBTTagCompound();
					nbttagcompound.setByte("Slot", (byte) i);
					this.inventory[i].writeToNBT(nbttagcompound);
					nbttaglist.appendTag(nbttagcompound);
				}
			}
			
			compound.setTag("Items", nbttaglist);
			
			if (this.hasCustomName()) {
				compound.setString("CustomName", this.getName());
			}
		}
		return compound;
	}
	
	@Override
	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);
		this.setMasterCoords(compound.getInteger("masterX"), compound.getInteger("masterY"), compound.getInteger("masterZ"));
		this.setHasMaster(compound.getBoolean("hasMaster"));
		this.setIsMaster(compound.getBoolean("isMaster"));
		if (this.isMaster()) {
			NBTTagList nbttaglist = compound.getTagList("Items", 10);
			this.inventory = new ItemStack[this.getSizeInventory()];
			
			for (int i = 0; i < nbttaglist.tagCount(); ++i) {
				NBTTagCompound nbttagcompound = nbttaglist.getCompoundTagAt(i);
				int j = nbttagcompound.getByte("Slot");
				
				if (j >= 0 && j < this.inventory.length) {
					this.inventory[j] = ItemStack.loadItemStackFromNBT(nbttagcompound);
				}
			}
			this.mode = compound.getInteger("mode");
			this.modeString = compound.getString("modeString");
			this.isActive = compound.getBoolean("isActive");
			this.fuelLevel = compound.getInteger("fuelLevel");
			this.burnTime = compound.getInteger("BurnTime");
			this.cookTime = compound.getInteger("CookTime");
			this.totalCookTime = compound.getInteger("CookTimeTotal");
			
			if (compound.hasKey("CustomName", 8)) {
				this.setName(compound.getString("CustomName"));
			}
		}
	}
}
