package net.maidkleid.utils;

import net.maidkleid.AimTrainerMain;
import net.maidkleid.arenas.Arena;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class ArenaHandler {

    AimTrainerMain main;

    public ArenaHandler() {

    }

    ArrayList<Arena> arenaList = new ArrayList<>();




    public ArenaHandler(AimTrainerMain main, FileConfiguration config) {
        this.main = main;
        ConfigurationSection arenas = config.getConfigurationSection("arenas");
        arenas.getKeys(false).forEach(s -> {
            ConfigurationSection arena = arenas.getConfigurationSection(s);
            World world = Bukkit.getWorld(arena.getString("world"));
            Location spawnLocation = locationFromConfigSection(arena.getConfigurationSection("spawnlocation"), world);
            Location locationA = locationFromConfigSection(arena.getConfigurationSection("locationA"), world);
            Location locationB = locationFromConfigSection(arena.getConfigurationSection("locationB"), world);


            arenaList.add(new Arena(s, spawnLocation, locationA, locationB));
        });


    }

    private Location locationFromConfigSection(ConfigurationSection section, World world) {
        double x = section.getDouble("x");
        double z = section.getDouble("y");
        double y = section.getDouble("z");

        double yaw = section.getDouble("yaw");
        double pitch = section.getDouble("pitch");

        return new Location(world,x,y,z,(float) yaw,(float) pitch);

    }






}