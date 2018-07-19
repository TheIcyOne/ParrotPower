package com.headfishindustries.parrotpower.proxy;

import com.headfishindustries.parrotpower.ParrotPower;
import com.headfishindustries.parrotpower.defs.BlockDefs;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.Loader;
import thaumcraft.api.ThaumcraftApi;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.crafting.ShapedArcaneRecipe;
import thaumcraft.api.items.ItemsTC;

public class CommonProxy {
	BlockDefs blocks;
	
	public void preInit(){
		blocks = new BlockDefs();
		blocks.preInit();
	}
	
	public void init(){
		if (Loader.isModLoaded("thaumcraft")) {					
			ThaumcraftApi.addArcaneCraftingRecipe(new ResourceLocation(ParrotPower.MODID, "visinator"), 
					new ShapedArcaneRecipe(
							new ResourceLocation(""),
							"visinator",
							50,
							new AspectList().add(Aspect.AIR, 1).add(Aspect.ORDER, 1).add(Aspect.ENTROPY, 1),
							new ItemStack(BlockDefs.volitantVisinator), 
							new Object[] {
									'G', new ItemStack(Items.GOLD_INGOT),
									'S', new ItemStack(ItemsTC.amber),
									'A', new ItemStack(BlockDefs.avesAlternator)}
					));			
			
			ThaumcraftApi.registerResearchLocation(new ResourceLocation(ParrotPower.MODID, "tc_research/volitant_visinator"));
		
		}
		
	}
	
	public void postInit(){
		if (Loader.isModLoaded("thaumcraft")) {
			
		}
	}
}
