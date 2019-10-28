package com.headfishindustries.parrotpower.tile;

import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.energy.IEnergyStorage;
import thaumcraft.api.aura.AuraHelper;

import java.util.List;

import com.headfishindustries.parrotpower.block.BlockAvesAlternator;
import com.headfishindustries.parrotpower.block.BlockVolitantVisinator;

import net.minecraft.block.BlockJukebox;
import net.minecraft.entity.passive.EntityParrot;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;

public class TileEntityVolitantVisinator extends TileEntity implements ITickable{
	
	final int maxGen = 100;
	boolean active;
	static final int range = 5;

	public TileEntityVolitantVisinator() {
		super();
		active = false;
	}


	@Override
	public void update() {
		getWorld().profiler.startSection("Parrot Party Vis Volumetrics");
		if (!(world.getWorldTime() % 20 == 0)) return;
		active = false;
		int powerGen = 0;
		
		if ((world.getBlockState(pos.up()).getBlock() instanceof BlockJukebox)) active = true;
		
		if (((world.getBlockState(pos.up()) == Blocks.JUKEBOX.getDefaultState().withProperty(BlockJukebox.HAS_RECORD, true)))){
		List<EntityParrot> parrots = world.getEntitiesWithinAABB(EntityParrot.class, new AxisAlignedBB(pos.add(-range, -range + 1, -range), pos.add(range, range + 1, range)));
			for (EntityParrot parrot : parrots){
				if (world.isRemote) {
					parrot.setPartying(pos.up(), true);
				}
				powerGen+= 1;
			}
		}
		((BlockVolitantVisinator)world.getBlockState(pos).getBlock()).setState(active, world, pos);
		getWorld().profiler.endStartSection("Parrot Party Vis Vapours");
			AuraHelper.addVis(world, pos, powerGen * 0.01667f);		
		getWorld().profiler.endSection();
		
	}

}
