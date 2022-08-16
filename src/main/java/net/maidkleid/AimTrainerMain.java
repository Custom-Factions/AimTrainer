package net.maidkleid;

import net.maidkleid.commands.TeleportSpawnCommand;
import net.maidkleid.listeners.ClickListener;
import net.maidkleid.listeners.ConnectionListener;
import net.maidkleid.utils.Items;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.plugin.java.JavaPlugin;

public final class AimTrainerMain extends JavaPlugin {



    private Items joinItems;
    private Items inventories;


    @Override
    public void onEnable() {

        joinItems = new Items(this);
        inventories = new Items(this);



        //commands
        getCommand("aimtrainer").setExecutor(new TeleportSpawnCommand());


        //listeners
        Bukkit.getPluginManager().registerEvents(new ConnectionListener(this), this);
        Bukkit.getPluginManager().registerEvents(new ClickListener(this), this);

    }

    public Items getJoinItems() {
        return joinItems;
    }

    public Items getInventories() {
        return inventories;
    }
}
