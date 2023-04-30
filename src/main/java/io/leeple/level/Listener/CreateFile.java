package io.leeple.level.Listener;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import static io.leeple.level.Main.plugin;

public class CreateFile implements Listener {

    private File uuidFolder;

    public CreateFile() {
        // 플러그인 데이터 폴더 내에 uuid 폴더 생성
        uuidFolder = new File(plugin.getDataFolder(), "UUIDs");
        if (!uuidFolder.exists() && !uuidFolder.mkdirs()) {
            // 폴더 생성에 실패한 경우 예외 처리
            throw new RuntimeException("Failed to create directory: " + uuidFolder.getAbsolutePath());
        }
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        UUID uuid = player.getUniqueId();
        File playerFile = new File(uuidFolder, uuid.toString() + ".yml");
        if (!playerFile.exists()) {
            try {
                playerFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        YamlConfiguration playerConfig = YamlConfiguration.loadConfiguration(playerFile);

        playerConfig.addDefault("Level", "1");
        playerConfig.addDefault("EXP", "0/10");
        playerConfig.options().copyDefaults(true);
        try {
            playerConfig.save(playerFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
