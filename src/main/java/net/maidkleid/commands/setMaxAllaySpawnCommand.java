package net.maidkleid.commands;

import net.maidkleid.AimTrainerMain;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class setMaxAllaySpawnCommand implements CommandExecutor {

    private final AimTrainerMain main;
    public setMaxAllaySpawnCommand(AimTrainerMain main) {
        this.main = main;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String s, @NotNull String[] args) {



        if (!(sender instanceof Player player)) {
            sender.sendMessage("Kein Spieler!");
            return false;
        }

        if (!player.hasPermission("changeAllay.spawn")) {
            player.sendMessage("keine rechte!");
            return false;
        }


        try {
            if (args.length != 1) {
                player.sendMessage("Bitte versuche /setmaxallays [value]");
                return false;
            }

        } catch (Exception e) {

            player.sendMessage("Bitte gebe nur einen zahlenwert an!");
        }



        int value = Integer.parseInt(args[0]);

        FileConfiguration config = main.getConfig();

        config.set("allayspawnmax", value);
        main.saveConfig();
        player.sendMessage("Der AllayMax wurde auf " + value + " gesetzt!");


        return false;
    }
}
