package net.maidkleid.listeners;

import net.maidkleid.AimTrainerMain;
import net.maidkleid.utils.Executer;
import net.maidkleid.utils.UtilConfig;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class InventoryClickListener implements Listener {
    private final AimTrainerMain main;

    public InventoryClickListener(AimTrainerMain main) {
        this.main = main;
    }


    @EventHandler
    public void clickEvent(InventoryClickEvent event) {
        ItemStack startButton = this.main.getJoinItems().getStartButton();
        ItemStack endButton = this.main.getJoinItems().getEndButton();
        Player player = (Player)event.getWhoClicked();
        UtilConfig joinItems = ((AimTrainerMain)AimTrainerMain.getPlugin(AimTrainerMain.class)).getJoinItems();
        Executer executer = joinItems.getExecuterInv(event.getCurrentItem());
        if (executer == null)
            return;
        executer.execute(player);
        event.setCancelled(true);
    }

    
}
