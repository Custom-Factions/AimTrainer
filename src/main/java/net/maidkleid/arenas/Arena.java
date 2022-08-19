package net.maidkleid.arenas;

import net.maidkleid.utils.Variablen;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class Arena {

    private Player player;
    private final String name;
    private final Location spawnLocation;
    private final Location boxLocation;
    private final Location boxLocationTwo;

    public Arena(String name, Location spawnLocation, Location boxLocationOne, Location boxLocationTwo){
        this.name = name;
        this.spawnLocation = spawnLocation;
        this.boxLocation = boxLocationOne;
        this.boxLocationTwo = boxLocationTwo;
    }

    public void init(){
        player.teleport(spawnLocation);
        player.sendMessage(Variablen.teleport);
    }

    public Player getPlayer() {
        return player;
    }

    public Location getSpawnLocation() {
        return spawnLocation.clone();
    }

    public Location getBoxLocation() {
        return boxLocation.clone();
    }

    public Location getBoxLocationTwo() {
        return boxLocationTwo.clone();
    }
}
