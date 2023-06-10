package io.leeple.level.Command;

import org.bukkit.command.*;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class LevelCommand implements CommandExecutor, TabExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (sender instanceof Player player) {

            if (args.length == 0) {
                Level level = new Level();
                level.onCommand(sender, command, label, args);
            }

            if (args.length >= 1) {
                switch (args[0]) {
                    case "제한설정" -> {
                        LevelLimit levelLimit = new LevelLimit();
                        levelLimit.onCommand(sender, command, label, args);
                    }

                    case "배수설정" -> {
                        LevelMultiple levelMultiple = new LevelMultiple();
                        levelMultiple.onCommand(sender, command, label, args);
                    }

                    case "킬설정" -> {
                        SetKillEntity setKillEntity = new SetKillEntity();
                        setKillEntity.onCommand(sender, command, label, args);
                    }
                }
            }

            if (args.length >= 2) {
                switch (args[1]) {
                    case "초기화", "reset" -> {
                        LevelReset levelReset = new LevelReset();
                        levelReset.onCommand(sender, command, label, args);
                    }

                    case "차감", "discount" -> {
                        LevelDiscount levelDiscount = new LevelDiscount();
                        levelDiscount.onCommand(sender, command, label, args);
                    }

                    case "추가", "count" -> {
                        LevelCount levelCount = new LevelCount();
                        levelCount.onCommand(sender, command, label, args);
                    }

                    case "정보", "info" -> {
                        LevelInfo levelInfo = new LevelInfo();
                        levelInfo.onCommand(sender, command, label, args);
                    }

                    case "보상설정", "ps" -> {
                        LevelRewardManager levelRewardManager = new LevelRewardManager();
                        levelRewardManager.onCommand(sender, command, label, args);
                    }

                    case "정보끄기", "Off", "off" -> {
                        OffActionBar offActionBar = new OffActionBar();
                        offActionBar.onCommand(sender, command, label, args);
                    }

                    case "정보켜기", "On", "on" -> {
                        OnActionBar onActionBar = new OnActionBar();
                        onActionBar.onCommand(sender, command, label, args);
                    }

                    case "보상", "reward" -> {
                        LevelReward levelReward = new LevelReward();
                        levelReward.onCommand(sender, command, label, args);
                    }

                    default -> {
                        player.sendMessage("올바른 명령어를 입력하세요.");
                    }
                }
            }
        }
        return false;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if (!(sender instanceof Player player)) {
            return Collections.emptyList();
        }

        final List<String> tabList = new ArrayList<>();

        if (args.length == 1) {
            if (player.isOp()) {
                tabList.add("제한설정");
                tabList.add("배수설정");
                tabList.add("킬설정");
                tabList.add("초기화");
                tabList.add("차감");
                tabList.add("추가");
                tabList.add("보상설정");
            } else {
                tabList.add("정보");
                tabList.add("정보끄기");
                tabList.add("정보켜기");
                tabList.add("보상");
            }
            return StringUtil.copyPartialMatches(args[0], tabList, new ArrayList<>());
        }

        if (args.length == 2) {
            if (("추가".equals(args[0]) || "차감".equals(args[0]) || "초기화".equals(args[0]) && player.isOp())) {
                tabList.add("<NickName>");
            }

            if (("제한설정".equals(args[0]) || "배수설정".equals(args[0]) || "킬설정".equals(args[0]) && player.isOp())) {
                tabList.add("<Number>");
            }
            return StringUtil.copyPartialMatches(args[1], tabList, new ArrayList<>());
        }

        if (args.length == 3 && player.isOp()) {
            if ("추가".equals(args[0]) || "차감".equals(args[0])) {
                tabList.add("<Number>");
            }
            return StringUtil.copyPartialMatches(args[2], tabList, new ArrayList<>());
        }
        return tabList;
    }
}