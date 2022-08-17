package net.maidkleid.arenas;

import net.maidkleid.utils.Variablen;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class Arena {

    private final Player player;
    private final Location spawnLocation;
    private final Location boxLocation;
    private final Location boxLocationTwo;

    public Arena(Player player, Location spawnLocation, Location boxLocationOne, Location boxLocationTwo){
        this.player = player;
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
