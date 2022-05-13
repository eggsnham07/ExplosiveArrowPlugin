package com.eggsnham;

import org.bukkit.entity.Arrow;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileHitEvent;

public class ExplosiveArrowHitListener implements Listener {
    Boolean enabled;
    Boolean debug;
    public ExplosiveArrowHitListener(Boolean enabled, Boolean debug) {
        this.enabled = enabled;
        this.debug = debug;
    }

    @EventHandler
    public void onShoot(ProjectileHitEvent event) {
        if(event.getEntity() instanceof Arrow)
        {
            Arrow arrow = (Arrow)event.getEntity();
            if(arrow.getCustomName() != null)
            {
                if (arrow.getCustomName().equals("explosive") && enabled == true)
                {
                    if(event.getHitEntity() != null) event.getEntity().getWorld().createExplosion(arrow.getLocation(), 10);
                    else event.getHitBlock().getWorld().createExplosion(arrow.getLocation(), 10);

                    event.getEntity().remove();
                }
            } else {
                if(this.debug == true) {
                    System.out.println("===========DEBUG===========\nArrow had no name, so not explosive\nenabled: " + enabled + "\n\n");
                }
            }
        }
    }
}
