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
            if (args.length > 0) {


                String arg = args[1];
                switch (arg) {
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
                player.sendMessage("[ Level Command ] ");
                player.sendMessage("1. /Level ( 레벨에 대한 명령어를 확인합니다. )");
                player.sendMessage("2. /Level <User> Count  <number> ( 유저에게 number만큼 경험치를 추가합니다. )");
                player.sendMessage("3. /Level <User> Discount  <number> ( 유저에게 number만큼 경험치를 차감합니다. )");
                player.sendMessage("4. /Level <User> info ( 레벨정보를 확인합니다. )");
                player.sendMessage("5. /Level <User> reset ( 레벨을 초기화 시킵니다. )");
                player.sendMessage("6. /Level <User> Reward ( 레벨보상 인벤토리를 오픈합니다. )");
                player.sendMessage("7. /Level <User> RewardManager ( 레벨보상 관리창을 오픈합니다. )");
                player.sendMessage("8. /Level <User> On/Off ( 하단에 표시된 레벨정보 바를 키거나 끕니다. )");
            }
        }
        return false;
    }
}