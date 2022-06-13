package com.eggsnham;

import com.eggsnham.DebugLogger;
import org.bukkit.GameMode;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.inventory.ItemStack;

public class ExplosiveArrowShootListener implements Listener {

    DebugLogger Debug;
    ItemStack arrow;
    Boolean enabled;
    Boolean debug;

    public ExplosiveArrowShootListener(ItemStack arrow, Boolean enabled, Boolean debug, DebugLogger Debug) {
        this.arrow = arrow;
        this.debug = debug;
        this.enabled = enabled;
        this.Debug = Debug;

        this.arrow.setAmount(1);
    }

    @EventHandler
    public void onShoot(EntityShootBowEvent event) {
        if(event.getEntity() instanceof Player)
        {
            Player player = (Player)event.getEntity();

            if(player.getInventory().getItemInOffHand().getAmount() > 0 && player.getInventory().getItemInOffHand().getItemMeta().getLore().get(0).equals("Explosive Arrow") && enabled)
            {
                event.setCancelled(true);
                Class<Arrow> newarrow = Arrow.class;
                Arrow larrow = player.launchProjectile(newarrow);
                larrow.setCustomName("explosive");
                larrow.setCustomNameVisible(false);
                if(player.getGameMode() != GameMode.CREATIVE){
                    arrow.setAmount(player.getInventory().getItemInOffHand().getAmount() - 1);
                    player.getInventory().setItemInOffHand(arrow);
                }
                player.updateInventory();
            }
            else if(debug)
            {
                Debug.log("Normal arrow shot\n        at ExplosiveArrowListener.java:34");
            }
        }
    }
}
