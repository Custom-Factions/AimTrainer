package net.maidkleid.utils;

import net.maidkleid.AimTrainerMain;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
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
    private final ItemStack score;





    private final Inventory startInventory;


    public UtilConfig(AimTrainerMain main) {
        this.main = main;

        joinItems = new NamespacedKey(main, "join.item");
        joinPaper = new ItemStack(Material.PAPER);
        startButton = new ItemStack(Material.GREEN_DYE);
        endButton = new ItemStack(Material.RED_DYE);
        score = new ItemStack(Material.PAPER);



        startInventory = Bukkit.createInventory(null, InventoryType.CHEST, "Start-Inventory");

        startButton.editMeta(itemMeta -> {
           itemMeta.setDisplayName("§2Start-Button");
        });
        endButton.editMeta(itemMeta -> {
           itemMeta.setDisplayName("§4End-Button");
        });

        score.editMeta(itemMeta -> {
           itemMeta.setDisplayName("§6LastScore");
            ArrayList<String> lore = new ArrayList<>();
            lore.add("");
            lore.add("Dein lezter Score: ");
            itemMeta.setLore(lore);
        });

        startInventory.setItem(10, startButton);
        startInventory.setItem(16, endButton);
        startInventory.setItem(19, score);


        joinPaper.editMeta(itemMeta -> {
            itemMeta.getPersistentDataContainer().set(joinItems, PersistentDataType.STRING, "START_PAPER");
            itemMeta.setDisplayName("§aStart-Menü");

        });

        executerMap.put(joinPaper, (player) -> {
            player.openInventory(startInventory);
            return true;

        });

        executerMapInv.put(startButton, (player) -> {
          main.getArenaHandler().joinArena(player);
          return true;
        });

        executerMapInv.put(endButton, (player) -> {
            main.getArenaHandler().leaveArena(player);
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
