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
import com.lala.CouponCodes.Configs.Coupon;
import com.lala.CouponCodes.Configs.PluginConfig;

public class C implements CommandExecutor {
	@SuppressWarnings("unused")
	private CouponCodes plugin;
	public C(CouponCodes plugin){
		this.plugin = plugin;
	}
	@Override
	public boolean onCommand(CommandSender sender, Command command, String commandLabel, String[] args) {
		if (!(sender instanceof Player)){
			sender.sendMessage("You need to be a player!");
			return true;
		}
		if (args.length == 0){
			help((Player) sender);
			return true;
		}
		Player player = (Player) sender;
		if (args[0].equalsIgnoreCase("add") && args.length >= 3 && player.hasPermission("coupon.add")){
			if (args[1].equalsIgnoreCase("ic") && args.length >= 4){
				String code = args[2];
				if (PluginConfig.iConomy == false){
					player.sendMessage(ChatColor.RED + "iConomy is disabled, you cannot create an iConomy code!");
					return true;
				}
				if (Coupon.exists(code)){
					player.sendMessage(ChatColor.RED + "That coupon already exists!");
					return true;
				}else{
					if (args.length < 4) return false;
					try{
						if (PluginConfig.icpriceenabled == true){
							Account ac = iConomy.getAccount(player.getName());
							double p = PluginConfig.icprice;
							ac.getHoldings().subtract(p);
							player.sendMessage(ChatColor.RED + "Price to make coupon: " + p);
						}
						int canbeused = 1;
						if (args.length >= 5) canbeused = Integer.parseInt(args[4]);
						Coupon.create(code, true, 0, Integer.parseInt(args[3]), canbeused);
						player.sendMessage(ChatColor.GREEN + "Coupon Code " + code + " created!");
						return true;
					}catch (NumberFormatException e){
						player.sendMessage(ChatColor.RED + "Syntax error, wanted integer but got a string!");
						return true;
					}catch (Exception e){
						player.sendMessage(ChatColor.RED + "Error making your coupon. Check console for more info.");
						e.printStackTrace();
						return true;
					}
				}
			}
			String code = args[1];
			if (Coupon.exists(code)){
				player.sendMessage(ChatColor.RED + "That coupon already exists!");
				return true;
			}else{
				try{
					if (PluginConfig.icpriceenabled == true){
						Account ac = iConomy.getAccount(player.getName());
						double p = PluginConfig.icprice;
						ac.getHoldings().subtract(p);
						player.sendMessage(ChatColor.RED + "Price to make coupon: " + p);
					}
					int canbeused = 1;
					int amount = 1;
					int id;
					if (args.length >= 5) canbeused = Integer.parseInt(args[4]);
					if (args.length >= 4) amount = Integer.parseInt(args[3]);
					id = Integer.parseInt(args[2]);
					Coupon.create(code, false, id, amount, canbeused);
					player.sendMessage(ChatColor.GREEN + "Coupon Code " + code + " created!");
					return true;
				}catch (NumberFormatException e){
					player.sendMessage(ChatColor.RED + "Syntax error, wanted integer but got a string!");
					return true;
				}catch (Exception e){
					player.sendMessage(ChatColor.RED + "Error making your coupon. Check console for more info.");
					e.printStackTrace();
					return true;
				}
			}
		}
		else if (args[0].equalsIgnoreCase("redeem") && args.length >= 2 && player.hasPermission("coupon.redeem") || args[0].equalsIgnoreCase("redeem") && player.hasPermission("coupon." + args[1])){
			String code = args[1];
			if (Coupon.exists(code)){
				if (Coupon.isUsed(code) == false){
					if (Coupon.hasPlayerUsedCoupon(code, player.getName()) == false){
						if (Coupon.isiConomy(code)){
							if (PluginConfig.iConomy == false){
								player.sendMessage(ChatColor.RED + "iConomy is disabled, you cannot create an iConomy code!");
								return true;
							}else{
								Coupon.redeem(code, player.getName());
								Account ac = iConomy.getAccount(player.getName());
								double money = Coupon.getAmount(code);
								ac.getHoldings().add(money);
								player.sendMessage(ChatColor.GREEN + "Coupon redeemed! You received " + Coupon.getAmount(code) + " money!");
								return true;
							}
						}else{							
							int id = Coupon.getId(code);
							int amount = Coupon.getAmount(code);
							Inventory i = player.getInventory();
							ItemStack prize = new ItemStack(id, amount);
							if (i.firstEmpty() == -1){
								Coupon.redeem(code, player.getName());
								player.getWorld().dropItem(player.getLocation(), prize);
								player.sendMessage(ChatColor.GREEN + "Coupon redeemed! (Item dropped because your inventory is full!)");
								return true;
							}else{
								Coupon.redeem(code, player.getName());
								i.addItem(prize);
								player.sendMessage(ChatColor.GREEN + "Coupon redeemed!");
								return true;
							}
						}
					}else{
						player.sendMessage(ChatColor.RED + "You have already used this coupon!");
						return true;
					}
				}else{
					player.sendMessage(ChatColor.RED + "That coupon has already been used!");
					return true;
				}
			}else{
				player.sendMessage(ChatColor.RED + "That coupon doesn't exist!");
				return true;
			}
		}
		else if (args[0].equalsIgnoreCase("remove") && args.length >= 2 && player.hasPermission("coupon.remove")){
			String code = args[1];
			if (Coupon.exists(code)){
				Coupon.remove(code);
				player.sendMessage(ChatColor.GREEN + "Coupon " + code + " removed!");
				return true;
			}else{
				player.sendMessage(ChatColor.RED + "That coupon doesn't exist!");
				return true;
			}
		}
		else if (args[0].equalsIgnoreCase("removeall") && player.hasPermission("coupon.remove.all")){
			Coupon.removeAll();
			player.sendMessage(ChatColor.GOLD + "Removed all coupons");
			return true;
		}
		else if (args[0].equalsIgnoreCase("renew") && args.length >= 2 && player.hasPermission("coupon.renew")){
			String code = args[1];
			if (Coupon.exists(code)){
				if (Coupon.isUsed(code)){
					int newnumber = 1;
					try{
					if (args.length >= 3) newnumber = Integer.parseInt(args[2]);
					}catch (NumberFormatException e){
						player.sendMessage(ChatColor.RED + "Syntax error, wanted integer but got a string!");
						return true;
					}
					Coupon.renew(code, newnumber);
					player.sendMessage(ChatColor.GREEN + "Coupon renewed!");
					return true;
				}else{
					player.sendMessage(ChatColor.RED + "That code is already renewed!");
					return true;
				}
			}else{
				player.sendMessage(ChatColor.RED + "That coupon doesn't exist!");
				return true;
			}			
		}
		else if (args[0].equalsIgnoreCase("reload")){
			PluginConfig.loadSettings(CouponCodes.data, CouponCodes.p);
			player.sendMessage(ChatColor.LIGHT_PURPLE + "Plugin reloaded!");			
			return true;
		}
		help(player);
		return true;
	}
	private void help (Player player){
		player.sendMessage(ChatColor.YELLOW + "-----Coupon Code Syntax-----");
		player.sendMessage(ChatColor.GOLD + "/coupon OR /c for all commands");
		player.sendMessage(ChatColor.GOLD + "/c redeem [code]");
		player.sendMessage(ChatColor.GOLD + "/c add [code] [id] [amount] [#canbeused] - See post for more info");
		player.sendMessage(ChatColor.GOLD + "/c add ic [code] [prizemoney] [#canbeused] - See post for more info");
		player.sendMessage(ChatColor.GOLD + "/c remove [code]");
		player.sendMessage(ChatColor.GOLD + "/c removeall - WARNING - will permanently remove all coupons!");
		player.sendMessage(ChatColor.GOLD + "/c renew [code] [#canbeused] - Activates a code again");
		player.sendMessage(ChatColor.GOLD + "/c reload - Reload this plugin and its config easily");
		return;
	}
}