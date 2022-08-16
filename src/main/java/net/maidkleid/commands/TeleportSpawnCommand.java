package net.maidkleid.commands;

import net.maidkleid.arenas.Arena;
import net.maidkleid.utils.Variablen;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class TeleportSpawnCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if(!(sender instanceof Player player)){
            sender.sendMessage(Variablen.onlyPlayer);
            return false;
        }

        if(args.length != 0){
            player.sendMessage(Variablen.prefix + "§eBenutze /aimtrainer <Arena>");
            return false;
        }


        World worldSpawn = player.getWorld();

        Location spawnLocation = new Location(worldSpawn, 100, 70 , 100, player.getLocation().getYaw(), player.getLocation().getPitch());
        Location boxOneLocation = new Location(worldSpawn, 98, 70, 95);
        Location boxTwoLocation = new Location(worldSpawn, 120, 70, 105);


        Arena arenaOne = new Arena(player, spawnLocation, boxOneLocation, boxTwoLocation);

        arenaOne.init();

        return false;
    }
}
