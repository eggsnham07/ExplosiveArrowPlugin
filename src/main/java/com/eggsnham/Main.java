package com.eggsnham;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
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
        //init variables
        String data = getData("/config.txt");
        ItemStack boomArrow = new ItemStack(Material.ARROW);
        ItemMeta bm = boomArrow.getItemMeta();
        List<String> lore = new ArrayList<>();

        //set explosive arrow lore
        lore.add("Explosive Arrow");
        bm.setLore(lore);

        //log 'Starting!'
        getLogger().info("Starting!");
        //initialize config files
        initFile();
        //register commands
        getCommand("create").setExecutor(new ExplosiveArrow(data, boomArrow));
        //set tab completers
        getCommand("create").setTabCompleter(new ExplosiveArrowTab());
        //setup listeners
        Bukkit.getServer().getPluginManager().registerEvents(new ExplosiveArrowHitListener(), this);
        Bukkit.getServer().getPluginManager().registerEvents(new ExplosiveArrowShootListener(boomArrow), this);
    }

    public void initFile() {
        File cfg = new File(this.getDataFolder().getAbsolutePath() + "/config.yml");
        File folder = new File(this.getDataFolder().getAbsolutePath() + "/");
        File debugLog = new File(this.getDataFolder().getAbsolutePath() + "/debug.log");
        File conf = new File(this.getDataFolder().getAbsolutePath() + "/config.txt");

        if(!folder.exists()) {
            folder.mkdir();
        }

        if(!cfg.exists()) {
            try {
                cfg.createNewFile();
                getLogger().info("Created new file: 'config.yml'");
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
        
        if(!conf.exists()) {
            try {
                conf.createNewFile();

                if(conf.exists()) {
                    FileWriter writer = new FileWriter(this.getDataFolder() + "/config.txt");
                    writer.write("debug=false\n");
                    writer.close();
                }
                getLogger().info("Created new file: 'config.txt'");
            } catch(IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    public String getData(String filename) {
        StringBuilder data = new StringBuilder("");
        List<String> arr = new ArrayList<>();
        try {
            File file = new File(this.getDataFolder() + filename);
            Scanner reader = new Scanner(file);
            while(reader.hasNextLine()) {
                arr.add(reader.nextLine());
            }
            reader.close();
            
            for(String str : arr) {
                data.append(str);
            }

        } catch(FileNotFoundException ex) {
            ex.printStackTrace();
        }
        return data.toString();
    }
}