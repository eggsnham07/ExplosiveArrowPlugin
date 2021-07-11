package com.eggsnham;

import org.bukkit.entity.Arrow;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileHitEvent;

public class ExplosiveArrowHitListener implements Listener {

    @EventHandler
    public void onShoot(ProjectileHitEvent event) {
        if(event.getEntity() instanceof Arrow)
        {
            Arrow arrow = (Arrow)event.getEntity();
            if(arrow.getCustomName().equals("explosive"))
            {
                event.getHitBlock().getWorld().createExplosion(arrow.getLocation(), 11);
            }
        }
    }
}
