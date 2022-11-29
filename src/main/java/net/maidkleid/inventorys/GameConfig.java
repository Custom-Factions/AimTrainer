package net.maidkleid.inventorys;

import net.kyori.adventure.text.Component;
import net.maidkleid.AimTrainerMain;
import net.maidkleid.arenas.Difficulty;
import net.maidkleid.data.PlayerData;
import net.maidkleid.weaponapi.utils.WeaponItemMidLevelUtils;
import net.maidkleid.weapons.WeaponTable;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;

public class GameConfig extends GuiInv {

    private static final String difficultyChangeEventNMS = "Difficulty";
    private static final int difficultySlot = 11;
    private static final int weaponChoseSlot = 15;
    private static final String choseWeapon = "choseWeapon";

    static {

        GuiInv.writeItemStackUniversalNameSpaceEvent(difficultyChangeEventNMS, GameConfig::changeDifficulty);
        GuiInv.writeItemStackUniversalNameSpaceEvent(choseWeapon, event -> {
            HumanEntity whoClicked = event.getWhoClicked();
            if (whoClicked.hasPermission("aimtrainer.admin.weaponselector"))
                whoClicked.openInventory(WeaponSelector.getAdminMenu().getInventory());
            else whoClicked.openInventory(WeaponSelector.get().getInventory());
        });
    }

    private final PlayerData data;

    GameConfig(Player player) {
        super(3 * 9);
        data = AimTrainerMain.getDataBase().getData(player.getUniqueId());
        buildInventory();
    }

    private static void changeDifficulty(InventoryClickEvent clickEvent) {
        if (!(clickEvent.getClickedInventory().getHolder() instanceof GameConfig gameConfig)) return;
        if (clickEvent.getAction() == InventoryAction.MOVE_TO_OTHER_INVENTORY) {
            if (gameConfig.data.getDifficulty() instanceof Difficulty.Defaults)
                gameConfig.data.setDifficulty(new Difficulty.CustomDifficulty(20, 10));
            else gameConfig.data.setDifficulty(Difficulty.Defaults.EASY);
            return;
        }
        Difficulty.Defaults d = Difficulty.Defaults.NORMAL;
        if (gameConfig.data.getDifficulty() instanceof Difficulty.Defaults defaults) {
            switch (defaults) {
                case NORMAL -> d = Difficulty.Defaults.HARD;
                case HARD -> d = Difficulty.Defaults.EASY;
            }
        }
        gameConfig.data.setDifficulty(d);
        gameConfig.data.save();
        gameConfig.setDifficultyItem();
    }

    public ItemStack getDifficultyItem() {
        Material m = Material.COMMAND_BLOCK;
        ;
        Component title = Component.text(ChatColor.BLUE + "Custom");
        ArrayList<Component> lore = new ArrayList<>();
        if (data.getDifficulty() instanceof Difficulty.Defaults difficulty) {
            switch (difficulty) {
                case EASY -> {
                    m = Material.GREEN_CONCRETE;
                    title = Component.text(ChatColor.GREEN + "Easy");
                }
                case NORMAL -> {
                    m = Material.ORANGE_CONCRETE;
                    title = Component.text(ChatColor.GOLD + "Normal");
                }
                case HARD -> {
                    m = Material.RED_CONCRETE;
                    title = Component.text(ChatColor.RED + "Hard");
                }
            }
        }
        lore.add(Component.text("MaxSpawns: " + data.getDifficulty().maxSpawns()));
        lore.add(Component.text("SpawnRate: " + data.getDifficulty().spawnRate()));
        ItemStack stack = new ItemStack(m);
        Component finalTitle = title;
        stack.editMeta(itemMeta -> {
            itemMeta.displayName(finalTitle);
            itemMeta.lore(lore);
            writeItemStackUniversalNameSpaceEvent(difficultyChangeEventNMS, itemMeta);
        });
        GuiInv.writeLockUniversalByNameSpace(stack);
        return stack;
    }

    public ItemStack getWeaponChoseItem() {
        ItemStack stack = WeaponItemMidLevelUtils.getWeaponItem(WeaponTable.getLowestCustomModelDataID(WeaponTable.getWeaponID(data.getWeaponName())), 1);
        GuiInv.writeLockUniversalByNameSpace(stack);
        GuiInv.writeItemStackUniversalNameSpaceEvent(stack, choseWeapon);
        return stack;
    }

    public void buildInventory() {
        setDifficultyItem();
        setWeaponChoseItem();
    }

    private void setDifficultyItem() {
        setItem(difficultySlot, getDifficultyItem());
    }

    private void setWeaponChoseItem() {
        setItem(weaponChoseSlot, getWeaponChoseItem());
    }
}
