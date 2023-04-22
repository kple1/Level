package io.leeple.level.command;

import io.leeple.level.utils.PlayerDataUtil;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class TestPlayerDataUtil implements CommandExecutor {

    YamlConfiguration config = PlayerDataUtil.GetConfig();

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (sender instanceof Player player) {
            if (args.length > 0) {
                function(player);
            }
        }
        return false;
    }

    public void function(Player player) {
        String level = String.valueOf(Integer.parseInt(config.getString("Level")));
        player.sendMessage("당신의 레벨: ", level);
    }
}
