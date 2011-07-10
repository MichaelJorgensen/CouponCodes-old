package com.lala.CouponCodes.Configs;

import java.io.File;
import java.util.Map;

import org.bukkit.entity.Player;
import org.bukkit.util.config.Configuration;

import com.lala.CouponCodes.CouponCodes;

public class Config extends Configuration{
	public static int i;
	public static int a;
	public static double m;	
	public static int r;
	public Config(File file){
		super(file);
	}
	public static void makeCoupon(File dataFolder, String code, int id, int amount, int ncbu, boolean ic) {		
		final File yml = new File(dataFolder + "/coupons", "Codes.yml");
		if (!dataFolder.exists()){
			dataFolder.mkdirs();
		}		
		loadCoupon(yml, code, id, amount, ncbu, ic);
	}
	private static void loadCoupon(File yaml, String code, int id, int amount, int ncbu, boolean ic){		
		final Config yml = new Config(yaml);
		if (yaml.exists()){
			yml.load();				
		}	
		yml.setProperty("config.coupons." + code + ".prize.id", id);
		yml.setProperty("config.coupons." + code + ".prize.amount", amount);
		yml.setProperty("config.coupons." + code + ".isiConomy", ic);
		yml.setProperty("config.coupons." + code + ".timescanbeused", ncbu);
		yml.save();		
	}
	public static void renewCoupon(File dataFolder, String code, int newnumber){
		final File yaml = new File(dataFolder + "/coupons", "Codes.yml");
		if (!dataFolder.exists()){
			dataFolder.mkdirs();
		}
		final Config yml = new Config(yaml);
		if (yaml.exists()){
			yml.load();
		}
		yml.setProperty("config.coupons." + code + ".timescanbeused", newnumber);
		yml.removeProperty("config.coupons." + code + ".players");
		yml.save();
	}
	public static void removeCoupon(File dataFolder, String code){
		final File yaml = new File(dataFolder + "/coupons", "Codes.yml");
		if (!dataFolder.exists()){
			dataFolder.mkdirs();
		}
		final Config yml = new Config(yaml);
		if (yaml.exists()){
			yml.load();
		}
		yml.removeProperty("config.coupons." + code);
		yml.save();
	}
	public static void cleanOutCoupons(File dataFolder){
		final File yaml = new File(dataFolder + "/coupons", "Codes.yml");
		if (!dataFolder.exists()){
			dataFolder.mkdirs();
		}
		final Config yml = new Config(yaml);
		if (yaml.exists()){
			yml.load();
		}
		yml.removeProperty("config");
		yml.save();
	}
	public static void getCouponInfo(String code, File dataFolder, Player player){
		String ma = "config.coupons.";
		i = 0;
		a = 0;
		final File yaml = new File(dataFolder + "/coupons", "Codes.yml");
		if (!dataFolder.exists()){
			dataFolder.mkdirs();
		}
		final Config yml = new Config(yaml);
		if (yaml.exists()){
			yml.load();
		}		
		i = (Integer) yml.getProperty(ma + code + ".prize.id");
		a = (Integer) yml.getProperty(ma + code + ".prize.amount");		
		m = yml.getDouble(m + code + ".prize.money", 0);
		r = yml.getInt(m + code + ".timescanbeused", 1);
		return;
	}
	public static boolean checkIfCouponExists(File dataFolder, String code){		
		Object f;
		final File yaml = new File(dataFolder + "/coupons", "Codes.yml");
		if (!dataFolder.exists()){
			dataFolder.mkdirs();
		}
		final Config yml = new Config(yaml);
		if (yaml.exists()){
			yml.load();
		}
		f = yml.getProperty("config.coupons." + code);	
		if (f == null){
			return false;
		}
		return true;
	}
	public static Map<String, Object> getAllCoupons(File dataFolder){
		Map<String, Object> l = null;
		final File yaml = new File(dataFolder + "/coupons", "Codes.yml");
		if (!dataFolder.exists()){
			dataFolder.mkdirs();
		}
		final Config yml = new Config(yaml);
		if (yaml.exists()){
			yml.load();
		}		
		l = yml.getAll();		
		return l;
	}
	public static boolean checkIfCouponIsiConomy(File dataFolder, String code){	
		boolean ic;
		final File yaml = new File(dataFolder + "/coupons" , "Codes.yml");
		if (!dataFolder.exists()){
			dataFolder.mkdirs();
		}
		final Config yml = new Config(yaml);
		if (yaml.exists()){
			yml.load();
		}
		ic = yml.getBoolean("config.coupons." + code + ".isiConomy", false);
		if (ic == true){
			return true;
		}else{
			return false;
		}
	}
	public static boolean isUsed(String code){
		int e = 0;
		File dataFolder = CouponCodes.data;
		final File yaml = new File(dataFolder + "/coupons", "Codes.yml");		
		if (!dataFolder.exists()){
			dataFolder.mkdirs();
		}
		final Config yml = new Config(yaml);
		if (yaml.exists()){
			yml.load();
		}
		e = yml.getInt("config.coupons." + code + ".timescanbeused", 1);
		if (e == 0){
			return true;
		}else{
			return false;
		}
	}
	public static int getTimesCanBeUsed(String code){
		int t = 1;
		File dataFolder = CouponCodes.data;
		final File yaml = new File(dataFolder + "/coupons", "Codes.yml");
		if (!dataFolder.exists()){
			dataFolder.mkdirs();			
		}
		final Config yml = new Config(yaml);
		if (yaml.exists()){
			yml.load();
		}
		t = yml.getInt("config.coupons." + code + ".timescanbeused", 1);
		return t;
	}
	public static void setTimesCanBeUsed(String code, int y){
		File dataFolder = CouponCodes.data;
		final File yaml = new File(dataFolder + "/coupons", "Codes.yml");
		if (!dataFolder.exists()){
			dataFolder.mkdirs();
		}
		final Config yml = new Config(yaml);
		if (yaml.exists()){
			yml.load();
		}
		yml.setProperty("config.coupons." + code + ".timescanbeused", y);
		yml.save();
		return;
	}
	public static void addUsedPlayer(String code, String name){
		File dataFolder = CouponCodes.data;
		final File yaml = new File(dataFolder + "/coupons", "Codes.yml");
		if (!dataFolder.exists()){
			dataFolder.mkdirs();
		}
		final Config yml = new Config(yaml);
		if (yaml.exists()){
			yml.load();
		}
		yml.setProperty("config.coupons." + code + ".players." + name, true);
		yml.save();
	}
	public static boolean hasUserUsedCoupon(String code, String name){
		boolean o;
		File dataFolder = CouponCodes.data;
		final File yaml = new File(dataFolder + "/coupons", "Codes.yml");
		if (!dataFolder.exists()){
			dataFolder.mkdirs();
		}
		final Config yml = new Config(yaml);
		if (yaml.exists()){
			yml.load();
		}
		o = yml.getBoolean("config.coupons." + code + ".players." + name , false);
		if (o == true){
			return true;
		}else{
			return false;
		}
	}
}