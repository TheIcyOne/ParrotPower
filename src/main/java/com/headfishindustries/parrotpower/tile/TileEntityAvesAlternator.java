package com.headfishindustries.parrotpower.tile;

import java.util.List;

import com.headfishindustries.parrotpower.block.BlockAvesAlternator;

import net.minecraft.block.BlockJukebox;
import net.minecraft.entity.passive.EntityParrot;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraftforge.energy.IEnergyStorage;

public class TileEntityAvesAlternator extends TileEntity implements IEnergyStorage, ITickable{
	
	 int energy;
	 int capacity = 1000;
	 int maxExtract = 1000;
	 int maxGen = 1000;
	 boolean active;
	 static int range = 5;

	public TileEntityAvesAlternator() {
		super();
		energy = 0;
		active = false;
	}
	
	@Override
	public void update(){
		getWorld().profiler.startSection("Parrot Party Power");
		if (!(world.getWorldTime() % 20 == 0)) return;
		active = false;
		int powerGen = 0;
		
		if ((world.getBlockState(pos.up()).getBlock() instanceof BlockJukebox)) active = true;
		
		if (((world.getBlockState(pos.up()) == Blocks.JUKEBOX.getDefaultState().withProperty(BlockJukebox.HAS_RECORD, true)))){
		List<EntityParrot> parrots = world.getEntitiesWithinAABB(EntityParrot.class, new AxisAlignedBB(pos.add(-range, -range + 1, -range), pos.add(range, range + 1, range)));
			for (EntityParrot parrot : parrots){
				parrot.setPartying(pos.up(), true);
				powerGen++;
			}
		}
			((BlockAvesAlternator)world.getBlockState(pos).getBlock()).setState(active, world, pos);
		this.energy = Math.min(this.capacity, Math.min(this.maxGen, powerGen));
		getWorld().profiler.endSection();
	}

	@Override
	public int receiveEnergy(int maxReceive, boolean simulate) {
		return 0;
	}

	@Override
	public int extractEnergy(int maxExtract, boolean simulate) {
		if (!simulate) this.energy -= maxExtract;
		return Math.min(this.getEnergyStored(), maxExtract);
	}

	@Override
	public int getEnergyStored() {
		return this.energy;
	}

	@Override
	public int getMaxEnergyStored() {
		return this.capacity;
	}

	@Override
	public boolean canExtract() {
		if (this.energy >= 0) return true;
		else return false;
	}

	@Override
	public boolean canReceive() {
		return false;
	}
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound){
		super.writeToNBT(compound);		
		compound.setInteger("ParrotPowerStored", this.energy);
		return compound;
	}
	
	@Override
	public void readFromNBT(NBTTagCompound compound){
		super.readFromNBT(compound);
		this.energy = compound.getInteger("ParrotPowerStored");
	}

}
