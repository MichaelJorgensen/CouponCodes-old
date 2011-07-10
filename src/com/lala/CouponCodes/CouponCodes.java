package com.lala.CouponCodes;

import java.io.File;

import org.bukkit.event.Event.Priority;
import org.bukkit.event.Event.Type;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

import com.iConomy.iConomy;
import com.lala.CouponCodes.Commands.Coupon;
import com.lala.CouponCodes.Configs.PluginConfig;
import com.lala.CouponCodes.Logger.log;
import com.nijiko.permissions.PermissionHandler;
import com.nijikokun.bukkit.Permissions.Permissions;

public class CouponCodes extends JavaPlugin{
	private final Coupon c = new Coupon(this);	
	public static PermissionHandler permissionsHandler;
	public static File data;
	public iConomy iConomy = null;
	boolean p;
	public void onEnable(){
    	PluginDescriptionFile pdfFile = getDescription();    	
    	getCommand("coupon").setExecutor(c);
    	getCommand("c").setExecutor(c);
    	data = getDataFolder();
    	setupPermissions();
        getServer().getPluginManager().registerEvent(Type.PLUGIN_ENABLE, new server(this), Priority.Monitor, this);
        getServer().getPluginManager().registerEvent(Type.PLUGIN_DISABLE, new server(this), Priority.Monitor, this);
        checkiConomy();
        PluginConfig.loadSettings(data, p);
    	log.info("Enabled! (Version: " + pdfFile.getVersion() + ")");
	}
	public void onDisable(){		
		log.info("Disabled!");
	}
	private void setupPermissions() {
	    if (permissionsHandler != null) {
	        return;
	    }	    
	    Plugin permissionsPlugin = this.getServer().getPluginManager().getPlugin("Permissions");	    
	    if (permissionsPlugin == null) {
	        log.severe("Permissions not detected, expect errors!");	       
	        return;
	    }
	    permissionsHandler = ((Permissions) permissionsPlugin).getHandler();	    
	}
	private void checkiConomy(){
		Plugin iC = this.getServer().getPluginManager().getPlugin("iConomy");
		if (iC != null){
			p = true;
		}else{
			p = false;
		}
	}
	public static void reload(){		
		PluginConfig.reloadSettings(data);
		return;
	}
}