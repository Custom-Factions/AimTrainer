package net.maidkleid.inventorys;

import net.maidkleid.AimTrainerMain;
import net.maidkleid.data.PlayerData;
import net.maidkleid.weaponapi.utils.WeaponItemMidLevelUtils;
import net.maidkleid.weapons.WeaponTable;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class WeaponSelector extends GuiInv {

    private static final WeaponSelector selector = new WeaponSelector(AimTrainerMain.getActivatedWeapons());
    private static final WeaponSelector developerSelector = new WeaponSelector(WeaponTable.getAllWeaponNames());

    WeaponSelector(List<String> activatedWeapons) {
        super(9 * (1 + activatedWeapons.size() / 9));
        for (int i = 0; i < activatedWeapons.size(); i++) {
            ItemStack stack = getWeaponItem(activatedWeapons.get(i));
            setItem(i, stack);
        }
    }

    public static WeaponSelector get() {
        return selector;
    }

    public static WeaponSelector getAdminMenu() {
        return developerSelector;
    }

    public ItemStack getWeaponItem(String w) {
        ItemStack stack = WeaponItemMidLevelUtils.getWeaponItem(WeaponTable.getLowestCustomModelDataID(WeaponTable.getWeaponID(w)), 1);
        GuiInv.writeLockUniversalByNameSpace(stack);
        GuiInv.writeItemStackUniversalNameSpaceEvent(stack, "setWeaponTo" + w, event -> {
            Player p = (Player) event.getWhoClicked();
            PlayerData data = AimTrainerMain.getDataBase().getData(p.getUniqueId());
            data.setWeaponName(w);
            data.save();
            p.openInventory(new GameConfig(p).inv);
        });

        return stack;
    }
}
