package net.maidkleid.data;

import java.util.UUID;

public interface DataBase {

    void save(PlayerData playerData);

    PlayerData getData(UUID uuid);

}
