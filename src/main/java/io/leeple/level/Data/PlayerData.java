package io.leeple.level.Data;

import io.leeple.level.Main;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.file.YamlConfiguration;
import java.io.File;

public class PlayerData {

    public static YamlConfiguration config = new YamlConfiguration();
    public static File playerFile = new File(Main.getPlugin().getUuidFolder(), "plugins/Lotto/UUIDs/");

    public static YamlConfiguration getPlayerConfig(OfflinePlayer player) {
        playerFile = new File(Main.getPlugin().getUuidFolder(), "plugins/Level/UUIDs/" + player.getUniqueId().toString() + ".yml");

        if (!playerFile.exists()) {
            Main.getPlugin().createPlayerDefaults();
        }

        config = YamlConfiguration.loadConfiguration(playerFile);
        return config;
    }
}
