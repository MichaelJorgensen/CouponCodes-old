package com.lala.CouponCodes;

import java.io.File;

import org.bukkit.event.Event.Priority;
import org.bukkit.event.Event.Type;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

import com.iConomy.*;

import com.lala.CouponCodes.Commands.C;
import com.lala.CouponCodes.Configs.Coupon;
import com.lala.CouponCodes.Configs.PluginConfig;
import com.lala.CouponCodes.Logger.*;

public class CouponCodes extends JavaPlugin{
	/**
	 * @author LaLa
	 * This is CouponCodes v1.5.3 - Update 1: Major code cleanup
	 */	
	private final C c = new C(this); // Registers class 'C' for later user
	public static File data; // Used for datafolder
	public iConomy iConomy = null; // iConomy variable
	public static boolean p; // Used for if iConomy exists or not for PluginConfig
	public void onEnable(){
    	PluginDescriptionFile pdfFile = getDescription();
    	getCommand("coupon").setExecutor(c); // Gets command /coupon and sets it to 'C.class'
    	getCommand("c").setExecutor(c); // Same as above but with /c
    	data = getDataFolder(); // Datafolder for configs
        getServer().getPluginManager().registerEvent(Type.PLUGIN_ENABLE, new server(this), Priority.Monitor, this); // For iConomy
        getServer().getPluginManager().registerEvent(Type.PLUGIN_DISABLE, new server(this), Priority.Monitor, this); // For iConomy
        checkiConomy(); // Checks if iConomy exists and sets boolean p
        PluginConfig.loadSettings(data, p); // Loads config
    	log.info("Enabled! <Version: " + pdfFile.getVersion() + ">"); // Logs Enabled! duh...
	}
	public void onDisable(){
		Coupon.saveAll(); // Saves config
		log.info("Disabled!"); // disabled...
	}
	private void checkiConomy(){
		// Checks if iConomy exists
		Plugin iC = this.getServer().getPluginManager().getPlugin("iConomy");
		if (iC != null){
			p = true;
		}else{
			p = false;
		}
	}
}