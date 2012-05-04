package com.mikecaron.tectonicus.ic2;

import java.util.EnumSet;
import java.util.Properties;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.File;
import java.io.IOException;
import org.w3c.dom.Element;

import tectonicus.Plugin;
import tectonicus.BlockTypeRegistry;
import tectonicus.configuration.XmlConfigurationParser;

public class IC2Plugin extends Plugin
{
	private static final EnumSet<Plugin.Feature> features = EnumSet.of(
		Plugin.Feature.BlockTypes
	);
	
	private File configFilePath;
	private Properties ic2Config;
	
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
		Element configFileNode = XmlConfigurationParser.getChild(config, "configFile");
		
		if(configFileNode != null) {
			String p = XmlConfigurationParser.getString(configFileNode, "path");
			
			//System.out.println("IC2 config file: " + p);
			
			configFilePath = new File( p );
			
			try {
				InputStream is = new FileInputStream( configFilePath );
				
				ic2Config.load(is);
				
				is.close();
			} catch(IOException ex) {
				// nom
				throw new Error("Unable to read config file", ex);
			}
			
			//System.out.println("IC2's machine block id is: " + ic2Config.getProperty("blockMachine", "null"));
			
		}
	}
	
	
	public EnumSet<Plugin.Feature> getFeatures()
	{
		return features;
	}
	
	
	public void registerBlockTypes(BlockTypeRegistry registry)
	{
		
	}
}