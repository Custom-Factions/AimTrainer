package net.maidkleid.data;

import com.google.common.base.Objects;
import net.maidkleid.arenas.Difficulty;
import net.maidkleid.weapons.WeaponTable;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.jetbrains.annotations.NotNull;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;

public class PlayerData implements ConfigurationSerializable {

     private final UUID player;
     private transient DataBase db;
     private String weaponName = WeaponTable.PISTOL.getName();
     private Difficulty difficulty = Difficulty.Defaults.EASY;

     protected PlayerData(UUID uuid) {
          this.player = uuid;
     }

     public static PlayerData deserialize(Map<String, Object> map) {
          PlayerData data = new PlayerData(UUID.fromString(map.get("player").toString()));
          data.weaponName = map.get("weapon").toString();
          Object difficulty = map.get("difficulty");
          if (difficulty instanceof String dif) data.difficulty = Difficulty.Defaults.valueOf(dif);
          else data.difficulty = Difficulty.CustomDifficulty.deserialize((Map<String, Object>) difficulty);
          return data;
     }

     @Override
     public boolean equals(Object o) {
          if (this == o) return true;
          if (o == null || getClass() != o.getClass()) return false;
          PlayerData that = (PlayerData) o;
          return Objects.equal(player, that.player);
     }

     @Override
     public int hashCode() {
          return Objects.hashCode(player);
     }

     protected DataBase getDb() {
          return db;
     }

     protected PlayerData setDb(DataBase db) {
          this.db = db;
          return this;
     }

     public void save() {
          try {
               db.save(this);
          } catch (Exception ignored) {
          }
     }

     public String getWeaponName() {
          return weaponName;
     }

     public void setWeaponName(String weaponName) {
          this.weaponName = weaponName;
     }

     public Difficulty getDifficulty() {
          return difficulty;
     }

     public void setDifficulty(Difficulty difficulty) {
          this.difficulty = difficulty;
     }

     public UUID getPlayer() {
          return player;
     }

     @Override
     public @NotNull Map<String, Object> serialize() {
          LinkedHashMap<String, Object> map = new LinkedHashMap<>();
          map.put("player", player.toString());
          map.put("weapon", weaponName);
          if (difficulty instanceof Difficulty.Defaults difficulty) {
               map.put("difficulty", difficulty.name());
          } else {
               map.put("difficulty", difficulty);
          }
          return map;
     }
}
