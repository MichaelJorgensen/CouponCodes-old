package com.lala.CouponCodes.Configs;

import java.io.File;

import org.bukkit.util.config.Configuration;

import com.lala.CouponCodes.Logger.log;

public class PluginConfig extends Configuration{
	public static String author = "LaLa";
	public static boolean iConomy;
	public static boolean icpriceenabled;
	public static int icprice;
	public static String nopmsg;
	public static String nopmsgcolor;
	public static String notaplayer;
	public static String syntaxparseissue;
	public static String couponalreadyexists;
	public static String couponalreadyused;
	public static String coupondoesntexist;
	public static String couponremoved;
	public static String couponalreadyrenewed;
	public static String couponsremoved;
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
		nopmsg = yml.getString("config.messages.insufficientpermissions.msg", "You do not have permission to use this command.");
		notaplayer = yml.getString("config.messages.notaplayer", "You need to be a player for me to work!");
		nopmsgcolor = yml.getString("config.messages.insufficientpermissions.color", "RED");
		syntaxparseissue = yml.getString("config.messages.syntaxparseissue", "Error on syntax. String instead of int?");
		couponalreadyexists = yml.getString("config.messages.couponalreadyexists", "That coupon already exists!");
		couponalreadyused = yml.getString("config.messages.couponalreadyused", "That coupon has already been used!");
		coupondoesntexist = yml.getString("config.messages.coupondoesntexist", "That coupon doesn't exist!");
		couponremoved = yml.getString("config.messages.couponremoved", "Coupon removed!");
		couponalreadyrenewed = yml.getString("config.messages.couponalreadyrenewed", "Coupon has already been renewed!");
		couponsremoved = yml.getString("config.messages.allcouponsremoved", "All coupons have been removed!");
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
	public static void reloadSettings(File dataFolder){
		final File yaml = new File(dataFolder, "Config.yml");
		if (!dataFolder.exists()){
			dataFolder.mkdirs();
		}
		final PluginConfig yml = new PluginConfig(yaml);
		if (yaml.exists()){
			yml.load();
		}
		icprice = yml.getInt("config.iconomy.price", 10);
		icpriceenabled = yml.getBoolean("config.iconomy.price.enabled", false);
		nopmsg = yml.getString("config.messages.insufficientpermissions.msg", "You do not have permission to use this command.");
		notaplayer = yml.getString("config.messages.notaplayer", "You need to be a player for me to work!");
		nopmsgcolor = yml.getString("config.messages.insufficientpermissions.color", "RED");
		syntaxparseissue = yml.getString("config.messages.syntaxparseissue", "Error on syntax. String instead of int?");
		couponalreadyexists = yml.getString("config.messages.couponalreadyexists", "That coupon already exists!");
		couponalreadyused = yml.getString("config.messages.couponalreadyused", "That coupon has already been used!");
		coupondoesntexist = yml.getString("config.messages.coupondoesntexist", "That coupon doesn't exist!");
		couponremoved = yml.getString("config.messages.couponremoved", "Coupon removed!");
		couponalreadyrenewed = yml.getString("config.messages.couponalreadyrenewed", "Coupon has already been renewed!");
		couponsremoved = yml.getString("config.messages.allcouponsremoved", "All coupons have been removed!");
		oneuseonly = yml.getBoolean("config.coupons.onetimeuseperplayer", true);
		debug = yml.getBoolean("config.debug", false);
		yml.save();
	}
}
