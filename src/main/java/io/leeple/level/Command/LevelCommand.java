package io.leeple.level.Command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.checkerframework.checker.units.qual.K;
import org.jetbrains.annotations.NotNull;

public class LevelCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (sender instanceof Player player) {

            if (args.length == 0) {
                Level Lv = new Level();
                Lv.onCommand(sender, command, label, args);
            }

            if (args.length >= 1) {
                switch (args[0]) {
                    case "제한설정" -> {
                        LevelLimit LL = new LevelLimit();
                        LL.onCommand(sender, command, label, args);
                    }

                    case "배수설정" -> {
                        LevelMultiple LM = new LevelMultiple();
                        LM.onCommand(sender, command, label, args);
                    }

                    case "경험치설정" -> {
                        KillEntityExp KEE = new KillEntityExp();
                        KEE.onCommand(sender, command, label, args);
                    }

                    case "킬설정" -> {
                        SetKillEntity SKE = new SetKillEntity();
                        SKE.onCommand(sender, command, label, args);
                    }
                }
            }

            if (args.length >= 2) {
                switch (args[1]) {
                    case "초기화", "reset" -> {
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

                    case "정보", "info" -> {
                        LevelInfo LI = new LevelInfo();
                        LI.onCommand(sender, command, label, args);
                    }

                    case "보상설정", "ps" -> {
                        LevelRewardManager LRM = new LevelRewardManager();
                        LRM.onCommand(sender, command, label, args);
                    }

                    case "정보끄기", "Off", "off" -> {
                        OffActionBar OAB = new OffActionBar();
                        OAB.onCommand(sender, command, label, args);
                    }

                    case "정보켜기", "On", "on" -> {
                        OnActionBar OnAB = new OnActionBar();
                        OnAB.onCommand(sender, command, label, args);
                    }

                    case "보상", "reward" -> {
                        LevelReward LR = new LevelReward();
                        LR.onCommand(sender, command, label, args);
                    }

                    default -> {
                        player.sendMessage("올바른 명령어를 입력하세요.");
                    }
                }
            }
        }
        return false;
    }
}