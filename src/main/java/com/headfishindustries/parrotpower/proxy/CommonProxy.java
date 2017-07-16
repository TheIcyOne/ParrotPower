package com.headfishindustries.parrotpower.proxy;

import com.headfishindustries.parrotpower.defs.BlockDefs;

public class CommonProxy {
	BlockDefs blocks;
	
	public void preInit(){
		blocks = new BlockDefs();
		blocks.preInit();
	}
	
	public void init(){
		
	}
	
	public void postInit(){
		
	}
}
