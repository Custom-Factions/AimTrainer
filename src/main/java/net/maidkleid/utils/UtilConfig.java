package net.maidkleid.utils;

import net.maidkleid.AimTrainerMain;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;

import java.util.HashMap;
import java.util.UUID;
import java.util.Vector;

public class UtilConfig {

    private final HashMap<ItemStack, Executer> executerMap = new HashMap<>();
    private final HashMap<ItemStack, Executer> executerMapInv = new HashMap<>();
    private final HashMap<UUID, Long> startTimeMap = new HashMap<>();
    private final AimTrainerMain main;
    public final NamespacedKey joinItems;
    private final ItemStack joinPaper;
    private final ItemStack startButton;
    private final ItemStack endButton;





    private final Inventory startInventory;


    public UtilConfig(AimTrainerMain main) {
        this.main = main;

        joinItems = new NamespacedKey(main, "join.item");
        joinPaper = new ItemStack(Material.PAPER);
        startButton = new ItemStack(Material.GREEN_DYE);
        endButton = new ItemStack(Material.RED_DYE);



        startInventory = Bukkit.createInventory(null, InventoryType.CHEST, "Start-Inventory");

        startButton.editMeta(itemMeta -> {
           itemMeta.setDisplayName("Start-Button");
        });
        endButton.editMeta(itemMeta -> {
           itemMeta.setDisplayName("End-Button");
        });

        startInventory.setItem(10, startButton);
        startInventory.setItem(16, endButton);


        joinPaper.editMeta(itemMeta -> {
            itemMeta.getPersistentDataContainer().set(joinItems, PersistentDataType.STRING, "START_PAPER");
            itemMeta.setDisplayName("§aStart-Menü");

        });

        executerMap.put(joinPaper, (player) -> {
            player.openInventory(startInventory);
            return true;

        });

        executerMapInv.put(startButton, (player) -> {
            player.sendMessage("Test start");

            Long startTime = startTimeMap.get(player.getUniqueId());
            Long currentTime = System.currentTimeMillis();

            if (startTime == null) {
                player.sendMessage("Die Zeit wurde gestartet!");
                startTimeMap.put(player.getUniqueId(), currentTime);
            } else {
                player.sendMessage("Die Zeit läuft bereits!");
            }

            return true;
        });

        executerMapInv.put(endButton, (player) -> {
            player.sendMessage("Test End!");

            Long startTime = startTimeMap.get(player.getUniqueId());
            Long currentTime = System.currentTimeMillis();

            if (startTime != null) {
                player.sendMessage("Die Zeit wurde gestoppt!");
                long duration = currentTime - startTimeMap.get(player.getUniqueId());
                player.sendMessage("Die Runde hat " + duration/1000 + " sekunden gedauert!");
                startTimeMap.remove(player.getUniqueId());
            } else {
                player.sendMessage("Die Zeit läuft gar nicht!");
            }

            return true;
        });


    }


    public Inventory getStartInventory() {
        return startInventory;
    }

    public ItemStack getJoinPaper() {
        return joinPaper.clone();
    }

    public ItemStack getStartButton() {
        return startButton.clone();
    }
    public ItemStack getEndButton() {
        return endButton.clone();
    }

    public Executer getExecuter(ItemStack stack) {
        return executerMap.get(stack);
    }

    public Executer getExecuterInv(ItemStack stack) {
        return executerMapInv.get(stack);
    }
}
