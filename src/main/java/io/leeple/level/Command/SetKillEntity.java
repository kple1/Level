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

    /* 수정하기 클릭시 설정된 엔티티 표시 */
    @EventHandler
    public void setKillEntity(InventoryClickEvent event) {
        if (event.getView().getTitle().equals("동물킬 경험치 설정 (1 Page)") || event.getView().getTitle().equals("동물킬 경험치 설정 (2 Page)")) {
            int clickedSlot = event.getSlot();
            ItemStack clickedItem = event.getInventory().getItem(clickedSlot);

            if (clickedItem != null) {
                // 선택한 아이템의 로어를 가져오기
                ItemMeta itemMeta = clickedItem.getItemMeta();
                List<String> lore = itemMeta.getLore();

                if (lore != null) {
                    lore.set(clickedSlot, "> 설정된 아이템 입니다.");

                    itemMeta.setLore(lore);
                    clickedItem.setItemMeta(itemMeta);

                    // 인벤토리 업데이트
                    event.getInventory().setItem(clickedSlot, clickedItem);
                } else if (lore.equals("> 설정된 아이템 입니다.")) {
                    lore.set(clickedSlot, "");
                }
            }
        }
    }
}