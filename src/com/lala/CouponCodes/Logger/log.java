package com.lala.CouponCodes.Logger;

import java.util.logging.Level;
import java.util.logging.Logger;

import com.lala.CouponCodes.Configs.PluginConfig;

public class log {
	
	/**
	 * This class is used for quick logging
	 */
	
	private static String m = "[CouponCodes] ";
	public static final Logger log = Logger.getLogger("Minecraft");	
	
	public static void info(String message){
		log.log(Level.INFO, m + message);		
	}	
	public static void warning(String message){
		log.log(Level.WARNING, m + message);
	}
	public static void severe(String message){
		log.log(Level.SEVERE, m + message);		
	}
	public static void debug(String message){
		if (PluginConfig.debug == true){
			log.info(Level.INFO + "[CouponCodes-Debug] " + message);
		}
	}
}
