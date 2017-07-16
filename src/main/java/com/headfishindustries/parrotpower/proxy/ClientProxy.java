package com.headfishindustries.parrotpower.proxy;

import com.headfishindustries.parrotpower.defs.BlockDefs;

public class ClientProxy extends CommonProxy{
	@Override
	public void preInit(){
		super.preInit();
	}
	
	@Override
	public void init(){
		super.init();
		BlockDefs.initClient();
	}
	
	@Override
	public void postInit(){
		super.postInit();
	}
}
