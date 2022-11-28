package net.maidkleid.data;

import net.maidkleid.arenas.Game;

import java.util.UUID;

public interface DataBase {

    void save(PlayerData playerData);

    PlayerData getData(UUID uuid);

    void save(Game game);
}
