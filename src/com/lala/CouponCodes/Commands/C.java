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
		}
		
	}
}