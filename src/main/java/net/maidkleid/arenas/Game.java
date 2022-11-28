package net.maidkleid.arenas;

import net.maidkleid.data.PlayerData;

import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

public record Game(UUID user, long startTime, Difficulty difficulty, String weapon, String arena, AtomicInteger score) {
    public static Game newGame(Arena arena, PlayerData data) {
        return new Game(data.getPlayer(), System.currentTimeMillis(), data.getDifficulty(), data.getWeaponName(), arena.getName(), new AtomicInteger());
    }
}
