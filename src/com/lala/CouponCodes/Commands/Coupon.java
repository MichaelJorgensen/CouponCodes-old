package com.lala.CouponCodes.Commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import com.iConomy.iConomy;
import com.iConomy.system.Account;
import com.lala.CouponCodes.CouponCodes;
import com.lala.CouponCodes.Configs.Config;
import com.lala.CouponCodes.Configs.PluginConfig;
import com.lala.CouponCodes.Logger.log;

public class Coupon implements CommandExecutor {
	@SuppressWarnings("unused")
	private CouponCodes plugin;
	public Coupon(CouponCodes plugin){
		this.plugin = plugin;
	}
	/**
	 * New in 1.5:
	 *  /c reload now works (hopefully - not tested yet!)
	 */
	public boolean onCommand(CommandSender sender, Command command, String commandLabel, String[] args) {
		if (args.length == 0 && sender instanceof Player){			
			Player player = (Player) sender;
			if (CouponCodes.permissionsHandler.has(player, "coupon.help")){
				player.sendMessage(ChatColor.YELLOW + "-----Coupon Code Syntax-----");
				player.sendMessage(ChatColor.GOLD + "/coupon OR /c for all commands");
				player.sendMessage(ChatColor.GOLD + "/c redeem [code]");
				player.sendMessage(ChatColor.GOLD + "/c add [code] [id] [amount] [#canbeused] - See post for more info");
				player.sendMessage(ChatColor.GOLD + "/c add ic [code] [prizemoney] [#canbeused] - See post for more info");
				player.sendMessage(ChatColor.GOLD + "/c remove [code]");
				player.sendMessage(ChatColor.GOLD + "/c removeall - WARNING - will permanently remove all coupons!");
				player.sendMessage(ChatColor.GOLD + "/c renew [code] [#canbeused] - Activates a code again");
				player.sendMessage(ChatColor.GOLD + "/c reload - Reload this plugin and its config easily");
				return true;
			}else{
				try{
					player.sendMessage(ChatColor.valueOf(PluginConfig.nopmsgcolor) + PluginConfig.nopmsg);
					return true;
				}catch (Exception e){
					player.sendMessage(ChatColor.RED + "Someone configured me wrong! Error at: " + ChatColor.GOLD + PluginConfig.nopmsgcolor + " Ex: RED or YELLOW or AQUA. Try: " + ChatColor.GOLD + PluginConfig.nopmsgcolor.toUpperCase());
					log.debug("Config error: " + PluginConfig.nopmsg);
					return true;
				}
			}
		}
		if (!(sender instanceof Player)){
			sender.sendMessage(PluginConfig.notaplayer);
			return true;
		}else{
			Player player = (Player) sender;
			if (args.length >= 4 && args[0].equalsIgnoreCase("add") && args[1].equalsIgnoreCase("ic") && CouponCodes.permissionsHandler.has(player, "coupon.add")){
				try{
				if (PluginConfig.iConomy == false){
					player.sendMessage(ChatColor.RED + "You cannot create an iConomy coupon as iConomy is disabled!");
					return true;
				}
				String code = args[2];
				double pm = Double.parseDouble(args[3]);

				if (Config.checkIfCouponExists(CouponCodes.data, code)){
					player.sendMessage(ChatColor.RED + "That coupon already exists!");
					return true;
				}else{
					if (PluginConfig.icpriceenabled == true){
						int price = PluginConfig.icprice;
						int ncbu = 1;
						if (args.length >= 5) ncbu = Integer.parseInt(args[4]);
						if (ncbu == 0) ncbu = 1;
						Account ac = iConomy.getAccount(player.getName());
						ac.getHoldings().subtract(price);
						Config.makeCoupon(CouponCodes.data, code, 0, (int) pm, ncbu, true);
						player.sendMessage(ChatColor.GREEN + "The iConomy Coupon " + ChatColor.GOLD + code + ChatColor.GREEN + " at the price of " + ChatColor.RED + price + ChatColor.GREEN + " has been created!");
						return true;
					}else{
						int ncbu = 1;
						if (args.length >= 5){
							ncbu = Integer.parseInt(args[4]);
						}
						Config.makeCoupon(CouponCodes.data, code, 0, (int) pm, ncbu, true);					
						player.sendMessage(ChatColor.GREEN + "The iConomy Coupon " + ChatColor.GOLD + code + ChatColor.GREEN + " has been created!");
						return true;
					}
					
				}
				}catch (Exception e){
					player.sendMessage(ChatColor.RED + PluginConfig.syntaxparseissue);
					return true;
				}
			}
			if (args[0].equalsIgnoreCase("add") && args.length >= 3 && CouponCodes.permissionsHandler.has(player, "coupon.add")){
				String code = args[1];
				int amount = 1;				
				if (Config.checkIfCouponExists(CouponCodes.data, code)){
					player.sendMessage(ChatColor.RED + PluginConfig.couponalreadyexists);
					return true;
				}
				int id = Integer.parseInt(args[2]);
				int ncbu = 1;
				if (args.length >= 5) ncbu = Integer.parseInt(args[4]);
				if (args.length >= 4) amount = Integer.parseInt(args[3]);
				if (PluginConfig.icpriceenabled == true){
					int price = PluginConfig.icprice;
					Account ac = iConomy.getAccount(player.getName());
					ac.getHoldings().subtract(price);
					Config.makeCoupon(CouponCodes.data, code, id, amount, ncbu, false);
					player.sendMessage(ChatColor.GREEN + "Made coupon " + ChatColor.GOLD + code + ChatColor.GREEN + " for the price of " + ChatColor.RED + price + ChatColor.GREEN + "!");
					return true;
				}
				Config.makeCoupon(CouponCodes.data, code, id, amount, ncbu, false);
				player.sendMessage(ChatColor.GREEN + "Made coupon " + ChatColor.GOLD + code + ChatColor.GREEN + "!");
				return true;
			}			
			else if (args[0].equalsIgnoreCase("redeem") && args.length >= 2 && CouponCodes.permissionsHandler.has(player, "coupon.redeem")){
				String codes = args[1];
				if (PluginConfig.oneuseonly == true){
					if (Config.hasUserUsedCoupon(codes, player.getName())){
						player.sendMessage(ChatColor.RED + "You cannot use a coupon more than once!");
						return true;
					}
				}
				int newnumber = Config.getTimesCanBeUsed(codes) - 1;
				if (Config.checkIfCouponIsiConomy(CouponCodes.data, codes)){
					if (Config.checkIfCouponExists(CouponCodes.data, codes)){
						Config.getCouponInfo(codes, CouponCodes.data, player);
						//Config.devalidateCoupon(CouponCodes.data, codes, player);
						if (Config.isUsed(codes) == false){
							if (PluginConfig.iConomy == false){
								player.sendMessage(ChatColor.RED + "You cannot redeem an iConomy code as iConomy is currently disabled!");
								return true;
							}
							double pm = Config.a;
							Account ac = iConomy.getAccount(player.getName());
							ac.getHoldings().add(pm);
							Config.setTimesCanBeUsed(codes, newnumber);
							Config.addUsedPlayer(codes, player.getName());
							player.sendMessage(ChatColor.GREEN + "Code redeemed! " + pm + " has been added to your iConomy account!");
							return true;
						}else{
							player.sendMessage(ChatColor.RED + PluginConfig.couponalreadyused);
							return true;
						}
					}else{
						player.sendMessage(ChatColor.RED + PluginConfig.coupondoesntexist);
						return true;
					}
				}
				try {
					String code = args[1];
					int newnum = Config.getTimesCanBeUsed(code) - 1;
					Config.getCouponInfo(code, CouponCodes.data, player);
					Inventory i = player.getInventory();
					ItemStack it = new ItemStack(Config.i, Config.a);
					if (Config.isUsed(code) == false){						
						if (i.firstEmpty() == -1){
							//Config.devalidateCoupon(CouponCodes.data, code, player);							
							player.getWorld().dropItem(player.getLocation(), it);
							Config.setTimesCanBeUsed(code, newnum);
							Config.addUsedPlayer(code, player.getName());
							player.sendMessage(ChatColor.GREEN + "Coupon redeemed! (Item dropped sense your inventory is full!)");
							return true;							
						}
						//Config.devalidateCoupon(CouponCodes.data, code, player);						
						i.addItem(it);
						Config.setTimesCanBeUsed(code, newnum);
						Config.addUsedPlayer(code, player.getName());
						player.sendMessage(ChatColor.GREEN + "Coupon redeemed!");
						return true;
					}else{
						player.sendMessage(ChatColor.RED + PluginConfig.couponalreadyused);
						return true;
					}
				}catch (Exception e){
					player.sendMessage(ChatColor.RED + PluginConfig.coupondoesntexist);
					return true;
				}
			}
			else if (args[0].equalsIgnoreCase("remove") && args.length >= 2 && CouponCodes.permissionsHandler.has(player, "coupon.remove")){
				String code = args[1];
				Config.removeCoupon(CouponCodes.data, code);
				player.sendMessage(ChatColor.GREEN + PluginConfig.couponremoved);
				return true;
			}
			else if (args[0].equalsIgnoreCase("renew") && args.length >= 2 && CouponCodes.permissionsHandler.has(player, "coupon.renew")){
				String code = args[1];
				if (Config.checkIfCouponExists(CouponCodes.data, code) == false){
					player.sendMessage(ChatColor.RED + PluginConfig.coupondoesntexist);
					return true;
				}
				Config.getCouponInfo(code, CouponCodes.data, player);
				if (Config.isUsed(code) == false){
					player.sendMessage(ChatColor.RED + PluginConfig.couponalreadyrenewed);
					return true;
				}
				int newnu = 1;
				if (args.length >= 3) newnu = Integer.parseInt(args[2]);
				Config.renewCoupon(CouponCodes.data, code, newnu);
				player.sendMessage(ChatColor.GREEN + "The coupon " + ChatColor.GREEN + code + ChatColor.GREEN + " has been renewed!");
				return true;
			}
			else if (args[0].equalsIgnoreCase("removeall") && CouponCodes.permissionsHandler.has(player, "coupon.remove.all")){
				Config.cleanOutCoupons(CouponCodes.data);
				player.sendMessage(ChatColor.YELLOW + PluginConfig.couponsremoved);
				return true;			
			}
			else if (args[0].equalsIgnoreCase("reload") && CouponCodes.permissionsHandler.has(player, "coupon.reload")){
				CouponCodes.reload();
				player.sendMessage(ChatColor.AQUA + "Plugin reloaded!");
				return true;
			}else{				
				return false;
			}			
		}
	}	
}