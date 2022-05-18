package com.eggsnham;

import com.eggsnham.Debug.DebugLogger;
import com.eggsnham.Debug.Level;
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
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main extends JavaPlugin
{
    public File debugLog = new File(getDataFolder().getAbsolutePath() + "/debug.log");
    public DebugLogger Debug = new DebugLogger(debugLog);
    public Boolean debug = true;
    public Boolean craftable;
    public Boolean enabled;
    public ItemStack boomArrow = new ItemStack(Material.ARROW);

    public void onEnable()
    {
        //Tell the console that the plugin is starting
        getLogger().info("Starting!");
        //Check for configuration files
        initFile();
        //Load Config files
        File config = new File(this.getDataFolder().getAbsolutePath() + "/config.yml");
        FileConfiguration cfg = YamlConfiguration.loadConfiguration(config);
        //Define config variables
        enabled = cfg.getBoolean("tntArrow.enabled");
        craftable = cfg.getBoolean("tntArrow.craftable");
        this.debug = cfg.getBoolean("debug");
        if(debug) Debug.log("Plugin has started!");
        //Define the explosive arrow ItemStack
        ItemMeta bm = boomArrow.getItemMeta();
        List<String> lore = new ArrayList<>();
        //Set the lore for the explosive arrow
        lore.add("Explosive Arrow");
        bm.setLore(lore);
        boomArrow.setItemMeta(bm);

        //register commands
        getCommand("create").setExecutor(new ExplosiveArrow(boomArrow, enabled, debug, Debug));
        //set tab completer
        getCommand("create").setTabCompleter(new ExplosiveArrowTab());
        //setup listeners
        Bukkit.getServer().getPluginManager().registerEvents(new ExplosiveArrowHitListener(enabled, debug, Debug), this);
        Bukkit.getServer().getPluginManager().registerEvents(new ExplosiveArrowShootListener(boomArrow, enabled, debug, Debug), this);
        //Tell the console if debug is enabled for the plugin
        getLogger().info("Debug: " + debug);
        //Check if crafting the explosive arrow is allowed, if so create new recipe for it
        if(craftable)
        {
            EnableTntCrafting(boomArrow);
            if(debug) Debug.log("Crafting explosive arrows is enabled!", Level.WARNING);
        }
    }

    public void onDisable() {
        if(debug) Debug.log("Plugin stopping...\n\n\n");
    }

    public void initFile() {
        //Define files
        File cfg = new File(this.getDataFolder().getAbsolutePath() + "/config.yml");
        File folder = new File(this.getDataFolder().getAbsolutePath() + "/");
        File debugLog = new File(this.getDataFolder().getAbsolutePath() + "/debug.log");
        File conf = new File(this.getDataFolder().getAbsolutePath() + "/config.txt");
        //Load config file
        FileConfiguration cfgWriter = YamlConfiguration.loadConfiguration(cfg);
        //Check if plugin folder exists, if not then create the folder
        if(!folder.exists()) {
            folder.mkdir();
        }
        //Check if config file exists, if not create it
        if(!cfg.exists()) {
            try {
                cfg.createNewFile();
                getLogger().info("Created new file: 'config.yml'");
                //Set default variables
                cfgWriter.set("tntArrow.craftable", true);
                cfgWriter.set("tntArrow.enabled", true);
                cfgWriter.set("debug", true);
                cfgWriter.save(cfg);
            } catch (IOException e) {
                e.printStackTrace(); 
            }
        }
        //Check if debug file exists, if not create it
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