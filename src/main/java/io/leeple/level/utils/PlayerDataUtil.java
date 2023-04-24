package io.leeple.level.utils;

import io.leeple.level.Main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.util.UUID;

public class PlayerDataUtil {

    private static YamlConfiguration config;
    private static File playerFile;

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

                playerFile = new File(Main.getPlugin().getUuidFolder(), targetUUID.toString() + ".yml");

                if (!playerFile.exists()) {
                    UUID uuid = player.getUniqueId();
                    Main.getPlugin().createPlayerDefaults(uuid);
                    config = YamlConfiguration.loadConfiguration(playerFile);
                } else {
                    config = YamlConfiguration.loadConfiguration(playerFile);
                }
                return config; //config 변수를 반환하도록 수정
            }
        }
        return null;
    }
}
