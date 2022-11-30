package net.maidkleid.arenas;

import net.maidkleid.AimTrainerMain;
import net.maidkleid.utils.Messages;
import net.maidkleid.weaponapi.utils.WeaponItemMidLevelUtils;
import net.maidkleid.weapons.WeaponTable;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Allay;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.Nullable;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.UUID;

public class Arena {
    private final String name;
    private final Location spawnLocation;
    private final Box box;
    private final AimTrainerMain main;
    private Game currentGame;
    private int spawnScheduler;
    private final ArrayList<Allay> livingAllays;
    private ArenaHandler handler;


    Arena(AimTrainerMain main, String name, Location spawnLocation, Location boxLocationOne, Location boxLocationTwo, boolean setBarrierCage) {
        this.main = main;
        this.name = name;
        this.spawnLocation = spawnLocation;
        this.box = new Box(boxLocationOne, boxLocationTwo);
        if (setBarrierCage) box.buildBarrierCage();
        livingAllays = new ArrayList<>();
    }

    protected void startGame(Player player, ArenaHandler handler) {
        this.handler = handler;
        main.getLogger().info(player.getName() + " has started the game: " + name);
        UUID uuid = player.getUniqueId();

        currentGame = Game.newGame(this, AimTrainerMain.getDataBase().getData(uuid));
        player.teleport(spawnLocation);
        player.sendMessage(Messages.DE.teleport());
        player.sendMessage(Messages.DE.startGame(currentGame));
        spawnScheduler = Bukkit.getScheduler().scheduleSyncRepeatingTask(main, this::checkAllays, currentGame.difficulty().spawnRate(), currentGame.difficulty().spawnRate());
        //ItemStack pistol = WeaponItemMidLevelUtils.getWeaponItem(WeaponProvider.getWeaponID(WeaponProvider.PISTOL.getName()), 1);
        //int weaponID = WeaponTable.AK47_ID;
        //int lowestCustomModelDataID = WeaponTable.getLowestCustomModelDataID(weaponID);
        //main.getLogger().info(lowestCustomModelDataID + " " + WeaponTable.AK47_ID + " " + WeaponTable.AK47 + " " + WeaponTable.getWeapon(lowestCustomModelDataID));
        int lowestCustomModelDataID = WeaponTable.getLowestCustomModelDataID(WeaponTable.getWeaponID(currentGame.weapon()));
        ItemStack weaponItem = WeaponItemMidLevelUtils.getWeaponItem(lowestCustomModelDataID, 1);
        player.getInventory().setItem(0, weaponItem);
    }

    protected Game endGame() {
        Player p = p();
        p.sendMessage(Messages.DE.endGame(currentGame));
        main.getLogger().info(p.getName() + " has end his game: " + name + " score: " + currentGame.score().get() + "\n Das Spiel ging: " + stopTime().toMillis() / 1000 + " sekunden lang!");
        Bukkit.getScheduler().cancelTask(spawnScheduler);

        //int weaponID = WeaponTable.getWeaponID(WeaponProvider.PISTOL.getName());
        //player.getInventory().removeItem(WeaponItemMidLevelUtils.getWeaponItem(WeaponProvider.getLowestCustomModelDataID(weaponID), 1));
        @Nullable ItemStack[] contents = p.getInventory().getContents();
        for (int i = 0; i < contents.length; i++) {
            if (contents[i] == null) continue;
            if (WeaponTable.getWeaponInstance(contents[i], p, i) != null)
                p.getInventory().remove(contents[i]);
        }
        livingAllays.removeIf(allay -> {
            allay.remove();
            return true;
        });
        Game g = currentGame;
        currentGame = null;
        return g;
    }

    protected void addKill() {
        currentGame.score().incrementAndGet();
    }

    protected void checkAllays() {
        int allayValue = currentGame.difficulty().maxSpawns();

        livingAllays.removeIf(Entity::isDead);
        spawnAllay();
        if (livingAllays.size() >= allayValue) main.getArenaHandler().leaveArena(currentGame.user());
    }
    protected void spawnAllay() {
        World w = spawnLocation.getWorld();
        while(true) {
            Location l = box.getRandomLocation();
            if(l.getBlock().isEmpty()) {
                Allay allay = w.spawn(l, Allay.class);
                allay.setCanDuplicate(false);
                allay.startDancing();
                allay.setCanPickupItems(false);
                allay.setGlowing(true);
                livingAllays.add(allay);
                return;
            }
        }
    }

    private Duration stopTime() {
        return Duration.of(System.currentTimeMillis() - currentGame.startTime(), ChronoUnit.MILLIS);
    }

    Player p() {
        UUID user = currentGame.user();
        Player player = Bukkit.getPlayer(user);
        if (player == null) handler.leaveArena(user);
        return player;
    }

    @Override
    public String toString() {
        return "Arena{" +
                "name='" + name + '\'' +
                ", spawnLocation=" + spawnLocation +
                ", box=" + box +
                ", currentGame=" + currentGame +
                ", main=" + main +
                ", spawnScheduler=" + spawnScheduler +
                ", livingAllays=" + livingAllays +
                '}';
    }

    public String getName() {
        return name;
    }
}
