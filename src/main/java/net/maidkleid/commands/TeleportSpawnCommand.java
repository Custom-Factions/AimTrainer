package net.maidkleid.commands;

import net.maidkleid.AimTrainerMain;
import net.maidkleid.arenas.Arena;
import net.maidkleid.utils.Variablen;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;

public class TeleportSpawnCommand implements CommandExecutor {

    AimTrainerMain main;

    public TeleportSpawnCommand(AimTrainerMain main) {
        this.main = main;
    }

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






        arenaOne.init();




        return false;
    }
}
