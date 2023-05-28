package io.leeple.level.Command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class LevelCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (sender instanceof Player player) {

                String arg0 = args[1];

                switch (arg0) {
                    case "초기화", "reset", "chrlghk" -> {
                        LevelReset RL = new LevelReset();
                        RL.onCommand(sender, command, label, args);
                    }

                    case "차감", "discount" -> {
                        LevelDiscount LD = new LevelDiscount();
                        LD.onCommand(sender, command, label, args);
                    }

                    case "추가", "count" -> {
                        LevelCount GL = new LevelCount();
                        GL.onCommand(sender, command, label, args);
                    }

                    case "보상", "reward" -> {
                        LevelReward LR = new LevelReward();
                        LR.onCommand(sender, command, label, args);
                    }

                    case "정보", "info" -> {
                        LevelInfo LI = new LevelInfo();
                        LI.onCommand(sender, command, label, args);
                    }

                    case "정보끄기", "Off", "off" -> {
                        OffActionBar OAB = new OffActionBar();
                        OAB.onCommand(sender, command, label, args);
                    }

                    case "정보켜기", "On", "on" -> {
                        OnActionBar OnAB = new OnActionBar();
                        OnAB.onCommand(sender, command, label, args);
                    }

                    case "보상설정", "ps" -> {
                        LevelRewardManager LRM = new LevelRewardManager();
                        LRM.onCommand(sender, command, label, args);
                    }

                    case "제한설정" -> {
                        LevelLimit LL = new LevelLimit();
                        LL.onCommand(sender, command, label, args);
                    }

                    /** PlayerDataUtil Test Command */
                    case "Test" -> {
                        TestPlayerDataUtil TPD = new TestPlayerDataUtil();
                        TPD.onCommand(sender, command, label, args);
                    }

                    default -> {
                        player.sendMessage("올바른 명령어를 입력하세요.");
                    }
                }
            } else {
                Level Lv = new Level();
                Lv.onCommand(sender, command, label, args);
            }
        return false;
    }
}