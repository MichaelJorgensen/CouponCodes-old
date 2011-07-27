package com.lala.CouponCodes.Configs;

import java.io.File;

import org.bukkit.util.config.Configuration;

import com.lala.CouponCodes.CouponCodes;

/**
 * This class is for everything to do with Codes.yml, which stores coupon codes
 */

public class Coupon extends Configuration{
	public Coupon(File file){
		super(file);
	}
	/**
	 * Returns the yml file of Codes.yml
	 * @return yml
	 */
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
	/**
	 * Saves all configs
	 */
	public static void saveAll(){
		final Coupon yml = getYML();
		yml.save();
		return;
	}
	/**
	 * Creates a coupon
	 * @param code
	 * @param ic
	 * @param id
	 * @param amount
	 * @param canbeused
	 */
	public static void create(String code, boolean ic, int id, int amount, int canbeused){
		final Coupon yml = getYML();
		yml.setProperty("config.coupons." + code + ".prize.id", id);
		yml.setProperty("config.coupons." + code + ".prize.amount", amount);
		yml.setProperty("config.coupons." + code + ".isiConomy", ic);
		yml.setProperty("config.coupons." + code + ".timescanbeused", canbeused);
		yml.save();
		return;
	}
	/**
	 * Redeems a coupon and performs necessary actions to consider it redeemed
	 * @param code
	 * @param name
	 */
	public static void redeem(String code, String name){		
		int newnumber = Coupon.getTimesCanBeUsed(code) - 1;
		setTimesCanBeUsed(code, newnumber);
		addUsedPlayer(code, name);
		return;
	}
	/**
	 * Renews a coupon and performs necessary actions to consider is renewed
	 * @param code
	 * @param newnumber
	 */
	public static void renew(String code, int newnumber){
		final Coupon yml = getYML();
		yml.removeProperty("config.coupons." + code + ".players");
		yml.setProperty("config.coupons." + code + ".timescanbeused", newnumber);
		yml.save();
		return;
	}
	/**
	 * Removes the given coupon
	 * @param code
	 */
	public static void remove(String code){
		final Coupon yml = getYML();
		yml.removeProperty("config.coupons." + code);
		yml.save();
		return;
	}
	/**
	 * Removes all coupons
	 */
	public static void removeAll(){
		final Coupon yml = getYML();
		yml.removeProperty("config");
		yml.save();
		return;
	}
	/**
	 * Returns the ID value of the given code
	 * @param code
	 * @return int ID
	 */
	public static int getId(String code){
		final Coupon yml = getYML();
		return yml.getInt("config.coupons." + code + ".prize.id", 0);
	}
	/**
	 * Returns the amount of the prize of the given code
	 * @param code
	 * @return int Amount
	 */
	public static int getAmount(String code){
		final Coupon yml = getYML();
		return yml.getInt("config.coupons." + code + ".prize.amount", 0);
	}
	/**
	 * Returns the amount of times the given coupon can be used
	 * @param code
	 * @return int timesCanBeUsed
	 */
	public static int getTimesCanBeUsed(String code){
		final Coupon yml = getYML();
		return yml.getInt("config.coupons." + code + ".timescanbeused", 0);
	}
	/**
	 * Returns if the coupon is considered an iConomy coupon
	 * @param code
	 * @return boolean isiConomy
	 */
	public static boolean isiConomy(String code){
		final Coupon yml = getYML();
		return yml.getBoolean("config.coupons." + code + ".isiConomy", false);
	}
	/**
	 * Returns if the given coupon is completely used
	 * @param code
	 * @return boolean true
	 * @return boolean false
	 */
	public static boolean isUsed(String code){
		final Coupon yml = getYML();
		if (yml.getInt("config.coupons." + code + ".timescanbeused", 0) <= 0){
			return true;
		}else{
			return false;
		}
	}
	/**
	 * Returns if the given player has used the given coupon before
	 * @param code
	 * @param name
	 * @return boolean true
	 * @return boolean false
	 */
	public static boolean hasPlayerUsedCoupon(String code, String name){
		final Coupon yml = getYML();
		if (!PluginConfig.oneuseonly){
			return false;
		}
		else if (yml.getProperty("config.coupons." + code + ".players." + name) == null){
			return false;
		}else{
			return true;
		}
	}
	/**
	 * Returns if the given coupon exists
	 * @param code
	 * @return boolean true
	 * @return boolean false
	 */
	public static boolean exists(String code){
		final Coupon yml = getYML();
		if (yml.getProperty("config.coupons." + code) == null){
			return false;
		}else{
			return true;
		}
	}
	/**
	 * Sets the amount of times the given coupon can be used
	 * @param code
	 * @param newnumber
	 */
	public static void setTimesCanBeUsed(String code, int newnumber){
		final Coupon yml = getYML();
		yml.setProperty("config.coupons." + code + ".timescanbeused", newnumber);
		yml.save();
		return;
	}
	/**
	 * Adds the given player to the used list of the given coupon
	 * @param code
	 * @param name
	 */
	public static void addUsedPlayer(String code, String name){
		final Coupon yml = getYML();		
		yml.setProperty("config.coupons." + code + ".players." + name, true);
		yml.save();
		return;
	}
}
