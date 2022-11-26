package net.maidkleid.utils;

import org.bukkit.ChatColor;

public interface Messages {
    String PREFIX = "§7[§bAim§9Trainer§7] ";

    String PURE_PREFIX = "AimTrainer";
    Messages DE = new Messages() {
        @Override
        public String noPermission() {
            return pF("Dazu hast du keine berechtigung");
        }

        @Override
        public String onlyPlayer() {
            return pF("Du musst ein Spieler sein!");
        }

        @Override
        public String teleport() {
            return fP("Du wurdest Teleportiert!");
        }

        @Override
        public String noArenaFree() {
            return pF("Keine Arena mehr Frei!");
        }
    };

    String noPermission();

    String onlyPlayer();

    String teleport();

    /**
     * Format prefix
     *
     * @param string
     * @return
     */
    static String fP(String string) {
        return PREFIX + ChatColor.WHITE + string;
    }

    /**
     * ProblemFormat
     *
     * @param string
     * @return
     */
    static String pF(String string) {
        return PURE_PREFIX + ChatColor.DARK_RED + string;
    }

    String noArenaFree();
}
