package com.eggsnham;

import com.eggsnham.Debug.DebugLogger;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.*;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class ExplosiveArrow implements CommandExecutor {
    DebugLogger Debug;
    ItemStack arrow;
    Boolean enabled;
    Boolean debug;

    public ExplosiveArrow(ItemStack arrow, Boolean enabled, Boolean debug, DebugLogger Debug)
    {
        this.arrow = arrow;
        this.enabled = enabled;
        this.debug = debug;
        this.Debug = Debug;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
    {
        if(sender instanceof Player)
        {
            Player player = (Player)sender;
            if(player.getInventory().containsAtLeast(new ItemStack(Material.ARROW), Integer.valueOf(args[1]))
                    && player.getInventory().containsAtLeast(new ItemStack(Material.GUNPOWDER), Integer.valueOf(args[1])) && enabled
                    || args.length == 3 && args[2].equals("cheatcode=144") && enabled)
            {
                if(args[0].equals("arrow"))
                {
                    this.arrow.setAmount(Integer.valueOf(args[1]));
                    player.getInventory().addItem(this.arrow);
                    player.sendMessage(ChatColor.GREEN + "Successfully created " + args[0] + " explosive arrows");
                }
            } else {
                player.sendMessage(ChatColor.RED + "You don't have enough arrows and/or gunpowder!");
            }
        } else {
            if(debug)
            {
                sender.sendMessage(ChatColor.RED + "You are not a Player!");
                Debug.log(args[0] + " requested: " + args[1] + "\n        at ExplosiveArrow.java:28");
            } else {
                sender.sendMessage(ChatColor.RED + "You are not a Player!");
            }
        }

        return true;
    }
}
