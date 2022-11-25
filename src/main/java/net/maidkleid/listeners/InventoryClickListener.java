package net.maidkleid.listeners;

import net.maidkleid.inventorys.InvHandler;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class InventoryClickListener implements Listener {

    @EventHandler
    public void clickEvent(InventoryClickEvent event) {
        //System.out.println("test" + event);
        InvHandler.onInventoryEvent(event);
        //ItemStack startButton = this.main.getJoinItems().getStartButton();
        //ItemStack endButton = this.main.getJoinItems().getEndButton();
        //Player player = (Player)event.getWhoClicked();
        //UtilConfig joinItems = ((AimTrainerMain)AimTrainerMain.getPlugin(AimTrainerMain.class)).getJoinItems();
        //Executer executer = joinItems.getExecuterInv(event.getCurrentItem());
        //if (executer == null)
        //    return;
        //executer.execute(player);
        //event.setCancelled(true);
    }


}
