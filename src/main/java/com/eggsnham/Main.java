package com.eggsnham;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main extends JavaPlugin
{
    public void onEnable()
    {
        //log 'Starting!'
        getLogger().info("Starting!");
        //initialize config files
        initFile();

        //init variables
        File config = new File(this.getDataFolder().getAbsolutePath() + "/config.yml");
        FileConfiguration cfg = YamlConfiguration.loadConfiguration(config);

        Boolean enabled = cfg.getBoolean("tntArrow.enabled");
        Boolean craftable = cfg.getBoolean("tntArrow.craftable");
        Boolean debug = cfg.getBoolean("debug");

        ItemStack boomArrow = new ItemStack(Material.ARROW);
        ItemMeta bm = boomArrow.getItemMeta();
        List<String> lore = new ArrayList<>();

        //set explosive arrow lore
        lore.add("Explosive Arrow");
        bm.setLore(lore);
        boomArrow.setItemMeta(bm);

        //register commands
        getCommand("create").setExecutor(new ExplosiveArrow(debug, boomArrow, enabled));
        //set tab completer
        getCommand("create").setTabCompleter(new ExplosiveArrowTab());
        //setup listeners
        Bukkit.getServer().getPluginManager().registerEvents(new ExplosiveArrowHitListener(enabled, debug), this);
        Bukkit.getServer().getPluginManager().registerEvents(new ExplosiveArrowShootListener(boomArrow, enabled, debug), this);

        getLogger().info("Enabled: " + enabled + "\nDebug: " + debug);

        if(craftable)
        {
            EnableTntCrafting(boomArrow);
        }
    }

    public void initFile() {
        File cfg = new File(this.getDataFolder().getAbsolutePath() + "/config.yml");
        File folder = new File(this.getDataFolder().getAbsolutePath() + "/");
        File debugLog = new File(this.getDataFolder().getAbsolutePath() + "/debug.log");
        File conf = new File(this.getDataFolder().getAbsolutePath() + "/config.txt");
        FileConfiguration cfgWriter = YamlConfiguration.loadConfiguration(cfg);

        if(!folder.exists()) {
            folder.mkdir();
        }

        if(!cfg.exists()) {
            try {
                cfg.createNewFile();
                getLogger().info("Created new file: 'config.yml'");
                cfgWriter.set("tntArrow.craftable", true);
                cfgWriter.set("tntArrow.enabled", true);
                cfgWriter.set("debug", false);
                cfgWriter.save(cfg);
            } catch (IOException e) {
                e.printStackTrace(); 
            }
        }

        if(!debugLog.exists()) {
            try {
                debugLog.createNewFile();
                getLogger().info("Created new file: 'debug.log'");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void EnableTntCrafting(ItemStack arrow) {
        NamespacedKey key = new NamespacedKey(this, "explosive-arrow");
        arrow.setAmount(1);

        ShapedRecipe tntArrowRecipe = new ShapedRecipe(key, arrow);

        tntArrowRecipe.shape("aag", "asa", "faa");

        tntArrowRecipe.setIngredient('a', Material.AIR);
        tntArrowRecipe.setIngredient('s', Material.STICK);
        tntArrowRecipe.setIngredient('g', Material.GUNPOWDER);
        tntArrowRecipe.setIngredient('f', Material.FEATHER);

        getServer().addRecipe(tntArrowRecipe);
    }
}