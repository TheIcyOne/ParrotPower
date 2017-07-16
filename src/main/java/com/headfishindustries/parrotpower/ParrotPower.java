package com.headfishindustries.parrotpower;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.headfishindustries.parrotpower.proxy.CommonProxy;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(modid=ParrotPower.MODID, version=ParrotPower.VERSION, name=ParrotPower.NAME, canBeDeactivated=true)
public class ParrotPower {
	public static final String MODID = "parrotpower";
	public static final String VERSION = "%gradle.version%";
	public static final String NAME = "Parrot Power";
	
	public static final Logger LOGGER = LogManager.getLogger(MODID);
	
	@SidedProxy(clientSide="com.headfishindustries.parrotpower.proxy.ClientProxy", serverSide="com.headfishindustries.parrotpower.proxy.CommonProxy", modId=MODID)
	public static CommonProxy proxy;
	
	@EventHandler
	void preInit(FMLPreInitializationEvent e){
		proxy.preInit();
	}
	
	@EventHandler
	void init(FMLInitializationEvent e){
		proxy.init();
	}
}