package io.leeple.level.command;

import io.leeple.level.Main;
import io.leeple.level.utils.ColorUtils;
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


public class LevelReset implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (sender instanceof Player player) {

            if (args.length == 0) {
                sender.sendMessage("/레벨 초기화 " + "플레이어 닉네임");
            }
            Main plugin = Main.getPlugin();
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
            }

            // 플레이어의 Level과 EXP 정보를 0으로 설정
            YamlConfiguration playerConfig = YamlConfiguration.loadConfiguration(playerFile);
            playerConfig.set("Level", "1");
            playerConfig.set("EXP", "0/10");
            Main.getPlugin().saveYamlConfiguration(playerConfig, playerFile);

            sender.sendMessage(ChatColor.GREEN + playerName + "님의 레벨이 초기화되었습니다.");
            String Level = playerConfig.getString("Level");
            String EXP = playerConfig.getString("EXP");
            String message = (ColorUtils.chat("[ &b" + player.getName() + "&f님의 레벨정보: " + "&aLevel&f: " + Level + " / " + "&aEXP&f: " + EXP + " ]"));
            plugin.updateActionBar(player, message);
            return true;
        }
        return false;
    }
}