package com.headfishindustries.parrotpower.tile;

import java.util.List;

import com.headfishindustries.parrotpower.block.BlockAvesAlternator;

import net.minecraft.block.BlockJukebox;
import net.minecraft.entity.passive.EntityParrot;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;

public class TileEntityAvesAlternator extends TileEntity implements IEnergyStorage, ITickable{
	
	 int energy;
	 final int maxGen = 1000;
	 boolean active;
	 static final int range = 5;

	public TileEntityAvesAlternator() {
		super();
		energy = 0;
		active = false;
	}
	
	@Override
	public void update(){
		getWorld().profiler.startSection("Parrot Party Power Production");
		if (!(world.getWorldTime() % 20 == 0)) return;
		active = false;
		int powerGen = 0;
		
		if ((world.getBlockState(pos.up()).getBlock() instanceof BlockJukebox)) active = true;
		
		if (((world.getBlockState(pos.up()) == Blocks.JUKEBOX.getDefaultState().withProperty(BlockJukebox.HAS_RECORD, true)))){
		List<EntityParrot> parrots = world.getEntitiesWithinAABB(EntityParrot.class, new AxisAlignedBB(pos.add(-range, -range + 1, -range), pos.add(range, range + 1, range)));
			for (EntityParrot parrot : parrots){
				parrot.setPartying(pos.up(), true);
				powerGen+= 1;
			}
		}
			((BlockAvesAlternator)world.getBlockState(pos).getBlock()).setState(active, world, pos);
		this.energy = Math.min(this.maxGen, powerGen + this.energy);
		getWorld().profiler.endStartSection("Parrot Party Power Pushing");
		
		for (EnumFacing fd : EnumFacing.VALUES) {
            TileEntity te = world.getTileEntity(new BlockPos(fd.getFrontOffsetX() + this.pos.getX(), fd.getFrontOffsetY() + this.pos.getY(), fd.getFrontOffsetZ() + this.pos.getZ()));
            if (te instanceof IEnergyStorage) {
                    IEnergyStorage es = (IEnergyStorage) te;
                        energy -= es.receiveEnergy(energy, true);
            }
		}
		getWorld().profiler.endSection();
	}

	@Override
	public int receiveEnergy(int maxReceive, boolean simulate) {
		return 0;
	}

	@Override
	public int extractEnergy(int maxExtract, boolean simulate) {
		if (!simulate) this.energy -= Math.min(this.getEnergyStored(), maxExtract);
		return Math.min(this.getEnergyStored(), maxExtract);
	}

	@Override
	public int getEnergyStored() {
		return this.energy;
	}

	@Override
	public int getMaxEnergyStored() {
		return this.maxGen;
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
		return super.writeToNBT(compound);	
	}
	
	@Override
	public void readFromNBT(NBTTagCompound compound){
		super.readFromNBT(compound);
	}
	
	@Override
	public <T> T getCapability(Capability<T> cap, final EnumFacing from) {

			if (cap == CapabilityEnergy.ENERGY) {
				return CapabilityEnergy.ENERGY.cast(new net.minecraftforge.energy.IEnergyStorage(){

					@Override
					public int receiveEnergy(int maxReceive, boolean simulate) {
						return TileEntityAvesAlternator.this.receiveEnergy(maxReceive, simulate);
					}

					@Override
					public int extractEnergy(int maxExtract, boolean simulate) {
						return TileEntityAvesAlternator.this.extractEnergy(maxExtract, simulate);
					}

					@Override
					public int getEnergyStored() {
						return TileEntityAvesAlternator.this.getEnergyStored();
					}

					@Override
					public int getMaxEnergyStored() {
						return TileEntityAvesAlternator.this.getMaxEnergyStored();
					}

					@Override
					public boolean canExtract() {
						return TileEntityAvesAlternator.this.canExtract();
					}

					@Override
					public boolean canReceive() {
						return  TileEntityAvesAlternator.this.canReceive();
					}});
					
				
			}	
			return super.getCapability(cap, from);
	}

}
