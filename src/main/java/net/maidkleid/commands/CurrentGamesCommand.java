package net.maidkleid.commands;

import net.maidkleid.AimTrainerMain;
import net.maidkleid.arenas.ArenaHandler;
import net.maidkleid.arenas.Game;
import net.maidkleid.utils.Messages;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.List;

public class CurrentGamesCommand implements CommandExecutor, TabCompleter {

    private final AimTrainerMain main;

    public CurrentGamesCommand(AimTrainerMain main) {
        this.main = main;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        ArenaHandler arenaHandler = main.getArenaHandler();
        List<Game> currentGames = arenaHandler.getCurrentGames();
        StringBuilder b = new StringBuilder();
        if (!currentGames.isEmpty()) b.append(Messages.PREFIX + " Current games: \n");
        else b.append(Messages.PREFIX + " No current games \n");
        for (Game game : currentGames) b.append(formatGame(game)).append("\n");
        b.append("Empty arenas: ").append(arenaHandler.getFreeArenas());
        sender.sendMessage(String.valueOf(b));
        return false;
    }

    private String formatGame(Game game) {
        Duration since = Duration.of(System.currentTimeMillis() - game.startTime(), ChronoUnit.MILLIS);
        return "Arena: " + game.arena() + "\n" +
                "Player: " + game.p() + "\n" +
                "Difficulty: " + game.difficulty() + "\n" +
                "Weapon " + game.weapon() + "\n" +
                "Since: " +
                ((since.toHours() == 0) ? "" : since.toHours() + "h ") +
                ((since.toMinutesPart() == 0) ? "" : since.toMinutesPart() + "min ") +
                ((since.toSecondsPart() == 0) ? "" : since.toSecondsPart() + "s");
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        return List.of();
    }
}
