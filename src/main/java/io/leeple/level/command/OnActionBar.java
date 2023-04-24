package io.leeple.level.command;

import io.leeple.level.utils.ColorUtils;
import io.leeple.level.utils.PlayerDataUtil;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import static io.leeple.level.Main.plugin;

public class OnActionBar implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (sender instanceof Player player) {
            if (args.length > 0) {
                YamlConfiguration config = PlayerDataUtil.Config(args, sender);
                String Level = config.getString("Level");
                String EXP = config.getString("EXP");
                String message = (ColorUtils.chat("[ &b" + player.getName() + "&f님의 레벨정보: " + "&aLevel&f: " + Level + " / " + "&aEXP&f: " + EXP + " ]"));
                plugin.updateActionBar(player, message);
            }
        }
        return false;
    }
}
