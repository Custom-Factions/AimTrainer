package net.maidkleid.arenas;

import net.maidkleid.AimTrainerMain;
import net.maidkleid.utils.Variablen;
import net.maidkleid.weaponapi.utils.WeaponItemMidLevelUtils;
import net.maidkleid.weaponapi.weaponlib.WeaponProvider;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Allay;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;


import java.io.File;
import java.nio.Buffer;
import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Random;

public class Arena {


   
    private Player player;
    private final String name;
    private final Location spawnLocation;

    private final Box box;

    private long startTime;
    private final AimTrainerMain main;
    private Location oldLocation;

    private int spawnScheduler;

    private final ArrayList<Allay> livingAllays;


    private int score;



     Arena(AimTrainerMain main, String name, Location spawnLocation, Location boxLocationOne, Location boxLocationTwo){
        this.main = main;
        this.name = name;
        this.spawnLocation = spawnLocation;
        this.box = new Box(boxLocationOne,boxLocationTwo);
        score = 0;
        livingAllays = new ArrayList<>();
    }

    protected void startGame(Player player){
        score = 0;
        oldLocation = player.getLocation();
        main.getLogger().info(player.getName() + " has started the game: " + name);
        this.player = player;
        this.player.teleport(spawnLocation);
        this.player.sendMessage(Variablen.teleport);
        startTime();
        spawnScheduler = Bukkit.getScheduler().scheduleSyncRepeatingTask(main, this::checkAllays, 1,20);
        //ItemStack pistol = WeaponItemMidLevelUtils.getWeaponItem(WeaponProvider.getWeaponID(WeaponProvider.PISTOL.getName()), 1);
        int weaponID = WeaponProvider.getWeaponID(WeaponProvider.PISTOL.getName());
        ItemStack weaponItem = WeaponItemMidLevelUtils.getWeaponItem(WeaponProvider.getLowestCustomModelDataID(weaponID), 1);
        player.getInventory().setItem(0,weaponItem);
        
    }

    protected void endGame() {
        player.teleport(oldLocation);
        main.getLogger().info(player.getName() + " has end his game: " + name + " score: " + score + "\n Das Spiel ging: " + stopTime().toMillis()/1000 + " sekunden lang!");
        Bukkit.getScheduler().cancelTask(spawnScheduler);
        int weaponID = WeaponProvider.getWeaponID(WeaponProvider.PISTOL.getName());
        player.getInventory().removeItem(WeaponItemMidLevelUtils.getWeaponItem(WeaponProvider.getLowestCustomModelDataID(weaponID), 1));
        livingAllays.removeIf(allay -> {
            allay.remove();
            return true;
        });
    }

    protected void addKill() {
        score++;
    }

    protected void checkAllays() {


        FileConfiguration config = main.getConfig();


        int allayValue = config.getInt("allayspawnmax");

         livingAllays.removeIf(Entity::isDead);
         spawnAllay();
         if(livingAllays.size() >= allayValue) main.getArenaHandler().leaveArena(player);
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

    private void startTime() {

         startTime = System.currentTimeMillis();
    }
    private Duration stopTime() {

        return Duration.of(System.currentTimeMillis() - startTime, ChronoUnit.MILLIS);
    }



    public int getScore() {
        return score;
    }

    public Player getPlayer() {
        return player;
    }

    public Location getSpawnLocation() {
        return spawnLocation.clone();
    }
}
