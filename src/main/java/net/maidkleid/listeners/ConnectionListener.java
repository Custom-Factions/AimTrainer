package net.maidkleid.listeners;

import net.kyori.adventure.text.Component;
import net.maidkleid.AimTrainerMain;
import net.maidkleid.utils.UtilConfig;
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

        UtilConfig joinUtilConfig = AimTrainerMain.getPlugin(AimTrainerMain.class).getJoinItems();

        ItemStack joinPaper = joinUtilConfig.getJoinPaper();

        Player player = event.getPlayer();
        player.getInventory().setItem(4, joinPaper);
        event.joinMessage(Component.text("§7[§2+§7] " + player.getName() + " hat den Server betreten!"));
        main.getDataBase().getData(player.getUniqueId()).save();

    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        event.quitMessage(Component.text("§7[§c-§7] " + player.getName() + " hat den Server verlassen!"));
        main.getDataBase().getData(player.getUniqueId()).save();
    }
}
