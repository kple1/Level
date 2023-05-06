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
import java.util.UUID;

public class PlayerData {

    public static YamlConfiguration config;
    public static YamlConfiguration eventConfig;
    public static File playerFile;

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

}
