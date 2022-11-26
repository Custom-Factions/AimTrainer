package net.maidkleid.inventorys;


import net.kyori.adventure.text.Component;
import net.maidkleid.AimTrainerMain;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;

public class GameSelector extends GuiInv {
    private static final ItemStack startButton;
    private static final ItemStack endButton;

    static {
        //START_BUTTON
        startButton = new ItemStack(Material.GREEN_DYE);
        GuiInv.writeLockUniversalByNameSpace(startButton);
        GuiInv.writeItemStackUniversalNameSpaceEvent(startButton, "startButton", event -> {
            //System.out.println("start-Button");
            if (!(event.getWhoClicked() instanceof Player player)) return;
            player.sendMessage("start-Button");
            AimTrainerMain.getPlugin(AimTrainerMain.class).getArenaHandler().joinArena(player);

        });
        startButton.editMeta(itemMeta -> itemMeta.displayName(Component.text("§2Start-Button")));

        //END_BUTTON
        endButton = new ItemStack(Material.RED_DYE);
        GuiInv.writeItemStackUniversalNameSpaceEvent(endButton, "stopButton", event -> {
            //System.out.println("stop-Button");
            if (!(event.getWhoClicked() instanceof Player player)) return;
            player.sendMessage("stop-Button");
            AimTrainerMain.getPlugin(AimTrainerMain.class).getArenaHandler().leaveArena(player);
        });
        GuiInv.writeLockUniversalByNameSpace(startButton);
        GuiInv.writeLockUniversalByNameSpace(endButton);
        endButton.editMeta(itemMeta -> itemMeta.displayName(Component.text("§4End-Button")));
    }

    public GameSelector(Player owningPlayer, int score) {
        super(InventoryType.CHEST);

        ItemStack scoreButton = new ItemStack(Material.PAPER);
        scoreButton.editMeta(itemMeta -> {
            itemMeta.displayName(Component.text("§6LastScore"));
            ArrayList<Component> lore = new ArrayList<>();
            lore.add(Component.empty());
            lore.add(Component.text("Dein lezter Score: " + score));
            itemMeta.lore(lore);
        });
        addLockedItem(scoreButton);
        addEventToItem(scoreButton, event -> {
            owningPlayer.playSound(owningPlayer, Sound.ENTITY_CAT_HURT, 1, 1);
        });
        setItem(10, startButton);
        setItem(16, endButton);
        setItem(19, scoreButton);
    }


}
