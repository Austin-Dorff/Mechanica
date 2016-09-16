package com.austindorff.mechanica.blocks.world;

import java.util.List;
import java.util.Random;

import com.austindorff.mechanica.Mechanica;
import com.austindorff.mechanica.world.gen.WorldGenRubberTree;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.BlockSapling;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenBigTree;
import net.minecraft.world.gen.feature.WorldGenTrees;
import net.minecraft.world.gen.feature.WorldGenerator;

public class BlockMechanicaSaplings extends BlockSapling {
	
	private static final String[]	saplings	= new String[] { "Rubber" };
												
	@SideOnly(Side.CLIENT)
	private static IIcon[]			textures;
									
	public BlockMechanicaSaplings() {
		float f = 0.4F;
		this.setBlockBounds(0.5F - f, 0.0F, 0.5F - f, 0.5F + f, f * 2.0F, 0.5F + f);
		this.setCreativeTab(Mechanica.tabMechanica);
	}
	
	@Override
    public void updateTick(World world, int posX, int posY, int posZ, Random rand)
    {
        if (!world.isRemote)
        {
            super.updateTick(world, posX, posY, posZ, rand);

            if (world.getBlockLightValue(posX, posY + 1, posZ) >= 9 && rand.nextInt(7) == 0)
            {
                this.func_149879_c(world, posX, posY, posZ, rand);
            }
        }
    }
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister iconRegister) {
		textures = new IIcon[saplings.length];
		
		for (int i = 0; i < saplings.length; i++) {
			textures[i] = iconRegister.registerIcon(Mechanica.MODID + ":" + saplings[i] + "TreeSapling");
		}
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void getSubBlocks(Item block, CreativeTabs creativeTabs, List list) {
		for (int i = 0; i < saplings.length; i++) {
			list.add(new ItemStack(block, 1, i));
		}
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int meta) {
		return textures[meta];
	}
	
	@Override
	public int damageDropped(int meta) {
		return meta;
	}
	
	@Override
	public boolean isOpaqueCube() {
		return false;
	}
	
    public void func_149879_c(World world, int posX, int posY, int posZ, Random rand)
    {
        int l = world.getBlockMetadata(posX, posY, posZ);
        this.func_149878_d(world, posX, posY, posZ, rand);
    }
	
    @Override
	public void func_149878_d(World world, int posX, int posY, int posZ, Random rand) {
		if (!net.minecraftforge.event.terraingen.TerrainGen.saplingGrowTree(world, rand, posX, posY, posZ))
			return;
		int blockMeta = world.getBlockMetadata(posX, posY, posZ);
		Object generatorObject = rand.nextInt(10) == 0 ? new WorldGenBigTree(true) : new WorldGenTrees(true);
		boolean flag = false;
		
		switch (blockMeta) {
			case 0:
				generatorObject = new WorldGenRubberTree(false);
				flag = true;
				break;
		}
		
		Block block = Blocks.air;
		
		if (flag) {
			world.setBlock(posX, posY, posZ, block, 0, 1);
			world.setBlock(posX + 1, posY, posZ, block, 0, 1);
			world.setBlock(posX, posY, posZ + 1, block, 0, 1);
			world.setBlock(posX + 1, posY, posZ + 1, block, 0, 1);
		} else {
			world.setBlock(posX, posY, posZ, block, 0, 1);
		}
		
		if (!((WorldGenerator) generatorObject).generate(world, rand, posX, posY, posZ)) {
			if (flag) {
				world.setBlock(posX, posY, posZ, this, blockMeta, 1);
				world.setBlock(posX + 1, posY, posZ, this, blockMeta, 1);
				world.setBlock(posX, posY, posZ + 1, this, blockMeta, 1);
				world.setBlock(posX + 1, posY, posZ + 1, this, blockMeta, 1);
			} else {
				world.setBlock(posX, posY, posZ, this, blockMeta, 1);
			}
		}
	}
	
    public boolean func_149851_a(World world, int posX, int posY, int posZ, boolean bool)
    {
        return true;
    }

    public boolean func_149852_a(World world, Random rand, int posX, int posY, int posZ)
    {
        return (double)world.rand.nextFloat() < 0.45D;
    }

    public void func_149853_b(World world, Random rand, int posX, int posY, int posZ)
    {
        this.func_149879_c(world, posX, posY, posZ, rand);
    }
}
