package com.lala.CouponCodes.Configs;

import java.io.File;

import org.bukkit.util.config.Configuration;

import com.lala.CouponCodes.CouponCodes;

public class Coupon extends Configuration{
	public Coupon(File file){
		super(file);
	}
	private static Coupon getYML(){
		File d = CouponCodes.data;
		final File yaml = new File(d + "/coupons", "Codes.yml");
		if (!d.exists()){
			d.mkdirs();
		}
		final Coupon yml = new Coupon(yaml);
		if (yaml.exists()){
			yml.load();
		}
		return yml;
	}
	public static void saveAll(){
		final Coupon yml = getYML();
		yml.save();
		return;
	}
	public static void create(String code, boolean ic, int id, int amount, int canbeused){
		final Coupon yml = getYML();
		yml.setProperty("config.coupons." + code + ".prize.id", id);
		yml.setProperty("config.coupons." + code + ".prize.amount", amount);
		yml.setProperty("config.coupons." + code + ".isiConomy", ic);
		yml.setProperty("config.coupons." + code + ".timescanbeused", canbeused);
		yml.save();
		return;
	}
	public static void redeem(String code, String name){		
		int newnumber = Coupon.getTimesCanBeUsed(code) - 1;
		Coupon.setTimesCanBeUsed(code, newnumber);
		Coupon.addUsedPlayer(code, name);
		return;
	}
	public static void renew(String code, int newnumber){
		final Coupon yml = getYML();
		yml.removeProperty("config.coupons." + code + ".players");
		yml.setProperty("config.coupons." + code + ".timescanbeused", newnumber);
		yml.save();
		return;
	}
	public static void remove(String code){
		final Coupon yml = getYML();
		yml.removeProperty("config.coupons." + code);
		yml.save();
		return;
	}
	public static void removeAll(){
		final Coupon yml = getYML();
		yml.removeProperty("config");
		yml.save();
		return;
	}
	public static int getId(String code){
		final Coupon yml = getYML();
		int i;
		i = (Integer) yml.getProperty("config.coupons." + code + ".prize.id");
		return i;
	}
	public static int getAmount(String code){
		final Coupon yml = getYML();
		int a;
		a = (Integer) yml.getProperty("config.coupons." + code + ".prize.amount");
		return a;
	}
	public static int getTimesCanBeUsed(String code){
		final Coupon yml = getYML();
		int t;
		t = (Integer) yml.getProperty("config.coupons." + code + ".timescanbeused");
		return t;
	}
	public static boolean isiConomy(String code){
		final Coupon yml = getYML();
		boolean m;
		m = (Boolean) yml.getProperty("config.coupons." + code + ".isiConomy");
		return m;
	}
	public static boolean isUsed(String code){
		final Coupon yml = getYML();
		int u;
		u = (Integer) yml.getProperty("config.coupons." + code + ".timescanbeused");
		if (u == 0){
			return true;
		}else{
			return false;
		}
	}
	public static boolean hasPlayerUsedCoupon(String code, String name){
		final Coupon yml = getYML();
		if (PluginConfig.oneuseonly == false){
			return false;
		}
		Object o;
		o = yml.getProperty("config.coupons." + code + ".players." + name);
		if (o == null){
			return false;
		}else{
			return true;
		}
	}
	public static boolean exists(String code){
		final Coupon yml = getYML();
		Object z;
		z = yml.getProperty("config.coupons." + code);
		if (z == null){
			return false;
		}else{
			return true;
		}
	}
	public static void setTimesCanBeUsed(String code, int newnumber){
		final Coupon yml = getYML();
		yml.setProperty("config.coupons." + code + ".timescanbeused", newnumber);
		yml.save();
		return;
	}
	public static void addUsedPlayer(String code, String name){
		final Coupon yml = getYML();		
		yml.setProperty("config.coupons." + code + ".players." + name, true);
		yml.save();
		return;
	}
}
