package net.maidkleid.listeners;

import net.maidkleid.AimTrainerMain;
import net.maidkleid.utils.Executer;
import net.maidkleid.utils.UtilConfig;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

public class ClickListener implements Listener {

    @EventHandler
    public void onClick(PlayerInteractEvent event) {
        UtilConfig joinUtilConfig = AimTrainerMain.getPlugin(AimTrainerMain.class).getJoinItems();
        Player player = event.getPlayer();

        Executer executer = joinUtilConfig.getExecuter(event.getItem());

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
