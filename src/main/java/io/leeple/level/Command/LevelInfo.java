package io.leeple.level.Command;

import io.leeple.level.Data.PlayerData;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class LevelInfo implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if (sender instanceof Player player) {
            if (args.length > 0) {
                YamlConfiguration config = PlayerData.Config(args, sender);

                String LEVEL = "[ &6" + player + "&f" + "님의 레벨정보 ]";
                // 플레이어의 Level 정보를 알려줌
                String level_info = config.getString("Level", "");
                String level_exp = config.getString("EXP", "");
                player.sendMessage("");
                player.sendMessage(LEVEL);
                player.sendMessage("&6" + player + "&f" + "님의 레벨: " + "&a" + level_info);
                player.sendMessage("&6" + player + "&f" + "님의 경험치: " + "&a" + level_exp);
                player.sendMessage("");
            }
        }
        return false;
    }
}