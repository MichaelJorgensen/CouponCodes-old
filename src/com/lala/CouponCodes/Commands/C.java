package com.lala.CouponCodes.Commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.lala.CouponCodes.CouponCodes;
import com.lala.CouponCodes.Configs.Coupon;
import com.lala.CouponCodes.Configs.PluginConfig;
import com.lala.CouponCodes.Logger.log;

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
			return false;
		}
		Player player = (Player) sender;
		if (args[0].equalsIgnoreCase("add") && args.length >= 3 && CouponCodes.permissionHandler.has(player, "coupon.add")){
			if (args[1].equalsIgnoreCase("ic") && args.length >= 4){
				String code = args[2];
				if (Coupon.exists(code)){
					player.sendMessage(ChatColor.RED + "That coupon already exists!");
					return true;
				}else{
					if (args.length < 4) return false;
					try{
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
		else if (args[0].equalsIgnoreCase("redeem") && args.length >= 2){
			log.debug(command.getName() +  " " + args);
			return true;
		}
		else if (args[0].equalsIgnoreCase("remove") && args.length >= 2){
			log.debug(command.getName() +  " " + args);
			return true;
		}
		else if (args[0].equalsIgnoreCase("removeall")){
			log.debug(command.getName() +  " " + args);
			return true;
		}
		else if (args[0].equalsIgnoreCase("renew") && args.length >= 2){
			log.debug(command.getName() +  " " + args);
			return true;
		}
		else if (args[0].equalsIgnoreCase("reload")){
			PluginConfig.reloadSettings(CouponCodes.data);
			player.sendMessage(ChatColor.LIGHT_PURPLE + "Plugin reloaded!");			
			return true;
		}
		return false;
	}
}