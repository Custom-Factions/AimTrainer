package net.maidkleid;

import net.maidkleid.commands.TeleportSpawnCommand;
import net.maidkleid.listeners.ClickListener;
import net.maidkleid.listeners.ConnectionListener;
import net.maidkleid.listeners.InventoryClickListener;
import net.maidkleid.utils.UtilConfig;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class AimTrainerMain extends JavaPlugin {



    private UtilConfig configUtils;



    @Override
    public void onEnable() {

        configUtils = new UtilConfig(this);



        //commands
        getCommand("aimtrainer").setExecutor(new TeleportSpawnCommand());


        //listeners
        Bukkit.getPluginManager().registerEvents(new ConnectionListener(this), this);
        Bukkit.getPluginManager().registerEvents(new ClickListener(this), this);
        Bukkit.getPluginManager().registerEvents(new InventoryClickListener(this), this);

    }

    public UtilConfig getJoinItems() {
        return configUtils;
    }

}
