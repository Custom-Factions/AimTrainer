package net.maidkleid.listeners;

import net.maidkleid.AimTrainerMain;
import net.maidkleid.arenas.Arena;
import net.maidkleid.arenas.ArenaHandler;
import net.maidkleid.weaponapi.events.ProjectileShootHitEvent;
import org.bukkit.entity.Allay;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class ShootHitListener implements Listener {

    @EventHandler
    public void onShootHit(ProjectileShootHitEvent event) {
        Entity hitEntity = event.getHitEntity();
        if(hitEntity == null) return;
        if(!(hitEntity instanceof Allay allay)) {
            event.setCancelled(true);
            return;
        }

        Player player;
        player = event.getProjectile().getWeaponInstance().getHandlingPlayer();
        allay.setKiller(player);
        allay.remove();
        AimTrainerMain.getPlugin(AimTrainerMain.class).getArenaHandler().addKill(player);
    }


}
