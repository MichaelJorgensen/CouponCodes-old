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
			if (args[0].equalsIgnoreCase("add") && args.length >= 3){
				if (args[1].equalsIgnoreCase("ic")){
					String code = args[2];
					ic((Player) sender, args, code);
					return true;
				}else{
					// /c add
				}
			}
		}
		return false;
	}
	private void ic(Player player, String[] args, String code){
		
	}
}