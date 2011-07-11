package com.lala.CouponCodes.Commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.lala.CouponCodes.CouponCodes;

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
		//Config stuff needs to be done first
	}
	private void add(Player player, String[] args, String code){
		//Config stuff needs some work first
	}
}