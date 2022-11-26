package net.maidkleid.arenas;

import net.maidkleid.AimTrainerMain;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;
import java.util.UUID;

public class ArenaHandler {

    private final HashMap<UUID, Arena> playerArenaHandler = new HashMap<>();
    private final ArrayList<Arena> freeArenas = new ArrayList<>();
    private final ArrayList<Arena> arenaList = new ArrayList<>();


    public ArenaHandler(AimTrainerMain main, FileConfiguration config) {

        ConfigurationSection arenas = config.getConfigurationSection("arenas");
        assert arenas != null;
        arenas.getKeys(false).forEach(s -> {
            ConfigurationSection arena = arenas.getConfigurationSection(s);
            assert arena != null;
            World world = Bukkit.getWorld(Objects.requireNonNull(arena.getString("world")));
            Location spawnLocation = locationFromConfigSection(Objects.requireNonNull(arena.getConfigurationSection("spawnlocation")), world);
            Location locationA = locationFromConfigSection(Objects.requireNonNull(arena.getConfigurationSection("locationA")), world);
            Location locationB = locationFromConfigSection(Objects.requireNonNull(arena.getConfigurationSection("locationB")), world);
            boolean b = arena.getBoolean("buildbarriercage");
            addArena(new Arena(main, s, spawnLocation, locationA, locationB, b));
        });

    }

    private void addArena(Arena arena) {
        if (arenaList.contains(arena)) return;
        arenaList.add(arena);
        freeArenas.add(arena);
    }

    private void removeArena(Arena arena) {
        if (!arenaList.contains(arena)) return;
        if (!freeArenas.contains(arena)) {
            Player player = arena.getPlayer();
            playerArenaHandler.remove(player.getUniqueId());
            player.sendMessage("Arena was closed by ArenaHandler");
            arena.endGame();
        }
        arenaList.remove(arena);
    }


    private Location locationFromConfigSection(ConfigurationSection section, World world) {
        double x = section.getDouble("x");
        double y = section.getDouble("y");
        double z = section.getDouble("z");

        double yaw = section.getDouble("yaw");
        double pitch = section.getDouble("pitch");

        return new Location(world,x,y,z,(float) yaw,(float) pitch);
    }
    public @Nullable Arena joinArena(Player player) {
        //player.sendMessage("You Tried to join");
        if (freeArenas.isEmpty() || playerArenaHandler.containsKey(player.getUniqueId())) return null;
        player.sendMessage("§6Du bist der Arena erfolgreich §2beigetreten!");
        Arena arena = freeArenas.get(0);
        freeArenas.remove(0);
        playerArenaHandler.put(player.getUniqueId(), arena);
        //System.out.println(this + " " + arena);
        arena.startGame(player);
        return arena;
    }

    public @Nullable Arena leaveArena(Player player) {
        Arena arena = playerArenaHandler.get(player.getUniqueId());
        if (arena == null) return null;
        arena.endGame();
        freeArenas.add(arena);
        playerArenaHandler.remove(player.getUniqueId(), arena);
        player.sendMessage("§6Du hast die Arena erfolgreich §4verlassen!" +
                "\nScore: " + arena.getScore());

        return arena;
    }

    public void addKill(Player player) {
        Arena arena = playerArenaHandler.get(player.getUniqueId());
        if (arena != null) arena.addKill();
    }

    @Override
    public String toString() {
        return "ArenaHandler{" +
                "playerArenaHandler=" + playerArenaHandler +
                ", freeArenas=" + freeArenas +
                ", arenaList=" + arenaList +
                '}';
    }
}