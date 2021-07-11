package com.eggsnham;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.*;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class ExplosiveArrow implements CommandExecutor{

    public String[] data;
    ItemStack arrow;

    public ExplosiveArrow(String string, ItemStack arrow)
    {
        this.arrow = arrow;

        if(string.contains("\n"))
        {
            this.data = string.split("\n");
        }
        else this.data = string.split(" ");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
    {
        if(sender instanceof Player)
        {
            Player player = (Player)sender;
            if(player.getInventory().containsAtLeast(new ItemStack(Material.ARROW), Integer.valueOf(args[1]))
                    && player.getInventory().containsAtLeast(new ItemStack(Material.GUNPOWDER), Integer.valueOf(args[1]))
                    && args[0].equals("arrow"))
            {
                this.arrow.setAmount(Integer.valueOf(args[0]));
                player.getInventory().addItem(this.arrow);
                player.sendMessage(ChatColor.GREEN + "Successfully created " + args[0] + " explosive arrows");
            } else {
                player.sendMessage(ChatColor.RED + "You don't have enough arrows and/or gunpowder!");
            }
        }
        
        if(data[0].equals("debug=true"))
        {
            sender.sendMessage(ChatColor.RED + "You are not a Player!" + ChatColor.BLUE + "\n===========DEBUG===========\n" + args[0] + "(s) requested: " + args[1] + "");
        } else {
            sender.sendMessage(ChatColor.RED + "You are not a Player!");
        }

        return true;
    }
}
