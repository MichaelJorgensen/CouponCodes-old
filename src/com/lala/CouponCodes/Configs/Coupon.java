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
	public static void create(String code, boolean ic, int id, int amount, int canbeused){
		final Coupon yml = getYML();
		yml.setProperty("config.coupons." + code + ".prize.id", id);
		yml.setProperty("config.coupons." + code + ".prize.amount", amount);
		yml.setProperty("config.coupons." + code + ".isiConomy", ic);
		yml.setProperty("config.coupons." + code + ".timescanbeused", canbeused);
		yml.save();
		return;
	}
}
