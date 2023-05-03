package io.leeple.level.Command;

import io.leeple.level.Listener.ChatListener;
import io.leeple.level.Utils.ColorUtils;
import io.leeple.level.Utils.ItemManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.NamespacedKey;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

import static io.leeple.level.Main.plugin;

public class LevelRewardManager implements CommandExecutor, Listener {

    private Inventory inventory;
    private Inventory Rinventory;
    private Inventory rewardInventory;

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (sender instanceof Player player) {
            if (args.length > 0) {
                if (player.isOp()) {
                    ItemNameSettings(player);
                    ItemList();
                }
            }
        }
        return false;
    }

    @EventHandler
    public void SettingsClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        if (event.getView().getTitle().equals("설정")) {
            if (event.getSlot() == 11) {
                player.closeInventory();
                player.sendMessage("아이템 이름을 채팅으로 입력해주세요:");
                int saveSlot = Integer.parseInt(plugin.getConfig().getString("saveSlot"));
                Bukkit.getPluginManager().registerEvents(new ChatListener(player, saveSlot), plugin);
            }
        }
    }

    @EventHandler
    public void ItemRewardSettings(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        if (event.getView().getTitle().equals("설정")) {
            if (event.getSlot() == 13) {
                this.rewardInventory = Bukkit.createInventory(null,54, "보상설정");
                getRewardSettings(player);
                player.openInventory(rewardInventory);
            }
        }
    }

    /* 관리자설정 아이템을 클릭시 아이콘에 대한 설정창 오픈 */
    @EventHandler
    public void RightClick(InventoryClickEvent event) {

        Player player = (Player) event.getWhoClicked();

        for (int i = 0; i < 36; i++) {
            if (event.getView().getTitle().equals("관리자설정")) {
                if (event.getSlot() == i) {
                    if (ClickType.SHIFT_LEFT == event.getClick()) {
                        this.Rinventory = Bukkit.createInventory(null, 27, "설정");
                        Rinventory.setItem(11, ItemManager.NameTag);
                        Rinventory.setItem(13, ItemManager.Reward);
                        Rinventory.setItem(15, ItemManager.LimitLevel);
                        player.openInventory(Rinventory);
                    }
                }
            }
        }
        for (int i = 36; i < 54; i++) {
            if (event.getView().getTitle().equals("관리자설정")) {
                if (event.getSlot() == i) {
                    event.setCancelled(true);
                }
            }
        }
    }

    public void ItemNameSettings(Player player) {
        this.inventory = Bukkit.createInventory(null, 54, "관리자설정");
        for (int i = 0; i < 36; i++) {
            // Item Name
            ItemStack item = plugin.getConfig().getItemStack("reward." + i + ".item");
            if (item == null) { // null 처리
                continue;
            }
            // Item Color
            String name = plugin.getConfig().getString("reward." + i + ".itemName");
            if (name == null) { // null 처리
                name = "";
            }
            name = ChatColor.translateAlternateColorCodes('&', name);
            // 아이템 이름 변경
            ItemMeta itemMeta = item.getItemMeta();
            itemMeta.setDisplayName(name);
            item.setItemMeta(itemMeta);
            // 변경된 아이템 배열
            inventory.setItem(i, item);
        }
        player.openInventory(inventory);
    }


    @EventHandler
    public void historyfunction(InventoryClickEvent event) {
        if (event.getView().getTitle().equals("관리자설정")) {
            plugin.getConfig().set("saveSlot", event.getSlot());
            plugin.saveConfig();
        }
    }

    public void ItemList() {
        ItemStack Designed = ItemManager.barrier;
        int i;
        for (i = 36; i < 54; i++) {
            inventory.setItem(i, Designed);
        }
    }

    @EventHandler
    public void CloseEvent(InventoryCloseEvent event) {
        if (event.getView().getTitle().equals("관리자설정")) {
            inventory = event.getInventory();
            for (int i = 0; i < 36; i++) {
                ItemStack item = inventory.getItem(i);
                if (item != null) {
                    plugin.getConfig().set("reward." + i + ".item", item);
                    plugin.getConfig().set("reward." + i + ".slot", i);
                } else {
                    plugin.getConfig().set("reward." + i + ".item", null);
                    plugin.getConfig().set("reward." + i + ".slot", null);
                }
            }
            plugin.saveConfig();
        }
    }

    //문제 해결하기 한개만 저장되는 문제
    @EventHandler
    public void RewardCloseEvent(InventoryCloseEvent event) {
        if (event.getView().getTitle().equals("보상설정")) {
            rewardInventory = event.getInventory();
            for (int i = 0; i < 54; i++) {
                ItemStack item = rewardInventory.getItem(i);
                if (item != null) {
                    ItemMeta itemMeta = item.getItemMeta();
                    if (itemMeta != null) {
                        // 메타데이터에서 식별자 추출
                        PersistentDataContainer container = itemMeta.getPersistentDataContainer();
                        if (container.has(new NamespacedKey(plugin, "identifier"), PersistentDataType.STRING)) {
                            String identifier = container.get(new NamespacedKey(plugin, "identifier"), PersistentDataType.STRING);
                            // 식별자를 사용하여 아이템을 설정 파일에 저장
                            plugin.getConfig().set("reward." + identifier + ".itemReward", item);
                        }
                    }
                }
            }
            plugin.saveConfig();
        }
    }



    public void getRewardSettings(Player player) {
        for (int i = 0; i < 36; i++) {
            ItemStack item = plugin.getConfig().getItemStack("reward." + i + ".itemReward");
            rewardInventory.setItem(i, item);
            player.openInventory(rewardInventory);
        }
    }
}
