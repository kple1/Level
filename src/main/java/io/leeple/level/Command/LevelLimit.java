package io.leeple.level.Command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import static io.leeple.level.Main.plugin;

public class LevelLimit implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (sender instanceof Player player) {
            if (args.length > 0) {
                int limitLevel = Integer.parseInt(args[2]);
                plugin.getConfig().set("maxLevel", limitLevel);
                plugin.saveConfig();
                player.sendMessage("최대 레벨제한이 설정되었습니다.");
            }
        }
        return false;
    }
}
