package com.eggsnham;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.inventory.ItemStack;

public class ExplosiveArrowShootListener implements Listener {
    ItemStack arrow;

    public ExplosiveArrowShootListener(ItemStack arrow) {
        this.arrow = arrow;
    }


    @EventHandler
    public void onShoot(EntityShootBowEvent event) {
        if(event.getEntity() instanceof Player)
        {
            Player player = (Player)event.getEntity();

            if(player.getInventory().getItemInOffHand().equals(this.arrow))
            {
                event.getProjectile().setCustomName("explosive");
                event.getProjectile().setCustomNameVisible(false);
            }
        }
    }
}
