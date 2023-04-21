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

    public YamlConfiguration Config(String[] args, CommandSender sender, Player player) {
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

        File playerFile = new File(Main.getPlugin().getUuidFolder(), targetUUID.toString() + ".yml");

        if (!playerFile.exists()) {
            UUID uuid = player.getUniqueId();
            Main.getPlugin().createPlayerDefaults(uuid);
            config = YamlConfiguration.loadConfiguration(playerFile);
        }
        return null;
    }

    public static YamlConfiguration GetConfig() {
        return config;
    }
}