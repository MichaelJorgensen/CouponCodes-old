package com.lala.CouponCodes.Commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.lala.CouponCodes.CouponCodes;
import com.lala.CouponCodes.Configs.Coupon;

public class C implements CommandExecutor {
	@SuppressWarnings("unused")
	private CouponCodes plugin;
	public C(CouponCodes plugin){
		this.plugin = plugin;
	}
	public boolean onCommand(CommandSender sender, Command command, String commandLabel, String[] args) {
		if (!(sender instanceof Player)){
			sender.sendMessage("You need to be a player!");
			return true;
		}else{
			Player player = (Player) sender;
			if (args[0].equalsIgnoreCase("add") && args.length >= 3){
				if (args[1].equalsIgnoreCase("ic") && args.length >=4){
					String code = args[2];
					ic(player, args, code);
					return true;				
				}
				String code = args[1];
				add(player, args, code);
				return true;
			}
		}
		return false;
	}
	private void ic(Player player, String[] args, String code){
		int canbeused = 1;
		try{
			if (args.length >= 5) canbeused = Integer.parseInt(args[4]);
		}catch (Exception e){
			player.sendMessage(ChatColor.RED + args[4] + " is not an integer!");
			return;
		}
		try{
			Coupon.create(code, true, 0, Integer.parseInt(args[3]), canbeused);
			return;
		}catch (Exception e){
			player.sendMessage(ChatColor.RED + "Error on syntax");
			return;
		}
	}
	private void add(Player player, String[] args, String code){
		int canbeused = 1;
		try{
			if (args.length >= 5) canbeused = Integer.parseInt(args[4]);
		}catch (Exception e){
			player.sendMessage(ChatColor.RED + args[4] + " is not an integer!");
			return;
		}
		try{
			Coupon.create(code, false, Integer.parseInt(args[2]), Integer.parseInt(args[3]), canbeused);
			return;
		}catch (Exception e){
			player.sendMessage(ChatColor.RED + "Error on syntax");
			return;
		}
	}
}