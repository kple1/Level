package io.leeple.level.Command;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static io.leeple.level.Main.plugin;

public class SetKillEntity implements CommandExecutor, Listener {

    private Inventory inv;
    private Inventory nextPage;

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (sender instanceof Player player) {
            if (args.length == 1) {
                if (player.isOp()) {
                    inventory(player);
                }
            }
        }
        return false;
    }

    public void inventory(Player player) {
        this.inv = Bukkit.createInventory(null, 54, "동물킬 경험치 설정 (1 Page)");
        for (int i = 0; i < 54; i++) {
            ItemStack item = plugin.getConfig().getItemStack("animal." + i + ".item");
            if (item == null) {
                continue;
            }
            String name = plugin.getConfig().getString("animal." + i + ".itemName");
            if (name == null) {
                name = "";
            }
            name = ChatColor.translateAlternateColorCodes('&', name);
            ItemMeta itemMeta = item.getItemMeta();
            itemMeta.setDisplayName(name);
            item.setItemMeta(itemMeta);
            inv.setItem(i, item);
            inv.setItem(i, item);
        }
        player.openInventory(inv);
    }

    @EventHandler
    public void nextPage(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        if (event.getView().getTitle().equals("동물킬 경험치 설정 (1 Page)")) {
            if (event.getSlot() == 53) {
                this.nextPage = Bukkit.createInventory(null, 54, "동물킬 경험치 설정 (2 Page)");
                for (int i = 0; i < 54; i++) {
                    ItemStack item = plugin.getConfig().getItemStack("animal2." + i + ".item");
                    if (item == null) {
                        continue;
                    }
                    String name = plugin.getConfig().getString("animal2." + i + ".itemName");
                    if (name == null) {
                        name = "";
                    }
                    nextPage.setItem(i, item);
                }
                player.openInventory(nextPage);
            }
        }
    }

    @EventHandler
    public void beforePageEvent(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        if (event.getView().getTitle().equals("동물킬 경험치 설정 (2 Page)")) {
            if (event.getSlot() == 45) {
                for (int i = 0; i < 54; i++) {
                    ItemStack item = plugin.getConfig().getItemStack("animal." + i + ".item");
                    if (item == null) {
                        continue;
                    }
                    String name = plugin.getConfig().getString("animal." + i + ".itemName");
                    if (name == null) {
                        name = "";
                    }
                    nextPage.setItem(i, item);
                }
                player.openInventory(inv);
            }
        }
    }

    @EventHandler
    public void setKillEntity(InventoryClickEvent event) {
        if (event.getView().getTitle().equals("동물킬 경험치 설정 (1 Page)") || event.getView().getTitle().equals("동물킬 경험치 설정 (2 Page)")) {
            Inventory inventory = event.getInventory();
            int clickedSlot = event.getSlot();

            ItemStack clickedItem = inventory.getItem(clickedSlot);

            // 클릭한 슬롯에 아이템이 존재하고 로어를 변경하고자 하는 아이템인지 확인
            if (clickedItem != null) {
                ItemMeta itemMeta = clickedItem.getItemMeta();

                // 기존 로어 값 확인
                List<String> lore = itemMeta.getLore();

                if (lore == null || lore.isEmpty() || lore.get(0).equals("")) {
                    // 아이템에 로어가 없거나 공백인 경우
                    itemMeta.setLore(Collections.singletonList(ChatColor.GREEN + "> 선택되었습니다!"));
                } else if (lore.get(0).equals(ChatColor.GREEN + "")) {
                    // 아이템에 공백이 있는 경우
                    itemMeta.setLore(Collections.singletonList(ChatColor.GREEN + "> 선택되었습니다!"));
                } else {
                    // 아이템에 다른 로어 값이 있는 경우
                    itemMeta.setLore(Collections.singletonList(ChatColor.GREEN + ""));
                }

                // 아이템 메타 업데이트
                clickedItem.setItemMeta(itemMeta);
            }
        }
    }
}