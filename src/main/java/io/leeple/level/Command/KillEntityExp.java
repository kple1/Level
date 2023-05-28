package io.leeple.level.Command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import static io.leeple.level.Main.plugin;

public class KillEntityExp implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (sender instanceof Player player) {
            if (args.length >= 2) {
                if (player.isOp()) {
                    String arg1 = args[1];
                    if (arg1.charAt(0) >= 'A' && arg1.charAt(0) <= 'Z') {
                        plugin.getConfig().set("KillEntityExp", Integer.parseInt(arg1));
                        plugin.saveConfig();
                        player.sendMessage("설정이 저장되었습니다");
                    } else {
                        player.sendMessage("대문자로 시작해야 합니다!");
                    }
                } else {
                    player.sendMessage("권한이 없습니다");
                }
            } else {
                player.sendMessage("값을 입력 해주세요");
            }
        }
        return false;
    }
}
