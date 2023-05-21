package io.leeple.level.Listener;

import io.leeple.level.Command.LevelReward;
import io.leeple.level.Utils.ColorUtils;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class MainLevelSystem implements Listener {

    @EventHandler
    public static void ClickSystem(InventoryClickEvent event) {

        Player player = (Player) event.getWhoClicked();

        if (!event.getView().getTitle().equals("Level System")) {
            return;
        }

        if (event.getSlot() == 10) {
            player.sendMessage("-----------------------------------------------------");
            player.sendMessage(ColorUtils.chat("&6>&f /Level -> &a레벨 편의기능 UI가 제공됩니다."));
            player.sendMessage(ColorUtils.chat("&6>&f /Level <User> Count / Discount  <number> -> &a유저에게 number만큼 경험치를 추가/차감 합니다."));
            player.sendMessage(ColorUtils.chat("&6>&f /Level <User> info -> &a레벨정보를 확인합니다."));
            player.sendMessage(ColorUtils.chat("&6>&f /Level <User> reset -> &a레벨을 초기화 시킵니다."));
            player.sendMessage(ColorUtils.chat("&6>&f /Level <User> Reward -> &a레벨보상 UI를 오픈합니다."));
            player.sendMessage(ColorUtils.chat("&6>&f /Level <User> RewardManager -> &a레벨보상 관리UI를 오픈합니다."));
            player.sendMessage(ColorUtils.chat("&6>&f /Level <User> On/Off -> &a하단 레벨정보 바를 키거나 끕니다."));
            player.sendMessage("-----------------------------------------------------");
        }

        if (event.getSlot() == 12) {
            LevelReward LR = new LevelReward();
            LR.ItemReward();
            LR.ItemList();
            LR.Inventory(player);
        }
    }
}
