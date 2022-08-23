package net.maidkleid.arenas;

import net.maidkleid.AimTrainerMain;
import net.maidkleid.utils.Variablen;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class Arena {

    

   
    private Player player;
    private final String name;
    private final Location spawnLocation;
    private final Location boxLocation;
    private final Location boxLocationTwo;
    private final AimTrainerMain main;
    private Location oldLocation;





    public Arena(AimTrainerMain main, String name, Location spawnLocation, Location boxLocationOne, Location boxLocationTwo){
        this.main = main;
        this.name = name;
        this.spawnLocation = spawnLocation;
        this.boxLocation = boxLocationOne;
        this.boxLocationTwo = boxLocationTwo;
    }

    public void init(Player player){

        oldLocation = player.getLocation();

        this.player = player;

        this.player.teleport(spawnLocation);
        this.player.sendMessage(Variablen.teleport);

    }

    public void unInit() {
        player.sendMessage("Java ist gut für die Menscheheit!");
        player.teleport(oldLocation);

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
