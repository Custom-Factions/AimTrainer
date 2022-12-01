package net.maidkleid;

import net.maidkleid.arenas.ArenaHandler;
import net.maidkleid.arenas.Difficulty;
import net.maidkleid.commands.CurrentGamesCommand;
import net.maidkleid.data.ConfigDB;
import net.maidkleid.data.DataBase;
import net.maidkleid.data.PlayerData;
import net.maidkleid.listeners.*;
import net.maidkleid.utils.UtilConfig;
import net.maidkleid.weaponapi.weaponlib.WeaponProvider;
import net.maidkleid.weapons.WeaponTable;
import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

public final class AimTrainerMain extends JavaPlugin {


    private UtilConfig configUtils;
    private ArenaHandler arenaHandler;

    private static DataBase dataBase;

    private static final ArrayList<String> allActivatedWeapons = new ArrayList<>();

    public static List<String> getActivatedWeapons() {
        return List.copyOf(allActivatedWeapons);
    }

    public UtilConfig getJoinItems() {
        return configUtils;
    }


    public ArenaHandler getArenaHandler() {
        return arenaHandler;
    }

    public static DataBase getDataBase() {
        return dataBase;
    }

    @Override
    public void onEnable() {
        ConfigurationSerialization.registerClass(Difficulty.CustomDifficulty.class);
        ConfigurationSerialization.registerClass(PlayerData.class);

        getLogger().info("Extra generated Weapons: " + WeaponTable.weaponTableOnlyWeaponsNames);

        //getConfig().options().copyDefaults();
        saveDefaultConfig();
        List<String> allWeaponNames = WeaponProvider.getAllWeaponNames();
        if (!getConfig().contains("weapons"))
            for (String w : allWeaponNames)
                getConfig().set("weapons." + w, false);
        ConfigurationSection weapons = getConfig().getConfigurationSection("weapons");

        for (String w : allWeaponNames) {
            assert weapons != null;
            if (!weapons.contains(w)) weapons.set(w, false);
            if (weapons.getBoolean(w)) allActivatedWeapons.add(w);
        }
        saveConfig();

        dataBase = new ConfigDB(this);

        configUtils = new UtilConfig(this);

        arenaHandler = new ArenaHandler(this, getConfig());


        //commands
        getCommand("currentGames").setExecutor(new CurrentGamesCommand(this));
        //getCommand("aimtrainer").setExecutor(new TeleportSpawnCommand(this));
        //getCommand("allayspawn").setExecutor(new setMaxAllaySpawnCommand(this));


        //listeners
        Bukkit.getPluginManager().registerEvents(new ConnectionListener(this), this);
        Bukkit.getPluginManager().registerEvents(new ClickListener(), this);
        Bukkit.getPluginManager().registerEvents(new InventoryClickListener(), this);
        Bukkit.getPluginManager().registerEvents(new ShootHitListener(), this);
        Bukkit.getPluginManager().registerEvents(new InteractListener(), this);

    }
}
