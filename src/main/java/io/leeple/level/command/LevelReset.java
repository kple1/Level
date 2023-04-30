package io.leeple.level.command;

import io.leeple.level.Main;
import io.leeple.level.utils.ColorUtils;
import io.leeple.level.Data.PlayerData;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import org.jetbrains.annotations.NotNull;

import static io.leeple.level.Main.plugin;


public class LevelReset implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (sender instanceof Player player) {
            String playerName = player.getName();
            if (args.length == 0) {
                sender.sendMessage("/레벨 초기화 " + "플레이어 닉네임");
            }
            if (player.isOp()) {
                YamlConfiguration config = PlayerData.Config(args, sender);
                config.set("Level", "1");
                config.set("EXP", "0/10");
                Main.getPlugin().saveYamlConfiguration(player);

                sender.sendMessage(ChatColor.GREEN + playerName + "님의 레벨이 초기화되었습니다.");
                String Level = config.getString("Level");
                String EXP = config.getString("EXP");
                String message = (ColorUtils.chat("[ &b" + player.getName() + "&f님의 레벨정보: " + "&aLevel&f: " + Level + " / " + "&aEXP&f: " + EXP + " ]"));
                plugin.updateActionBar(player, message);
                return true;
            }
        }
        return false;
    }
}