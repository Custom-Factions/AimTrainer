package net.maidkleid.utils;

import org.bukkit.ChatColor;

public interface Messages {
    String PREFIX = "§7[§bAim§9Trainer§7] ";
    Messages DE = new Messages() {
        @Override
        public String noPermission() {
            return PREFIX + ChatColor.RED + "Dazu hast du keine berechtigung";
        }

        @Override
        public String onlyPlayer() {
            return PREFIX + ChatColor.DARK_RED + "Du musst ein Spieler sein!";
        }

        @Override
        public String teleport() {
            return PREFIX + ChatColor.WHITE + "Du wurdest Teleportiert!";
        }
    };

    String noPermission();

    String onlyPlayer();

    String teleport();
}
