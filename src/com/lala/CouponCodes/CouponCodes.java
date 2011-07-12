package com.lala.CouponCodes;

import java.io.File;

import org.bukkit.event.Event.Priority;
import org.bukkit.event.Event.Type;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

import com.iConomy.iConomy;
import com.lala.CouponCodes.Commands.C;
import com.lala.CouponCodes.Configs.Coupon;
import com.lala.CouponCodes.Configs.PluginConfig;
import com.lala.CouponCodes.Logger.log;
import com.nijiko.permissions.PermissionHandler;
import com.nijikokun.bukkit.Permissions.Permissions;

public class CouponCodes extends JavaPlugin{
	/**
	 * @author LaLa
	 * This is CouponCodes v1.5
	 */	
	private final C c = new C(this); // Registers class 'C' for later user
	public static PermissionHandler permissionHandler; // Permissions variable
	public static File data; // Used for datafolder
	public iConomy iConomy = null; // iConomy variable
	public static boolean p; // Used for if iConomy exists or not for PluginConfig
	public void onEnable(){
    	PluginDescriptionFile pdfFile = getDescription();
    	getCommand("coupon").setExecutor(c); // Gets command /coupon and sets it to 'C.class'
    	getCommand("c").setExecutor(c); // Same as above but with /c
    	data = getDataFolder(); // Datafolder for configs
    	setupPermissions(); // Setup permissions
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
	/**
	 * The following method is provided by the permission's wiki/api
	 */
	private void setupPermissions() {
	    if (permissionHandler != null) {
	        return;
	    }	    
	    Plugin permissionsPlugin = this.getServer().getPluginManager().getPlugin("Permissions");	    
	    if (permissionsPlugin == null) {
	        log.severe("Permissions not detected, disabling couponcodes!");
	        Plugin plugin = this.getServer().getPluginManager().getPlugin("CouponCodes");
	        this.getServer().getPluginManager().disablePlugin(plugin);	        
	        return;
	    }
	    permissionHandler = ((Permissions) permissionsPlugin).getHandler();	    
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