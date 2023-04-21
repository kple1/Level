package io.leeple.level.command;

import io.leeple.level.Main;
import io.leeple.level.utils.ColorUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.UUID;

public class LevelCount implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (sender instanceof Player player) {

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

            // 플레이어의 Level과 EXP 정보를 추가
            YamlConfiguration config = YamlConfiguration.loadConfiguration(playerFile);

            String expString = config.getString("EXP"); // "현재 경험치/최대 경험치" 형식의 문자열을 가져옴
            int expToAdd = Integer.parseInt(args[2]); // args[0]에 입력된 값을 int형으로 파싱하여 추가할 경험치로 사용
            String[] expSplit = expString.split("/");
            int currentExp = Integer.parseInt(expSplit[0]); // 현재 경험치
            int maxExp = Integer.parseInt(expSplit[1]); // 최대 경험치
            int newExp = currentExp + expToAdd; // 이전 경험치와 추가할 경험치를 더하여 새로운 경험치를 계산
            if (newExp > maxExp) { // 새로운 경험치가 최대 경험치를 초과할 경우 최대 경험치로 설정
                newExp = maxExp;
            }

            String newExpString = newExp + "/" + maxExp; // 새로운 "현재 경험치/최대 경험치" 형식의 문자열을 생성
            config.set("EXP", newExpString); // 새로운 문자열로 "EXP" 키의 값을 업데이트
            Main.getPlugin().saveYamlConfiguration(config, playerFile); // 업데이트된 설정 파일을 저장

            String Level = config.getString("Level");
            String EXP = config.getString("EXP");
            String message = (ColorUtils.chat("[ &b" + player.getName() + "&f님의 레벨정보: " + "&aLevel&f: " + Level + " / " + "&aEXP&f: " + EXP + " ]"));
            plugin.updateActionBar(player, message);
        }
        return false;
    }
}