package net.maidkleid.data;

import net.maidkleid.AimTrainerMain;
import org.bukkit.configuration.file.YamlConfiguration;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.UUID;

public class ConfigDB implements DataBase {

    private final HashMap<UUID, PlayerData> dataHashMap = new HashMap<>();
    private final File dataPath;

    public ConfigDB(AimTrainerMain main) {
        dataPath = new File(main.getDataFolder() + File.separator + "playerdata");
        dataPath.mkdir(); //ignored
    }

    @Override
    public void save(PlayerData playerData) {

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

    @NotNull
    private File getDataFile(UUID uuid) {
        return new File(dataPath + File.separator + uuid + ".yml");
    }

    private PlayerData fromFile(File file) {
        return (PlayerData) YamlConfiguration.loadConfiguration(file).get("data");
    }
}
