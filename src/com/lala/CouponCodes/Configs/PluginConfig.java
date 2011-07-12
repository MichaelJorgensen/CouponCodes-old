package com.lala.CouponCodes.Configs;

import java.io.File;

import org.bukkit.util.config.Configuration;

import com.lala.CouponCodes.Logger.log;

public class PluginConfig extends Configuration{
	public static String author = "LaLa";
	public static boolean iConomy;
	public static boolean icpriceenabled;
	public static int icprice;
	public static boolean oneuseonly;
	public static boolean debug;
	public PluginConfig(File file){
		super(file);
	}
	public static void loadSettings(File dataFolder, boolean p){
		final File yaml = new File(dataFolder, "Config.yml");		
		if (!dataFolder.exists()){
			dataFolder.mkdirs();
		}
		final PluginConfig yml = new PluginConfig(yaml);
		if (yaml.exists()){
			yml.load();
		}
		yml.setHeader("#Learn more about how this config can be edited and changed to your preference on the forum page.");		
		icprice = yml.getInt("config.iconomy.couponcost.price", 10);
		icpriceenabled = yml.getBoolean("config.iconomy.couponcost.enabled", false);
		yml.setProperty("author", "LaLa");
		iConomy = yml.getBoolean("config.iconomy.enabled", false);
		yml.setProperty("config.iconomy.has", p);
		oneuseonly = yml.getBoolean("config.coupons.onetimeuseperplayer", true);
		debug = yml.getBoolean("config.debug", false);
		yml.save();
		if (p == false && iConomy == true || p == false && icpriceenabled == true || iConomy == false && icpriceenabled == true){
			log.severe("iConomy is enabled, but iConomy was not found or is disabled. Disabling iConomy");
			yml.setProperty("config.iconomy.enabled", false);
			yml.setProperty("config.iconomy.couponcost.enabled", false);
			iConomy = yml.getBoolean("config.iconomy.enabled", false);
			icpriceenabled = yml.getBoolean("config.iconomy.couponcost.enabled", false);
			yml.save();
			return;
		}		
	}
}
