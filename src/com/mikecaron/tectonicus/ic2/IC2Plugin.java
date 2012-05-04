package com.mikecaron.tectonicus.ic2;

import java.util.EnumSet;
import java.util.Properties;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.File;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import org.w3c.dom.Element;

import tectonicus.Plugin;
import tectonicus.BlockTypeRegistry;
import tectonicus.configuration.XmlConfigurationParser;
import tectonicus.texture.*;

public class IC2Plugin extends Plugin
{
	private static final EnumSet<Plugin.Feature> features = EnumSet.of(
		Plugin.Feature.BlockTypes,
		Plugin.Feature.Textures
	);
	
	private File configFilePath;
	private File jarFilePath;
	private Properties ic2Config;
	private ZipFile ic2Jar;
	
	public IC2Plugin()
	{
		ic2Config = new Properties();
	}
	
	
	public String getName()
	{
		return "IndustrialCraft2 Tectonicus Module";
	}
	
	
	public void loadConfiguration(Element config)
	{
		configFilePath = new File( XmlConfigurationParser.getString(config, "configFile") );
		jarFilePath = new File( XmlConfigurationParser.getString(config, "jarFile") );

		if(configFilePath.exists())
		{
			try {
				InputStream is = new FileInputStream( configFilePath );
				
				ic2Config.load(is);
				
				is.close();
			} catch(IOException ex) {
				// nom
				throw new Error("Unable to read config file", ex);
			}
		}	
		
		if(jarFilePath.exists())
		{
			try {
				ic2Jar = new ZipFile(jarFilePath);
			} catch(IOException ex) {
				throw new Error("Unable to read jar file");
			}
		}
		
		//System.out.println("IC2's machine block id is: " + ic2Config.getProperty("blockMachine", "null"));
			
		
		
		
	}
	
	
	public EnumSet<Plugin.Feature> getFeatures()
	{
		return features;
	}
	
	
	public void registerBlockTypes(TexturePack texturePack, BlockTypeRegistry registry)
	{
		int ic2MachineBlock = Integer.parseInt(ic2Config.getProperty("blockMachine", "-1"));

		registry.register(ic2MachineBlock, new IC2Machine("Iron Furnace", texturePack, "ic2/sprites/block_machine.png", 1));
		
	}
	
	public InputStream loadTexture(String path) throws IOException
	{
		//System.out.println("Got a requrest for " + path);
		if(ic2Jar != null)
		{
			ZipEntry ze = ic2Jar.getEntry(path);
			if(ze != null) {
				return ic2Jar.getInputStream(ze);
			}
		}
		return null;
	}
}