package com.eggsnham;

import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.inventory.ItemStack;

public class ExplosiveArrowShootListener implements Listener {
    ItemStack arrow;

    public ExplosiveArrowShootListener(ItemStack arrow) {
        this.arrow = arrow;
        this.arrow.setAmount(1);
    }


    @EventHandler
    public void onShoot(EntityShootBowEvent event) {
        if(event.getEntity() instanceof Player)
        {
            Player player = (Player)event.getEntity();

            if(player.getInventory().getItemInOffHand().equals(this.arrow))
            {
                event.setCancelled(true);
                Class<Arrow> newarrow = Arrow.class;
                Arrow larrow = player.launchProjectile(newarrow);
                larrow.setCustomName("explosive");
                larrow.setCustomNameVisible(false);
                player.getInventory().removeItem(arrow);
                player.updateInventory();
            }
        }
    }
}
