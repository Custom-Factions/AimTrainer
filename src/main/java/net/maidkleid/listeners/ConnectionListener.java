package net.maidkleid.listeners;

import net.maidkleid.AimTrainerMain;
import net.maidkleid.utils.Items;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;

public class ConnectionListener implements Listener {

    private final AimTrainerMain main;

    public ConnectionListener(AimTrainerMain main) {
        this.main = main;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {

        Items joinItems = AimTrainerMain.getPlugin(AimTrainerMain.class).getJoinItems();


        ItemStack joinPaper = joinItems.getJoinPaper();


        Player player = event.getPlayer();
        player.getInventory().setItem(4, joinPaper);
        event.setJoinMessage("§7[§c-§7]" + player.getName() + "hat den Server betreten!");
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        event.setQuitMessage("§7[§c-§7]" + player.getName() + "hat den Server verlassen!");
    }
}
