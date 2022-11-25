package net.maidkleid.inventorys;


import net.kyori.adventure.text.Component;
import net.maidkleid.AimTrainerMain;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;

public class GameSelector extends GuiInv {
    private static final ItemStack startButton;

    private static final ItemStack endButton;

    static {
        startButton = new ItemStack(Material.GREEN_DYE);
        endButton = new ItemStack(Material.RED_DYE);
        GuiInv.writeLockUniversalByNameSpace(startButton);
        GuiInv.writeItemStackUniversalNameSpaceEvent(startButton, "startButton", event -> {
            InventoryView view = event.getView();
            if (!(view instanceof Player player)) return;
            AimTrainerMain.getPlugin(AimTrainerMain.class).getArenaHandler().leaveArena(player);
        });
        GuiInv.writeItemStackUniversalNameSpaceEvent(endButton, "stopButton", event -> {
            InventoryView view = event.getView();
            if (!(view instanceof Player player)) return;
            AimTrainerMain.getPlugin(AimTrainerMain.class).getArenaHandler().joinArena(player);
        });
        GuiInv.writeLockUniversalByNameSpace(startButton);
        GuiInv.writeLockUniversalByNameSpace(endButton);
        startButton.editMeta(itemMeta -> itemMeta.displayName(Component.text("§2Start-Button")));
        endButton.editMeta(itemMeta -> itemMeta.displayName(Component.text("§4End-Button")));
    }

    public GameSelector(Player owningPlayer, int score) {
        super(InventoryType.CHEST);

        ItemStack score1 = new ItemStack(Material.PAPER);
        score1.editMeta(itemMeta -> {
            itemMeta.displayName(Component.text("§6LastScore"));
            ArrayList<Component> lore = new ArrayList<>();
            lore.add(Component.empty());
            lore.add(Component.text("Dein lezter Score: " + score));
            itemMeta.lore();
        });
        addLockedItem(score1);
        setItem(10, startButton);
        setItem(16, endButton);
        setItem(19, score1);
    }


}
