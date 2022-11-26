package net.maidkleid.arenas;

import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.jetbrains.annotations.NotNull;

import java.util.LinkedHashMap;
import java.util.Map;

public interface Difficulty {


    long spawnRate();

    int maxSpawns();

    enum Defaults implements Difficulty {
        EASY(2000, 20),
        NORMAL(1000, 10),
        HARD(500, 10);

        private final long spawnRate;

        private final int maxSpawns;

        Defaults(long spawnRate, int maxSpawns) {
            this.spawnRate = spawnRate;
            this.maxSpawns = maxSpawns;
        }

        @Override
        public long spawnRate() {
            return spawnRate;
        }

        @Override
        public int maxSpawns() {
            return maxSpawns;
        }

    }

    public class CustomDifficulty implements Difficulty, ConfigurationSerializable {

        private long spawnRate;
        private int maxSpawns;

        public CustomDifficulty(long spawnRate, int maxSpawns) {
            this.spawnRate = spawnRate;
            this.maxSpawns = maxSpawns;
        }

        public static Difficulty deserialize(Map<String, Object> map) {
            return new CustomDifficulty((long) map.get("spawnRate"), (int) map.get("maxSpawns"));
        }

        public void setSpawnRate(long spawnRate) {
            this.spawnRate = spawnRate;
        }

        public void setMaxSpawns(int maxSpawns) {
            this.maxSpawns = maxSpawns;
        }

        @Override
        public long spawnRate() {
            return spawnRate;
        }

        @Override
        public int maxSpawns() {
            return maxSpawns;
        }

        @Override
        public @NotNull Map<String, Object> serialize() {
            LinkedHashMap<String, Object> map = new LinkedHashMap<>();
            map.put("spawnRate", spawnRate);
            map.put("maxSpawns", maxSpawns);
            return map;
        }
    }
}
