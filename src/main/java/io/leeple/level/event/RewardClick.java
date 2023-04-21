package io.leeple.level.event;

import io.leeple.level.utils.ItemManager;
import io.leeple.level.utils.PlayerDataUtil;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

public class RewardClick implements Listener {

    /** Level Reward Slot Settings Inventory Open */
    public void Inventory(Player player) {
        Inventory inv = Bukkit.createInventory(null, 27, "레벨보상 슬롯설정");
        for (int i = 11; i < 16; i++) {
            switch (i) {
                case 11 -> inv.setItem(i, ItemManager.NameTag);
                case 12 -> inv.setItem(i, ItemManager.Seal);
                case 13 -> inv.setItem(i, ItemManager.Reward);
                case 14 -> inv.setItem(i, ItemManager.LimitLevel);
                case 15 -> inv.setItem(i, ItemManager.Icon);
                default -> {
                }
            }
        }
        player.openInventory(inv);
    }

    /** Inventory All Item Click Event */
    @EventHandler
    public void ClickEvent(InventoryClickEvent event) {
        int i;
        Player player = (Player) event.getWhoClicked();
        Inventory clickedInventory = event.getClickedInventory();
        int clickedSlot = event.getSlot();

        if (clickedInventory != null && clickedInventory.getType().name().equals("레벨보상")) {
            if (player.isOp()) {

            } else {
                player.sendMessage("관리자 권한이 없습니다");
            }
        }
    }
}
