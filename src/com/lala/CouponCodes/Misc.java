package com.lala.CouponCodes;

public class Misc {	
	/**
	 * This string builder is from essentials, credit goes to them for it. I don't think I ever used it though
	 */
	public static String getLastArgs(String[] args, int start){
		StringBuilder build = new StringBuilder();
		for (int f = start; f < args.length; f++){
			if (f != start){
				build.append(" ");
			}
			build.append(args[f]);
		}		
		return build.toString();
	}
}
