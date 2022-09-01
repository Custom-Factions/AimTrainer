package net.maidkleid.arenas;

import net.maidkleid.AimTrainerMain;
import net.maidkleid.utils.Variablen;
import net.maidkleid.weaponapi.utils.WeaponItemMidLevelUtils;
import net.maidkleid.weaponapi.weaponlib.WeaponProvider;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Allay;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.checkerframework.checker.units.qual.A;
import org.jetbrains.annotations.NotNull;

import java.nio.Buffer;
import java.util.ArrayList;
import java.util.Random;

public class Arena {


   
    private Player player;
    private final String name;
    private final Location spawnLocation;

    private final Box box;
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
        spawnScheduler = Bukkit.getScheduler().scheduleSyncRepeatingTask(main, this::checkAllays, 1,20);
        //ItemStack pistol = WeaponItemMidLevelUtils.getWeaponItem(WeaponProvider.getWeaponID(WeaponProvider.PISTOL.getName()), 1);
        int weaponID = WeaponProvider.getWeaponID(WeaponProvider.PISTOL.getName());
        ItemStack weaponItem = WeaponItemMidLevelUtils.getWeaponItem(WeaponProvider.getLowestCustomModelDataID(weaponID), 1);
        player.getInventory().setItem(0,weaponItem);
        
    }

    protected void endGame() {
        player.teleport(oldLocation);
        main.getLogger().info(player.getName() + " has end his game: " + name + " score: " + score);
        Bukkit.getScheduler().cancelTask(spawnScheduler);
        livingAllays.removeIf(allay -> {
            allay.remove();
            return true;
        });
    }

    protected void addKill() {
        score++;
    }

    protected void checkAllays() {
         livingAllays.removeIf(Entity::isDead);
         spawnAllay();
         if(livingAllays.size() >= 15) main.getArenaHandler().leaveArena(player);
    }
    protected void spawnAllay() {
        World w = spawnLocation.getWorld();
        while(true) {
            Location l = box.getRandomLocation();
            if(l.getBlock().isEmpty()) {
                Allay allay = w.spawn(l, Allay.class);
                allay.setCanDuplicate(false);
                allay.startDancing();
                livingAllays.add(allay);
                return;
            }
        }
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
