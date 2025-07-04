package com.lerdorf.enchantiotrades;

import org.bukkit.enchantments.Enchantment;

public class EnchantData {

	public int maxLevel = 1;
	public String name = "enchant";
	public boolean doublePrice = false;
	public Enchantment enchant = null;
	
	public EnchantData(Enchantment enchant, String name, int maxLevel, boolean doublePrice) {
		this.enchant = enchant;
		this.maxLevel = maxLevel;
		this.name = name;
		this.doublePrice = doublePrice;
	}
	
}
