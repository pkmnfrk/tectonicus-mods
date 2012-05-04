package com.mikecaron.tectonicus.ic2;

import java.util.EnumSet;
import org.w3c.dom.Element;

import tectonicus.Plugin;
import tectonicus.configuration.XmlConfigurationParser;

public class IC2Plugin implements Plugin
{
	private static final EnumSet<Plugin.Feature> features = EnumSet.of(
		Plugin.Feature.BlockTypes
	);
	
	private String configFilePath;
	
	public String getName()
	{
		return "IndustrialCraft2 Tectonicus Module";
	}
	
	public void loadConfiguration(Element config)
	{
		Element configFileNode = XmlConfigurationParser.getChild(config, "configFile");
		
		if(configFileNode != null) {
			configFilePath = configFileNode.getNodeValue();
		}
	}
	
	public EnumSet<Plugin.Feature> getFeatures()
	{
		return features;
	}
}