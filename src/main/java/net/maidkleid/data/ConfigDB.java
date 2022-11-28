package net.maidkleid.data;

import net.maidkleid.AimTrainerMain;
import net.maidkleid.arenas.Game;
import org.bukkit.configuration.file.YamlConfiguration;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Objects;
import java.util.UUID;

public class ConfigDB implements DataBase {

    private final HashMap<UUID, PlayerData> dataHashMap = new HashMap<>();
    private final File playerDataPath;
    private final File gameStatsPath;

    public ConfigDB(AimTrainerMain main) {
        playerDataPath = new File(main.getDataFolder() + File.separator + "playerdata");
        playerDataPath.mkdir(); //ignored
        gameStatsPath = new File(main.getDataFolder() + File.separator + "gamestats");
        gameStatsPath.mkdir();
    }

    @Override
    public void save(PlayerData playerData) {
        //AimTrainerMain.getPlugin(AimTrainerMain.class).getLogger().info("save playerData: " + playerData);
        YamlConfiguration yamlConfiguration = new YamlConfiguration();
        yamlConfiguration.set("data", playerData);
        try {
            yamlConfiguration.save(getDataFile(playerData.getPlayer()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public PlayerData getData(UUID uuid) {
        PlayerData playerData = dataHashMap.get(uuid);
        if (playerData == null) {
            File dataFile = getDataFile(uuid);
            if (!dataFile.exists()) playerData = new PlayerData(uuid).setDb(this);
            else playerData = fromFile(dataFile);
            dataHashMap.put(playerData.getPlayer(), playerData);
        }
        return playerData;
    }

    @Override
    public void save(Game game) {
        File file = new File(gameStatsPath + File.separator + game.user() + ".log");
        try {
            FileWriter fileWriter = new FileWriter(file, true);
            fileWriter.write(game + "\n");
            fileWriter.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @NotNull
    private File getDataFile(UUID uuid) {
        return new File(playerDataPath + File.separator + uuid + ".yml");
    }

    private PlayerData fromFile(File file) {
        return ((PlayerData) Objects.requireNonNull(YamlConfiguration.loadConfiguration(file).get("data"))).setDb(this);
    }
}
