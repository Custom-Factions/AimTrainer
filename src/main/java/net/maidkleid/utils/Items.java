package net.maidkleid.utils;

import net.maidkleid.AimTrainerMain;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;

public class Items {

    private final AimTrainerMain main;
    public final NamespacedKey joinItems;
    private final ItemStack joinPaper;

    private final Inventory startInventory;


    public Items(AimTrainerMain main) {
        this.main = main;

        joinItems = new NamespacedKey(main, "join.item");
        joinPaper = new ItemStack(Material.PAPER);

        startInventory = Bukkit.createInventory(null, InventoryType.CHEST);


        joinPaper.editMeta(itemMeta -> {
            itemMeta.getPersistentDataContainer().set(joinItems, PersistentDataType.STRING, "START_PAPER");
            itemMeta.setDisplayName("§aStart-Menü");
        });
    }


    public Inventory getStartInventory() {
        return startInventory;
    }

    public ItemStack getJoinPaper() {
        return joinPaper.clone();
    }
}
