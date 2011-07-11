package com.lala.CouponCodes.Configs;

import java.io.File;

import org.bukkit.util.config.Configuration;

import com.lala.CouponCodes.CouponCodes;

public class Coupon extends Configuration{
	public Coupon(File file){
		super(file);
	}
	private Coupon YML(){
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
}
