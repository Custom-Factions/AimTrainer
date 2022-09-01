package net.maidkleid;

import net.maidkleid.commands.TeleportSpawnCommand;
import net.maidkleid.listeners.ClickListener;
import net.maidkleid.listeners.ConnectionListener;
import net.maidkleid.listeners.InventoryClickListener;
import net.maidkleid.arenas.ArenaHandler;
import net.maidkleid.listeners.ShootHitListener;
import net.maidkleid.utils.UtilConfig;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class AimTrainerMain extends JavaPlugin {



    private UtilConfig configUtils;
    private ArenaHandler arenaHandler;




    @Override
    public void onEnable() {


        getConfig().options().copyDefaults();
        saveDefaultConfig();

        configUtils = new UtilConfig(this);

        arenaHandler = new ArenaHandler(this, getConfig());



        //commands
        getCommand("aimtrainer").setExecutor(new TeleportSpawnCommand(this));


        //listeners
        Bukkit.getPluginManager().registerEvents(new ConnectionListener(this), this);
        Bukkit.getPluginManager().registerEvents(new ClickListener(this), this);
        Bukkit.getPluginManager().registerEvents(new InventoryClickListener(this), this);
        Bukkit.getPluginManager().registerEvents(new ShootHitListener(), this);

    }

    public UtilConfig getJoinItems() {
        return configUtils;
    }


    public ArenaHandler getArenaHandler() {
        return arenaHandler;
    }
}
