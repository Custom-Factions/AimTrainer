package net.maidkleid.utils;

import net.kyori.adventure.text.Component;
import net.maidkleid.arenas.Difficulty;
import net.maidkleid.arenas.Game;
import org.bukkit.ChatColor;

;

public interface Messages {
    String PREFIX = "§7[§bAim§9Trainer§7] ";

    String PURE_PREFIX = "AimTrainer";
    Messages DE = new Messages() {
        @Override
        public Component noPermission() {
            return pF("Dazu hast du keine berechtigung");
        }

        @Override
        public Component onlyPlayer() {
            return pF("Du musst ein Spieler sein!");
        }

        @Override
        public Component teleport() {
            return fP("Du wurdest Teleportiert!");
        }

        @Override
        public Component noArenaFree() {
            return pF("Keine Arena mehr Frei!");
        }

        @Override
        public Component startGame(Game game) {
            return fP("Du hast eine Runde gestartet!\n" +
                    "Deine Waffe: " + game.weapon() + "\n" +
                    "Difficulty: " + game.difficulty());
        }

        @Override
        public Component endGame(Game game) {
            return fP("Die Runde ist zuende!\n" +
                    "Dein Score:" + game.score().get());
        }

        @Override
        public Component setGameDifficulty(Difficulty difficulty) {
            return fP("Ändere Schwierigkeit zu " + difficulty);
        }

        @Override
        public Component setWeapon(String weapon) {
            return fP("Ändere Waffe zu " + weapon);
        }

        @Override
        public Component arenaCloseByHandler() {
            return Messages.pF("die Arena wurde vom \"ArenaHandler\" geschlossen!"); // "Arena was closed by ArenaHandler"
        }
    };

    /**
     * Format prefix
     *
     * @param string
     * @return
     */
    static Component fP(String string) {
        return Component.text(PREFIX + ChatColor.WHITE + string);
    }

    /**
     * ProblemFormat
     *
     * @param string
     * @return
     */
    static Component pF(String string) {
        return Component.text(PURE_PREFIX + ChatColor.DARK_RED + string);
    }

    Component noPermission();

    Component onlyPlayer();

    Component teleport();

    Component noArenaFree();

    Component startGame(Game game);

    Component endGame(Game game);

    Component setGameDifficulty(Difficulty difficulty);

    Component setWeapon(String weapon);

    Component arenaCloseByHandler();
}
