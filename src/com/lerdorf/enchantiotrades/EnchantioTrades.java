package com.lerdorf.enchantiotrades;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Registry;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.VillagerAcquireTradeEvent;
import org.bukkit.event.entity.VillagerCareerChangeEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.MerchantRecipe;
import org.bukkit.inventory.MerchantInventory;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;
import me.youhavetrouble.enchantio.enchants.*;

public class EnchantioTrades extends JavaPlugin implements Listener {
    
    private NamespacedKey tradedKey;
    public static boolean fletcherCompatibility = false;
    
    EnchantData[] enchants = {
    	new EnchantData(Registry.ENCHANTMENT.get(NamespacedKey.fromString("enchantio:airbag")), "Airbag", 4	, false), // elytra
    	new EnchantData(Registry.ENCHANTMENT.get(NamespacedKey.fromString("enchantio:beheading")), "Beheading", 1, true), // axe
    	new EnchantData(Registry.ENCHANTMENT.get(NamespacedKey.fromString("enchantio:cloaking")), "Cloaking", 1, true), // leggings
    	new EnchantData(Registry.ENCHANTMENT.get(NamespacedKey.fromString("enchantio:executioner")), "Executioner", 5, false), // weapon
    	new EnchantData(Registry.ENCHANTMENT.get(NamespacedKey.fromString("enchantio:homecoming")), "Homecoming", 1, true), // totem
    	new EnchantData(Registry.ENCHANTMENT.get(NamespacedKey.fromString("enchantio:insomnia_curse")), "Curse of Insomnia", 1, false), // armor
    	new EnchantData(Registry.ENCHANTMENT.get(NamespacedKey.fromString("enchantio:panic_curse")), "Curse of Panic", 1, false), // armor
    	new EnchantData(Registry.ENCHANTMENT.get(NamespacedKey.fromString("enchantio:replanting")), "Replanting", 1, true), // hoe
    	new EnchantData(Registry.ENCHANTMENT.get(NamespacedKey.fromString("enchantio:smelting")), "Smelting", 1, true), // mining
    	new EnchantData(Registry.ENCHANTMENT.get(NamespacedKey.fromString("enchantio:soulbound")), "Soulbound", 1, true), // armor, weapon, mining
    	new EnchantData(Registry.ENCHANTMENT.get(NamespacedKey.fromString("enchantio:telepathy")), "Telepathy", 1, true), // mining
    	new EnchantData(Registry.ENCHANTMENT.get(NamespacedKey.fromString("enchantio:vampirism_curse")), "Curse of Vampirism", 1, false), // armor
    	new EnchantData(Registry.ENCHANTMENT.get(NamespacedKey.fromString("enchantio:volley")), "Volley", 3, true), // bow
    	new EnchantData(Registry.ENCHANTMENT.get(NamespacedKey.fromString("enchantio:ward")), "Ward", 1, true), // shield
    };
    
    EnchantData[] curses = {
    		new EnchantData(Registry.ENCHANTMENT.get(NamespacedKey.fromString("enchantio:insomnia_curse")), "Curse of Insomnia", 1, false), // armor
        	new EnchantData(Registry.ENCHANTMENT.get(NamespacedKey.fromString("enchantio:panic_curse")), "Curse of Panic", 1, false), // armor
        	new EnchantData(Registry.ENCHANTMENT.get(NamespacedKey.fromString("enchantio:vampirism_curse")), "Curse of Vampirism", 1, false), // armor
        	new EnchantData(Enchantment.BINDING_CURSE, "Curse of Binding", 1, false),
        	new EnchantData(Enchantment.VANISHING_CURSE, "Curse of Vanishing", 1, false)
    };
    
    EnchantData[] mining = {
    		new EnchantData(Registry.ENCHANTMENT.get(NamespacedKey.fromString("enchantio:smelting")), "Smelting", 1, true), // mining
    		new EnchantData(Registry.ENCHANTMENT.get(NamespacedKey.fromString("enchantio:soulbound")), "Soulbound", 1, true), // armor, weapon, mining
        	new EnchantData(Registry.ENCHANTMENT.get(NamespacedKey.fromString("enchantio:telepathy")), "Telepathy", 1, true), // mining
    };
    
    EnchantData[] armor = {
    		new EnchantData(Registry.ENCHANTMENT.get(NamespacedKey.fromString("enchantio:soulbound")), "Soulbound", 1, true), // armor, weapon, mining
        	new EnchantData(Registry.ENCHANTMENT.get(NamespacedKey.fromString("enchantio:insomnia_curse")), "Curse of Insomnia", 1, false), // armor
        	new EnchantData(Registry.ENCHANTMENT.get(NamespacedKey.fromString("enchantio:panic_curse")), "Curse of Panic", 1, false), // armor
        	new EnchantData(Registry.ENCHANTMENT.get(NamespacedKey.fromString("enchantio:vampirism_curse")), "Curse of Vampirism", 1, false), // armor
    };
    
    EnchantData[] leggings = {
        	new EnchantData(Registry.ENCHANTMENT.get(NamespacedKey.fromString("enchantio:cloaking")), "Cloaking", 1, true), // leggings
    		new EnchantData(Registry.ENCHANTMENT.get(NamespacedKey.fromString("enchantio:soulbound")), "Soulbound", 1, true), // armor, weapon, mining
        	new EnchantData(Registry.ENCHANTMENT.get(NamespacedKey.fromString("enchantio:insomnia_curse")), "Curse of Insomnia", 1, false), // armor
        	new EnchantData(Registry.ENCHANTMENT.get(NamespacedKey.fromString("enchantio:panic_curse")), "Curse of Panic", 1, false), // armor
        	new EnchantData(Registry.ENCHANTMENT.get(NamespacedKey.fromString("enchantio:vampirism_curse")), "Curse of Vampirism", 1, false), // armor
    };
    
    EnchantData[] axe = {
    		new EnchantData(Registry.ENCHANTMENT.get(NamespacedKey.fromString("enchantio:beheading")), "Beheading", 1, true), // axe
        	new EnchantData(Registry.ENCHANTMENT.get(NamespacedKey.fromString("enchantio:executioner")), "Executioner", 5, false), // weapon
    		new EnchantData(Registry.ENCHANTMENT.get(NamespacedKey.fromString("enchantio:soulbound")), "Soulbound", 1, true), // armor, weapon, mining
    };
    
    EnchantData[] weapon = {
        	new EnchantData(Registry.ENCHANTMENT.get(NamespacedKey.fromString("enchantio:executioner")), "Executioner", 5, false), // weapon
    		new EnchantData(Registry.ENCHANTMENT.get(NamespacedKey.fromString("enchantio:soulbound")), "Soulbound", 1, true), // armor, weapon, mining
    };
    
    EnchantData[] bow = {
    		new EnchantData(Registry.ENCHANTMENT.get(NamespacedKey.fromString("enchantio:volley")), "Volley", 3, true), // bow
    };

    EnchantData[] hoe = {
    		new EnchantData(Registry.ENCHANTMENT.get(NamespacedKey.fromString("enchantio:replanting")), "Replanting", 1, true), // hoe
    };
	
    @Override
    public void onEnable() {
        // Initialize the NamespacedKey for tracking traded villagers
        tradedKey = new NamespacedKey(this, "has_traded");
        
        // Check if the Fletcher plugin is present
        fletcherCompatibility = getServer().getPluginManager().getPlugin("Fletcher") != null;
        
    	getServer().getPluginManager().registerEvents(this, this);
    	
    	if (fletcherCompatibility)
    		getServer().getPluginManager().registerEvents(new VolleyListener(), this);
    	
        getLogger().info("EnchantioTrades plugin enabled!");
    }
    
    @Override
    public void onDisable() {
        getLogger().info("EnchantioTrades plugin disabled!");
    }
    
    float weighting = 0.318182f;
    float curseWeighting = 0.1f;
    
    /**
     * Checks if a villager has been traded with
     */
    private boolean hasTraded(Villager villager) {
        return villager.getPersistentDataContainer().has(tradedKey, PersistentDataType.BYTE);
    }
    
    /**
     * Marks a villager as having been traded with
     */
    private void markAsTraded(Villager villager) {
        villager.getPersistentDataContainer().set(tradedKey, PersistentDataType.BYTE, (byte) 1);
    }
    
    /**
     * Handles when a player trades with a villager
     */
    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (event.getInventory() instanceof MerchantInventory) {
            MerchantInventory merchantInventory = (MerchantInventory) event.getInventory();
            
            // Check if the merchant is a villager
            if (merchantInventory.getHolder() instanceof Villager) {
                Villager villager = (Villager) merchantInventory.getHolder();
                
                // Check if this is a trade completion (clicking on the result slot)
                if (event.getSlot() == 2 && event.getCurrentItem() != null) {
                    // Mark the villager as having been traded with
                    markAsTraded(villager);
                    
                    // Debug message (can be removed in production)
                    getLogger().info("Villager at " + villager.getLocation() + " has been marked as traded");
                }
            }
        }
    }
    
    /**
     * Prevents villagers from changing careers if they have been traded with
     */
    @EventHandler
    public void onVillagerCareerChange(VillagerCareerChangeEvent event) {
        if (event.getEntity() instanceof Villager) {
            Villager villager = (Villager) event.getEntity();
            
            // If the villager has been traded with, prevent career change
            if (hasTraded(villager)) {
                event.setCancelled(true);
                
                // Debug message (can be removed in production)
                getLogger().info("Prevented career change for traded villager at " + villager.getLocation());
            }
        }
    }
    
    @EventHandler
    public void onVillagerTradeSetup(VillagerAcquireTradeEvent event) {
    	if (event.getEntity() instanceof Villager villager) {
	        if (villager.getProfession() == Villager.Profession.LIBRARIAN && event.getRecipe().getResult().getType() == Material.ENCHANTED_BOOK) {
	            if (Math.random() < weighting) {
		        	// Create a custom enchanted book
		            ItemStack enchantedBook = new ItemStack(Material.ENCHANTED_BOOK);
		            EnchantmentStorageMeta meta = (EnchantmentStorageMeta) enchantedBook.getItemMeta();
		
		            EnchantData edata = enchants[(int)(Math.random()*enchants.length)];
		            int level = (int)(Math.random()*edata.maxLevel)+1;
		            int price = 5;
		            switch (level) {
			            case 1:
			            	price = (int)(5+Math.random()*15);
			            	break;
			            case 2:
			            	price = (int)(8+Math.random()*25);
			            	break;
			            case 3:
			            	price = (int)(11+Math.random()*35);
			            	break;
			            case 4:
			            	price = (int)(14+Math.random()*45);
			            	break;
			            case 5:
			            	price = (int)(17+Math.random()*54);
			            	break;
			            case 6:
			            case 7:
			            case 8:
			            case 9:
			            case 10:
			            	price = 64;
		            }
		            
		            if (edata.doublePrice)
		            	price *= 2;
		            price = Math.min(price, 64);
		            
		            // Add Enchantio custom enchant
		            meta.addStoredEnchant(edata.enchant, level, true);
		            enchantedBook.setItemMeta(meta);
		
		            // Create the trade - this is fine since we're replacing the entire trade
		            MerchantRecipe recipe = new MerchantRecipe(enchantedBook, (int)(3 + Math.random()*3)); // 3-5 max uses
		            recipe.addIngredient(new ItemStack(Material.EMERALD, price));
		            recipe.addIngredient(new ItemStack(Material.BOOK));
		            event.setRecipe(recipe);
		        }
	            if (Math.random() < curseWeighting) {
	            	// For curse books, modify the existing result item instead of creating new recipe
	            	MerchantRecipe recipe = event.getRecipe();
	            	ItemStack enchantedBook = recipe.getResult().clone();
	            	
	            	EnchantmentStorageMeta meta = (EnchantmentStorageMeta) enchantedBook.getItemMeta();
	            	EnchantData edata = curses[(int)(Math.random()*curses.length)];
	            	int level = (int)(Math.random()*edata.maxLevel)+1;
	            	meta.addStoredEnchant(edata.enchant, level, true);
	            	enchantedBook.setItemMeta(meta);
	            	
	            	// Create new recipe with curse book
	            	MerchantRecipe newRecipe = new MerchantRecipe(enchantedBook, recipe.getMaxUses());
	    	        for (ItemStack ingredient : recipe.getIngredients()) {
	    	        	newRecipe.addIngredient(ingredient);
	    	        }
	    	        
	    	        newRecipe.setExperienceReward(recipe.hasExperienceReward());
	    	        newRecipe.setVillagerExperience(recipe.getVillagerExperience());
	    	        newRecipe.setSpecialPrice(recipe.getSpecialPrice());
	    	        newRecipe.setPriceMultiplier(recipe.getPriceMultiplier());
	    	        newRecipe.setDemand(recipe.getDemand());
	    	        
	    	        event.setRecipe(newRecipe);
	            }
	        }
    	} else if (event.getRecipe().getResult().getEnchantments().size() > 0 && Math.random() < weighting) {
    		// For enchanted tools/weapons/armor, modify the existing item instead of creating new recipe
    		MerchantRecipe recipe = event.getRecipe();
        	ItemStack item = recipe.getResult().clone();
        	
        	ItemMeta meta = item.getItemMeta();
        	
        	EnchantData edata = null;
        	
        	// Fixed the mining tools check - was using "hoe" array for pickaxes
        	if (item.getType().toString().contains("pickax") || item.getType().toString().contains("shovel")) {
        		edata = mining[(int)(Math.random()*mining.length)];
        		
        	} else if (item.getType().toString().contains("axe")) {
        		edata = axe[(int)(Math.random()*axe.length)];
        		
        	} else if (item.getType().toString().contains("sword")) {
        		edata = weapon[(int)(Math.random()*weapon.length)];
        		
        	} else if (item.getType().toString().contains("leggings")) {
        		edata = leggings[(int)(Math.random()*leggings.length)];
        		
        	} else if (item.getType().toString().contains("chestplate") || item.getType().toString().contains("helmet") || item.getType().toString().contains("boots")) {
        		edata = armor[(int)(Math.random()*armor.length)];
        		
        	} else if (item.getType() == Material.BOW) {
        		edata = bow[(int)(Math.random()*bow.length)];
        		
        	} else if (item.getType().toString().contains("hoe")) {
        		edata = hoe[(int)(Math.random()*hoe.length)];
        	}
        	
        	if (edata != null) {
        		int level = (int)(Math.random()*edata.maxLevel)+1;
	        	meta.addEnchant(edata.enchant, level, true);
	        	item.setItemMeta(meta);
	        	
	        	// Create new recipe with enchanted item
	        	MerchantRecipe newRecipe = new MerchantRecipe(item, recipe.getMaxUses());
	
		        for (ItemStack ingredient : recipe.getIngredients()) {
		        	newRecipe.addIngredient(ingredient);
		        }
		        
		        newRecipe.setExperienceReward(recipe.hasExperienceReward());
		        newRecipe.setVillagerExperience(recipe.getVillagerExperience());
		        newRecipe.setSpecialPrice(recipe.getSpecialPrice());
		        newRecipe.setPriceMultiplier(recipe.getPriceMultiplier());
		        newRecipe.setDemand(recipe.getDemand());
		        
		        event.setRecipe(newRecipe);
        	}
    	}
    }
}