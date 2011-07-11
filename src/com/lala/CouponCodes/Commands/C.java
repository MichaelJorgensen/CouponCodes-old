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
			if (args[0].equalsIgnoreCase("add") && args.length >= 3 && CouponCodes.ph.has(player, "coupon.add")){
				if (args[1].equalsIgnoreCase("ic") && args.length >=4){
					String code = args[2];
					if (Coupon.exists(code)){
						player.sendMessage(ChatColor.RED + "That coupon already exists!");
						return true;
					}else{
						ic(player, args, code);
						return true;
					}
				}				
				String code = args[1];
				if (Coupon.exists(code)){
					player.sendMessage(ChatColor.RED + "That coupon already exists!");
					return true;
				}else{
					add(player, args, code);
					return true;
				}
			}
			else if (args[0].equalsIgnoreCase("redeem") && args.length >= 2 && CouponCodes.ph.has(player, "coupon.redeem") || CouponCodes.ph.has(player, "coupon." + args[1])){
				String code = args[1];
				if (Coupon.hasPlayerUsedCoupon(code, player.getName())){
					player.sendMessage(ChatColor.RED + "You have already used this coupon!");
					return true;
				}
				else if (Coupon.getTimesCanBeUsed(code) == 0){
					player.sendMessage(ChatColor.RED + "That coupon has already been used up!");
					return true;
				}else{
					if (Coupon.isiConomy(code)){
						Coupon.redeem(code, player.getName());
						Account ac = iConomy.getAccount(player.getName());
						ac.getHoldings().add((double) Coupon.getAmount(code));
						player.sendMessage(ChatColor.GREEN + "Coupon redeemed! " + Coupon.getAmount(code) + " has been added to your account.");
						return true;
					}else{						
						Coupon.redeem(code, player.getName());
						Inventory i = player.getInventory();
						ItemStack s = new ItemStack(Coupon.getId(code), Coupon.getAmount(code));
						if (i.firstEmpty() == -1){
							player.getWorld().dropItem(player.getLocation(), s);
							player.sendMessage(ChatColor.GREEN + "Code redeemed! (Item dropped sense your inventory is full!");
							return true;
						}
						i.addItem(s);
						player.sendMessage(ChatColor.GREEN + "Code redeemed!");
						return true;
					}
				}
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