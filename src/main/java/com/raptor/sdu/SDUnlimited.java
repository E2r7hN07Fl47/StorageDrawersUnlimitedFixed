package com.raptor.sdu;

import java.io.File;

import com.raptor.sdu.event.RegisterOreDictionary;
import org.apache.logging.log4j.Logger;

import com.raptor.sdu.proxy.CommonProxy;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;

@Mod(modid = SDUnlimited.MODID, version = SDUnlimited.VERSION, name = "Storage Drawers Unlimited Fixed",
		useMetadata = true)
public class SDUnlimited {
	public static final String MODID = "storagedrawersunlimited";
	public static final String VERSION = "${version}";
	
	@Instance(SDUnlimited.MODID)
	public static SDUnlimited instance;
	
	@SidedProxy(clientSide = "com.raptor.sdu.proxy.ClientProxy", serverSide = "com.raptor.sdu.proxy.CommonProxy")
	public static CommonProxy proxy;
	
	public static Logger logger;
	
	public static File configFolder; 
	
	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		logger = event.getModLog();
		configFolder = new File(event.getModConfigurationDirectory(), "storagedrawersunlimited");
		if(!configFolder.isDirectory())
			configFolder.mkdirs();
		proxy.preInit(event);
	}
	
	@EventHandler
	public void init(FMLInitializationEvent event) {
		RegisterOreDictionary.register(); // Removed from preInit as it was too early.
		// Removing from preInit causes bug in JEI, when there is no recipe with this mod's items by ore dict.
		// But registering in preInit causes bug, when no ore dict registered.
		proxy.init(event);
	}

	@EventHandler
	public void postInit(FMLPostInitializationEvent event) {
		proxy.postInit(event);
	}
	
	@EventHandler
	public void serverInit(FMLServerStartingEvent event) {
		proxy.serverInit(event);
	}
}
