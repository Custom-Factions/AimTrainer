package net.maidkleid.utils;

import net.kyori.adventure.text.Component;
import net.maidkleid.AimTrainerMain;
import net.maidkleid.inventorys.GameSelector;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;

import java.util.HashMap;

public class UtilConfig {

    private final HashMap<ItemStack, Executer> executerMap = new HashMap<>();
    public final NamespacedKey joinItems;
    private final ItemStack joinPaper;



    public UtilConfig(AimTrainerMain main) {

        joinItems = new NamespacedKey(main, "join.item");
        joinPaper = new ItemStack(Material.PAPER);


        joinPaper.editMeta(itemMeta -> {
            itemMeta.getPersistentDataContainer().set(joinItems, PersistentDataType.STRING, "START_PAPER");
            itemMeta.displayName(Component.text("§aStart-Menü"));

        });

        executerMap.put(joinPaper, (player) -> {
            player.openInventory(new GameSelector(player, 0).getInventory());
            return true;

        });

    }

    public ItemStack getJoinPaper() {
        return joinPaper.clone();
    }

    public Executer getExecuter(ItemStack stack) {
        return executerMap.get(stack);
    }

}
