package net.maidkleid.inventorys;

import org.bukkit.Bukkit;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryInteractEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.jetbrains.annotations.NotNull;

public class InvHandler {

    static public void onInventoryEvent(InventoryInteractEvent event) {
        //System.out.println("test" + event);
        InventoryHolder holder = event.getInventory().getHolder();
        if (event instanceof InventoryClickEvent clickEvent) {
            Inventory clickedInventory = clickEvent.getClickedInventory();
            if (clickedInventory == null) return;
            holder = clickedInventory.getHolder();
        }
        if (holder instanceof Inv inv) inv.onInventoryEvent(event);
    }

    protected static abstract class Inv implements InventoryHolder {

        protected final Inventory inv;


        Inv(int size) {
            this.inv = initInventory(size);
        }

        Inv(InventoryType type) {
            this.inv = initInventory(type);
        }

        protected Inventory initInventory(InventoryType type) {
            return Bukkit.createInventory(this, type);
        }

        protected Inventory initInventory(int size) {
            return Bukkit.createInventory(this, size);
        }


        @Override
        public @NotNull Inventory getInventory() {
            return inv;
        }

        abstract public boolean onInventoryEvent(InventoryInteractEvent event);
    }

}
