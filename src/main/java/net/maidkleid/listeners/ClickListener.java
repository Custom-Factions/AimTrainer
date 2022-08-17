package net.maidkleid.listeners;

import net.kyori.adventure.text.event.ClickEvent;
import net.maidkleid.AimTrainerMain;
import net.maidkleid.utils.Executer;
import net.maidkleid.utils.Items;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.persistence.PersistentDataType;


import java.net.http.WebSocket;
import java.util.List;
import java.util.Objects;

public class ClickListener implements Listener {

    private final AimTrainerMain main;

    public ClickListener(AimTrainerMain main) {
        this.main = main;
    }



    @EventHandler
    public void onClick(PlayerInteractEvent event) {
        Items joinItems = AimTrainerMain.getPlugin(AimTrainerMain.class).getJoinItems();
        Player player = event.getPlayer();

        Items inventorys = AimTrainerMain.getPlugin(AimTrainerMain.class).getJoinItems();

        Inventory inv = inventorys.getStartInventory();

        Executer executer = joinItems.getExecuter(event.getItem());

        if (executer == null){
            return;
        }
        executer.execute(player);


        //if (event.getItem().getItemMeta().getPersistentDataContainer().has(joinItems.joinItems));

        /*if (Objects.equals(event.getItem(), joinItems.getJoinPaper())) {
            player.openInventory(inv);
            event.setCancelled(true);

        }*/




    }
}
