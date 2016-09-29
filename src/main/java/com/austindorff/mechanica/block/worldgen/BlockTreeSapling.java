package com.austindorff.mechanica.block.worldgen;

import java.util.Random;

import com.austindorff.mechanica.Reference;
import com.austindorff.mechanica.world.gen.plant.tree.GenRubberTree;

import net.minecraft.block.BlockBush;
import net.minecraft.block.IGrowable;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

public class BlockTreeSapling extends BlockBush implements IGrowable {
	
	public static final IProperty<EnumType>	TYPES	= PropertyEnum.create("type", BlockTreeSapling.EnumType.class);
	public static final PropertyInteger		STAGE	= PropertyInteger.create("stage", 0, 1);
													
	public BlockTreeSapling(String name) {
		this.setUnlocalizedName(Reference.addBlockUnlocalizedName(name));
		this.setRegistryName(Reference.addBlockRegistryName(name));
        this.setCreativeTab(Reference.TAB_MECHANICA);
	}
	
	@Override
	public BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, new IProperty[] { TYPES, STAGE });
	}
	
	@Override
	public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
		if (!worldIn.isRemote) {
			super.updateTick(worldIn, pos, state, rand);
			
			if (worldIn.getLightFromNeighbors(pos.up()) >= 9 && rand.nextInt(7) == 0) {
				this.grow(worldIn, pos, state, rand);
			}
		}
	}
	
	public void grow(World worldIn, BlockPos pos, IBlockState state, Random rand) {
		if (((Integer) state.getValue(STAGE)).intValue() == 0) {
			worldIn.setBlockState(pos, state.cycleProperty(STAGE), 4);
		} else {
			this.generateTree(worldIn, pos, state, rand);
		}
	}
	
	public boolean isTypeAt(World worldIn, BlockPos pos, BlockTreeSapling.EnumType type) {
		IBlockState iblockstate = worldIn.getBlockState(pos);
		return iblockstate.getBlock() == this && iblockstate.getValue(TYPES) == type;
	}
	
	public void generateTree(World worldIn, BlockPos pos, IBlockState state, Random rand)
    {
        if (!net.minecraftforge.event.terraingen.TerrainGen.saplingGrowTree(worldIn, rand, pos)) return;
        WorldGenerator worldgenerator = (WorldGenerator)(new GenRubberTree(true));
        int i = 0;
        int j = 0;
        boolean flag = false;

        IBlockState iblockstate2 = Blocks.AIR.getDefaultState();

        if (flag)
        {
            worldIn.setBlockState(pos.add(i, 0, j), iblockstate2, 4);
            worldIn.setBlockState(pos.add(i + 1, 0, j), iblockstate2, 4);
            worldIn.setBlockState(pos.add(i, 0, j + 1), iblockstate2, 4);
            worldIn.setBlockState(pos.add(i + 1, 0, j + 1), iblockstate2, 4);
        }
        else
        {
            worldIn.setBlockState(pos, iblockstate2, 4);
        }

        if (!worldgenerator.generate(worldIn, rand, pos.add(i, 0, j)))
        {
            if (flag)
            {
                worldIn.setBlockState(pos.add(i, 0, j), state, 4);
                worldIn.setBlockState(pos.add(i + 1, 0, j), state, 4);
                worldIn.setBlockState(pos.add(i, 0, j + 1), state, 4);
                worldIn.setBlockState(pos.add(i + 1, 0, j + 1), state, 4);
            }
            else
            {
                worldIn.setBlockState(pos, state, 4);
            }
        }
    }
	
	@Override
	public IBlockState getStateFromMeta(int meta) {
		return this.getDefaultState().withProperty(TYPES, BlockTreeSapling.EnumType.byMetadata(meta & 7)).withProperty(STAGE, Integer.valueOf((meta & 8) >> 3));
	}
	
	@Override
	public int getMetaFromState(IBlockState state) {
		byte b0 = 0;
		int i = b0 | ((BlockTreeSapling.EnumType) state.getValue(TYPES)).getMetadata();
		i |= ((Integer) state.getValue(STAGE)).intValue() << 3;
		return i;
	}
	
	static final class SwitchEnumType {
		static final int[] SAPLING_TYPE_LOOKUP = new int[BlockTreeSapling.EnumType.values().length];
		
		static {
			try {
				SAPLING_TYPE_LOOKUP[BlockTreeSapling.EnumType.RUBBER.ordinal()] = 1;
			} catch (NoSuchFieldError var6) {
				;
			}
		}
	}
	
	public static enum EnumType implements IStringSerializable {
		RUBBER(0, "rubber");
		private static final BlockTreeSapling.EnumType[]	META_LOOKUP	= new BlockTreeSapling.EnumType[values().length];
		private final int									meta;
		private final String								name;
		private final String								unlocalizedName;
															
		private EnumType(int meta, String name) {
			this(meta, name, name);
		}
		
		private EnumType(int meta, String name, String unlocalizedName) {
			this.meta = meta;
			this.name = name;
			this.unlocalizedName = unlocalizedName;
		}
		
		public int getMetadata() {
			return this.meta;
		}
		
		public String toString() {
			return this.name;
		}
		
		public static BlockTreeSapling.EnumType byMetadata(int meta) {
			if (meta < 0 || meta >= META_LOOKUP.length) {
				meta = 0;
			}
			
			return META_LOOKUP[meta];
		}
		
		public String getName() {
			return this.name;
		}
		
		public String getUnlocalizedName() {
			return this.unlocalizedName;
		}
		
		static {
			BlockTreeSapling.EnumType[] var0 = values();
			int var1 = var0.length;
			
			for (int var2 = 0; var2 < var1; ++var2) {
				BlockTreeSapling.EnumType var3 = var0[var2];
				META_LOOKUP[var3.getMetadata()] = var3;
			}
		}
	}
	
	@Override
	public boolean isOpaqueCube(IBlockState state) {
		return false;
	}

    public boolean canGrow(World worldIn, BlockPos pos, IBlockState state, boolean isClient)
    {
        return true;
    }

    public boolean canUseBonemeal(World worldIn, Random rand, BlockPos pos, IBlockState state)
    {
        return (double)worldIn.rand.nextFloat() < 0.45D;
    }

    public void grow(World worldIn, Random rand, BlockPos pos, IBlockState state)
    {
        this.grow(worldIn, pos, state, rand);
    }
}
