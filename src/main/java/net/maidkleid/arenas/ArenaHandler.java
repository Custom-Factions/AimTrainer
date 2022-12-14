package net.maidkleid.arenas;

import net.maidkleid.AimTrainerMain;
import net.maidkleid.utils.Messages;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.Nullable;

import java.util.*;

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
            Player player = arena.p();
            playerArenaHandler.remove(player.getUniqueId());
            player.sendMessage(Messages.DE.arenaCloseByHandler());
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
        //player.sendMessage("??6Du bist der Arena erfolgreich ??2beigetreten!");
        Arena arena = freeArenas.get(0);
        freeArenas.remove(0);
        playerArenaHandler.put(player.getUniqueId(), arena);
        //System.out.println(this + " " + arena);
        arena.startGame(player, this);
        return arena;
    }

    public @Nullable Game leaveArena(UUID user) {
        Arena arena = playerArenaHandler.get(user);
        if (arena == null) return null;
        freeArenas.add(arena);
        playerArenaHandler.remove(user, arena);
        //player.sendMessage("??6Du hast die Arena erfolgreich ??4verlassen!" +
        //        "\nScore: " + arena.getScore());
        Game game = arena.endGame();
        AimTrainerMain.getDataBase().save(game);
        return game;
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

    public List<Game> getCurrentGames() {
        ArrayList<Game> games = new ArrayList<>();
        playerArenaHandler.forEach((uuid, arena) -> games.add(arena.currentGame()));
        return List.copyOf(games);
    }

    public List<String> getFreeArenas() {
        String[] arenas = new String[freeArenas.size()];
        for (int i = 0; i < freeArenas.size(); i++) arenas[i] = freeArenas.get(i).getName();
        return List.of(arenas);
    }
}