package io.leeple.level.Command;

import io.leeple.level.Utils.ColorUtils;
import io.leeple.level.Data.PlayerData;
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
                YamlConfiguration config = PlayerData.getPlayerConfig(player);
                String Level = config.getString("Level");
                String EXP = config.getString("EXP");
                String message = (ColorUtils.chat("[ &b" + player.getName() + "&f님의 레벨정보: " + "&aLevel&f: " + Level + " / " + "&aEXP&f: " + EXP + " ]"));
                plugin.updateActionBar(player, message);
            }
        }
        return false;
    }
}
