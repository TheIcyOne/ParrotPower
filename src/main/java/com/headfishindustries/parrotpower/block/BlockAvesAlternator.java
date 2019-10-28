package com.headfishindustries.parrotpower.block;

import com.headfishindustries.parrotpower.ParrotPower;
import com.headfishindustries.parrotpower.defs.BlockDefs;
import com.headfishindustries.parrotpower.tile.TileEntityAvesAlternator;


import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;

public class BlockAvesAlternator extends Block implements ITileEntityProvider{
	
	public static PropertyInteger ISACTIVE = PropertyInteger.create("active", 0, 1);

	public BlockAvesAlternator() {
		super(Material.CLOTH);
		setCreativeTab(CreativeTabs.DECORATIONS);
		this.setHardness(1.5f);
		this.setResistance(10.0f);
		this.setHarvestLevel("pickaxe", 1);
		this.setRegistryName(ParrotPower.MODID, "aves_alternator");
	}
	
	public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack)
    {
		if (worldIn.getTileEntity(pos) == null)
		createNewTileEntity(worldIn, 0);
    }
	
	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileEntityAvesAlternator();
	}
	
	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState s, EntityPlayer player, EnumHand hand, EnumFacing facing, float x, float y, float z){
		if (world.getTileEntity(pos) != null && world.getTileEntity(pos) instanceof TileEntityAvesAlternator){
		if(!world.isRemote){
			TileEntityAvesAlternator aa =(TileEntityAvesAlternator)world.getTileEntity(pos);
			player.sendMessage(new TextComponentString(String.format("\247o%s", "Energy stored: " + aa.getEnergyStored() + "/" + aa.getMaxEnergyStored())));
		}
		return true;
		}
		return false;
	}
	
	public void setState(boolean active, World w, BlockPos pos){
		if (active){
			w.setBlockState(pos, BlockDefs.avesAlternator.getDefaultState().withProperty(ISACTIVE, 1));
		}else {
			w.setBlockState(pos, BlockDefs.avesAlternator.getDefaultState().withProperty(ISACTIVE, 0));
		}
	}
	
	protected BlockStateContainer createBlockState(){
		return new BlockStateContainer(this, new IProperty[] {ISACTIVE});
	}
	
	public IBlockState getStateFromMeta(int meta){
		IBlockState state = this.getDefaultState();
		
		switch (meta & 0b1100){
		case 0:
			return state.withProperty(ISACTIVE, 0);
		case 1:
			return state.withProperty(ISACTIVE, 1);
		}
		return state;
	}
	
	public int getMetaFromState(IBlockState state)
    {
        switch (state.getValue(ISACTIVE))
        {
            case 0: return 0;
            case 1: return 1;
            default: return 0;
        }
    }

}
