package com.eggsnham;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

public class ExplosiveArrowTab implements TabCompleter {

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        List<String> tabs = new ArrayList<>();
        if(args.length == 1) 
        {
            tabs.clear();
            tabs.add("arrow");
        }

        if(args.length == 2) 
        {
            tabs.clear();
            for(int i=0;i<=64;i++) {
                tabs.add(String.valueOf(i));
            }
        }
        return tabs;
    }

}
