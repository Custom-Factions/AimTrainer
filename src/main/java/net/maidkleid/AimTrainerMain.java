package net.maidkleid;

import net.maidkleid.arenas.ArenaHandler;
import net.maidkleid.arenas.Difficulty;
import net.maidkleid.commands.TeleportSpawnCommand;
import net.maidkleid.commands.setMaxAllaySpawnCommand;
import net.maidkleid.data.ConfigDB;
import net.maidkleid.data.DataBase;
import net.maidkleid.data.PlayerData;
import net.maidkleid.listeners.*;
import net.maidkleid.utils.UtilConfig;
import org.bukkit.Bukkit;
import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.bukkit.plugin.java.JavaPlugin;

public final class AimTrainerMain extends JavaPlugin {



    private UtilConfig configUtils;
    private ArenaHandler arenaHandler;

    private DataBase dataBase;



    @Override
    public void onEnable() {
        ConfigurationSerialization.registerClass(Difficulty.CustomDifficulty.class);
        ConfigurationSerialization.registerClass(PlayerData.class);

        getConfig().options().copyDefaults();
        saveDefaultConfig();
        dataBase = new ConfigDB(this);


        configUtils = new UtilConfig(this);

        arenaHandler = new ArenaHandler(this, getConfig());


        //commands
        getCommand("aimtrainer").setExecutor(new TeleportSpawnCommand(this));
        getCommand("allayspawn").setExecutor(new setMaxAllaySpawnCommand(this));


        //listeners
        Bukkit.getPluginManager().registerEvents(new ConnectionListener(this), this);
        Bukkit.getPluginManager().registerEvents(new ClickListener(), this);
        Bukkit.getPluginManager().registerEvents(new InventoryClickListener(), this);
        Bukkit.getPluginManager().registerEvents(new ShootHitListener(), this);
        Bukkit.getPluginManager().registerEvents(new InteractListener(), this);

    }

    public UtilConfig getJoinItems() {
        return configUtils;
    }


    public ArenaHandler getArenaHandler() {
        return arenaHandler;
    }

    public DataBase getDataBase() {
        return dataBase;
    }
}
