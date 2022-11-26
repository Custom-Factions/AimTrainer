package net.maidkleid.commands;

import net.maidkleid.AimTrainerMain;
import net.maidkleid.utils.Messages;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class TeleportSpawnCommand implements CommandExecutor {

    AimTrainerMain main;


    public TeleportSpawnCommand(AimTrainerMain main) {
        this.main = main;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(!(sender instanceof Player player)){

            sender.sendMessage(Messages.DE.onlyPlayer());
            return false;
        }

        if(args.length != 0){
            player.sendMessage(Messages.PREFIX + "§eBenutze /aimtrainer <Arena>");
            return false;
        }
        return false;
    }
}
