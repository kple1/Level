package io.leeple.level.command;

import io.leeple.level.Main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.UUID;

public class LevelInfo implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if (sender instanceof Player player) {
            if (args.length > 0) {
                String playerName = args[0];
                Player targetPlayer = Bukkit.getPlayerExact(playerName);
                UUID targetUUID;

                // 플레이어가 현재 온라인 상태인 경우, 온라인 플레이어의 UUID를 사용
                if (targetPlayer != null) {
                    targetUUID = targetPlayer.getUniqueId();
                } else { // 오프라인 플레이어의 경우, 플레이어 데이터 파일에서 UUID를 가져옴
                    OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(playerName);
                    if (!offlinePlayer.hasPlayedBefore()) {
                        sender.sendMessage(ChatColor.RED + "해당 플레이어가 한 번도 접속한 적이 없습니다.");
                        return true;
                    }
                    targetUUID = offlinePlayer.getUniqueId();
                }
                // 해당 UUID의 폴더에서 플레이어 데이터 파일을 가져옴
                File playerFile = new File(Main.getPlugin().getUuidFolder(), targetUUID.toString() + ".yml");

                // 해당 플레이어의 데이터 파일이 없는 경우 초기값 생성
                if (!playerFile.exists()) {
                    UUID uuid = player.getUniqueId();
                    Main.getPlugin().createPlayerDefaults(uuid);

                    String LEVEL = "[ &6" + player + "&f" + "님의 레벨정보 ]";

                    // 플레이어의 Level 정보를 알려줌
                    YamlConfiguration playerConfig = YamlConfiguration.loadConfiguration(playerFile);
                    String level_info = playerConfig.getString("Level", "");
                    String level_exp = playerConfig.getString("EXP", "");
                    player.sendMessage("");
                    player.sendMessage(LEVEL);
                    player.sendMessage("&6" + player + "&f" + "님의 레벨: " + "&a" + level_info);
                    player.sendMessage("&6" + player + "&f" + "님의 경험치: " + "&a" + level_exp);
                    player.sendMessage("");
                }
            }
        }
        return false;
    }
}