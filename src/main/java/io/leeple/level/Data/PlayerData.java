package io.leeple.level.Data;

import io.leeple.level.Main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

public class PlayerData {

    public static YamlConfiguration config;
    public static File playerFile;
    public static YamlConfiguration eventConfig;

    public static YamlConfiguration Config(String[] args, CommandSender sender) {
        if (sender instanceof Player player) {
            if (args.length > 0) {
                String playerName = args[0];
                Player targetPlayer = Bukkit.getPlayerExact(playerName);
                UUID targetUUID;

                if (targetPlayer != null) {
                    targetUUID = targetPlayer.getUniqueId();
                } else {
                    OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(playerName);
                    if (!offlinePlayer.hasPlayedBefore()) {
                        sender.sendMessage(ChatColor.RED + "해당 플레이어가 한 번도 접속한 적이 없습니다.");
                    }
                    targetUUID = offlinePlayer.getUniqueId();
                }

                playerFile = new File(Main.getPlugin().getUuidFolder(), "plugins/Level/UUIDs/" + targetUUID.toString() + ".yml");

                if (!playerFile.exists()) {
                    Main.getPlugin().createPlayerDefaults(player);
                    config = YamlConfiguration.loadConfiguration(playerFile);
                } else {
                    config = YamlConfiguration.loadConfiguration(playerFile);
                }
                return config;
            }
        }
        return null;
    }

    @EventHandler
    public static YamlConfiguration ClickConfig(InventoryClickEvent event) {
        if (event.getView().getTitle().equals("레벨보상")) {
            Player player = (Player) event.getWhoClicked();
            UUID targetUUID = player.getUniqueId();

            playerFile = new File(Main.getPlugin().getUuidFolder(), "plugins/Level/UUIDs/" + targetUUID.toString() + ".yml");

            if (!playerFile.exists()) {
                Main.getPlugin().createPlayerDefaults(player);
            }
            eventConfig = YamlConfiguration.loadConfiguration(playerFile);
        }
        return eventConfig;
    }

    public static void saveEventYamlConfiguration() {
        try {
            eventConfig.save(playerFile);
            // 저장 성공 메시지 등을 추가로 처리할 수 있습니다.
        } catch (IOException e) {
            e.printStackTrace();
            // 저장 실패 처리를 할 수 있습니다.
        }
    }
}
