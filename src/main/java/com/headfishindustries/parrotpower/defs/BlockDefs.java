package com.headfishindustries.parrotpower.defs;

import com.headfishindustries.parrotpower.ParrotPower;
import com.headfishindustries.parrotpower.block.BlockAvesAlternator;
import com.headfishindustries.parrotpower.block.BlockVolitantVisinator;
import com.headfishindustries.parrotpower.tile.TileEntityAvesAlternator;
import com.headfishindustries.parrotpower.tile.TileEntityVolitantVisinator;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;


public class BlockDefs {
	public static Block avesAlternator; 
	public static Block volitantVisinator; 

	public void preInit(){
		//Do things later. Not now.
		//Fine, do them now if you want.
		GameRegistry.registerTileEntity(TileEntityAvesAlternator.class, "aves_alternator");
		if (Loader.isModLoaded("thaumcraft"))
		GameRegistry.registerTileEntity(TileEntityVolitantVisinator.class, "volitant_visinator");
		MinecraftForge.EVENT_BUS.register(this.getClass());
	}
	
	private static void registerTexture(Block block) {
		ResourceLocation loc = block.getRegistryName();
		Item item = GameRegistry.findRegistry(Item.class).getValue(loc);
		Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(item, 0, (new ModelResourceLocation(loc, "inventory")));
	}
	
	@SideOnly(Side.CLIENT)
	public static void initClient() {
		registerTexture(avesAlternator);
		if (Loader.isModLoaded("thaumcraft"))
		registerTexture(volitantVisinator);
	}
	
	@SubscribeEvent
	public static void registerBlocks(RegistryEvent.Register<Block> e) {
		avesAlternator = registerBlock(new BlockAvesAlternator(), "aves_alternator").setUnlocalizedName("aves_alternator"); 
		if (Loader.isModLoaded("thaumcraft"))
		volitantVisinator = registerBlock(new BlockVolitantVisinator(), "volitant_visinator").setUnlocalizedName("volitant_visinator");
	}
	
			
private static Block registerBlock(Block block, String name){
		
		if(block.getRegistryName() == null) block.setRegistryName(name);
		ForgeRegistries.BLOCKS.register(block);
		ParrotPower.LOGGER.info("Registering block " + block.getRegistryName());
		ForgeRegistries.ITEMS.register(new ItemBlock(block).setRegistryName(name));
		return block;
	}


}
