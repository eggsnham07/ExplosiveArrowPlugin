package com.eggsnham;

import com.eggsnham.DebugLogger;
import org.bukkit.entity.Arrow;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileHitEvent;

public class ExplosiveArrowHitListener implements Listener {
    DebugLogger Debug;
    Boolean enabled;
    Boolean debug;

    public ExplosiveArrowHitListener(Boolean enabled, Boolean debug, DebugLogger Debug) {
        this.enabled = enabled;
        this.debug = debug;
        this.Debug = Debug;
    }

    @EventHandler
    public void onShoot(ProjectileHitEvent event) {
        if(event.getEntity() instanceof Arrow)
        {
            Arrow arrow = (Arrow)event.getEntity();
            if(arrow.getCustomName() != null)
            {
                if (arrow.getCustomName().equals("explosive") && enabled)
                {
                    if(event.getHitEntity() != null) event.getEntity().getWorld().createExplosion(arrow.getLocation(), 10);
                    else event.getHitBlock().getWorld().createExplosion(arrow.getLocation(), 10);

                    event.getEntity().remove();
                }
            } else {
                if(debug) {
                    Debug.log("Arrow had no name, so not explosive\n        at ExplosiveArrowHitListener.java:26");
                }
            }
        }
    }
}
