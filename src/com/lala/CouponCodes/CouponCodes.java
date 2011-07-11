package com.lala.CouponCodes;

import java.io.File;

import org.bukkit.event.Event.Priority;
import org.bukkit.event.Event.Type;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

import com.iConomy.iConomy;
import com.lala.CouponCodes.Commands.C;
import com.lala.CouponCodes.Configs.PluginConfig;
import com.lala.CouponCodes.Logger.log;
import com.nijiko.permissions.PermissionHandler;
import com.nijikokun.bukkit.Permissions.Permissions;

public class CouponCodes extends JavaPlugin{
	/**
	 * @author LaLa
	 * This is CouponCodes v2.0
	 * More like v0.0.0.0.1
	 * (rewriting entire plugin!)
	 */
	private final C c = new C(this);	
	public static PermissionHandler ph;
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
    	log.info("Enabled! <Version: " + pdfFile.getVersion() + ">");
	}
	public void onDisable(){		
		log.info("Disabled!");
	}
	private void setupPermissions() {
	    if (ph != null) {
	        return;
	    }	    
	    Plugin permissionsPlugin = this.getServer().getPluginManager().getPlugin("Permissions");	    
	    if (permissionsPlugin == null) {
	        log.severe("Permissions not detected, defaulting to op!");
	        Plugin plugin = this.getServer().getPluginManager().getPlugin("CouponCodes");
	        this.getServer().getPluginManager().disablePlugin(plugin);	        
	        return;
	    }
	    ph = ((Permissions) permissionsPlugin).getHandler();	    
	}
	private void checkiConomy(){
		Plugin iC = this.getServer().getPluginManager().getPlugin("iConomy");
		if (iC != null){
			p = true;
		}else{
			p = false;
		}
	}
}