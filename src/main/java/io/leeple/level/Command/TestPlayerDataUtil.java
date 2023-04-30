package io.leeple.level.Command;

import io.leeple.level.Data.PlayerData;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class TestPlayerDataUtil implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        YamlConfiguration config = PlayerData.Config(args, sender); // config 변수를 수정된 PlayerDataUtil.Config() 메소드에서 반환된 값으로 할당
        if (config != null && sender instanceof Player player) { // config가 null이 아니면 실행
            String useConfig = String.valueOf(Integer.parseInt(config.getString("Level")));
            player.sendMessage(useConfig);
            return true;
        }
        return false;
    }
}
