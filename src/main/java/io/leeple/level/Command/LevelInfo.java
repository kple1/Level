package io.leeple.level.Command;

import io.leeple.level.Data.PlayerData;
import io.leeple.level.Utils.ColorUtils;
import org.bukkit.Bukkit;
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
                YamlConfiguration config = PlayerData.getPlayerConfig(player);
                String getArgsPlayerName = args[0];
                Player targetPlayer = Bukkit.getPlayerExact(getArgsPlayerName);
                String playerName = targetPlayer.getName();

                String LEVEL = (ColorUtils.chat ("[ &6" + playerName + "&f" + "님의 레벨정보 ]"));
                // 플레이어의 Level 정보를 알려줌
                String level_info = config.getString("Level", "");
                String level_exp = config.getString("EXP", "");
                player.sendMessage("");
                player.sendMessage(LEVEL);
                player.sendMessage(ColorUtils.chat ("[ Level ] : " + "&a" + level_info));
                player.sendMessage(ColorUtils.chat ("[ EXP ] : " + "&a" + level_exp));
                player.sendMessage("");
            }
        }
        return false;
    }
}