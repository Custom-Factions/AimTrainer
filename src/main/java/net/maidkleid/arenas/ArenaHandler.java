package net.maidkleid.arenas;

import it.unimi.dsi.fastutil.Hash;
import net.maidkleid.AimTrainerMain;
import net.maidkleid.arenas.Arena;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public class ArenaHandler {

    private final HashMap<UUID, Arena> playerArenaHandler = new HashMap<>();
    private final ArrayList<Arena> freeArenas = new ArrayList<>();



    AimTrainerMain main;



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


            Arena arena1 = new Arena(main, s, spawnLocation, locationA, locationB);

            arenaList.add(arena1);
        });

        freeArenas.addAll(arenaList);
    }

    private Location locationFromConfigSection(ConfigurationSection section, World world) {
        double x = section.getDouble("x");
        double z = section.getDouble("y");
        double y = section.getDouble("z");

        double yaw = section.getDouble("yaw");
        double pitch = section.getDouble("pitch");

        return new Location(world,x,y,z,(float) yaw,(float) pitch);

    }






    public @Nullable Arena joinArena(Player player) {
        if (freeArenas.isEmpty()) return null;
        player.sendMessage("§6Du bist der Arena erfolgreich §2beigetreten!");
        player.sendMessage(player.getName());
        Arena arena = freeArenas.get(0);
        freeArenas.remove(0);
        playerArenaHandler.put(player.getUniqueId(), arena);
        arena.init(player);
        return arena;
    }

    public @Nullable Arena leaveArena(Player player) {
        player.sendMessage("§6Du hast die Arena erfolgreich §4verlassen!");
        Arena arena = playerArenaHandler.get(player.getUniqueId());
        if (arena == null) return null;
        arena.unInit();
        freeArenas.add(arena);
        playerArenaHandler.remove(player.getUniqueId(), arena);

        return arena;
    }





}