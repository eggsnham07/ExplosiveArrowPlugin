package com.eggsnham;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.*;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class ExplosiveArrow implements CommandExecutor {

    public String[] data;
    ItemStack arrow;
    Boolean enabled = false;
    Boolean debug = false;

    public ExplosiveArrow(Boolean debug, ItemStack arrow, Boolean enabled)
    {
        this.arrow = arrow;
        this.enabled = enabled;
        this.debug = debug;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
    {
        if(sender instanceof Player)
        {
            Player player = (Player)sender;
            if(player.getInventory().containsAtLeast(new ItemStack(Material.ARROW), Integer.valueOf(args[1]))
                    && player.getInventory().containsAtLeast(new ItemStack(Material.GUNPOWDER), Integer.valueOf(args[1])) && enabled == true
                    || args.length == 3 && args[2].equals("cheatcode=144") && enabled == true)
            {
                if(args[0].equals("arrow"))
                {
                    this.arrow.setAmount(Integer.valueOf(args[1]));
                    player.getInventory().addItem(this.arrow);
                    player.sendMessage(ChatColor.GREEN + "Successfully created " + args[0] + " explosive arrows");
                }
            } else {
                player.sendMessage(ChatColor.RED + "You don't have enough arrows and/or gunpowder!");
                if(debug == true)
                {
                    player.sendMessage(ChatColor.BLUE + "\n===========DEBUG===========\nenabled: " + enabled);
                }
            }
        } else {
            if(debug == true)
            {
                sender.sendMessage(ChatColor.RED + "You are not a Player!" + ChatColor.BLUE + "\n===========DEBUG===========\n" + args[0] + "(s) requested: " + args[1] + "");
            } else {
                sender.sendMessage(ChatColor.RED + "You are not a Player!");
            }
        }

        return true;
    }
}
