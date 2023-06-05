package io.leeple.level.Command;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

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

    @EventHandler
    public void closeEvent(InventoryCloseEvent event) {
        if (event.getView().getTitle().equals("동물킬 경험치 설정 (1 Page)")) {
            inv = event.getInventory();
            for (int i = 0; i < 54; i++) {
                ItemStack item = inv.getItem(i);
                if (item != null) {
                    plugin.getConfig().set("animal." + i + ".item", item);
                    plugin.getConfig().set("animal." + i + ".slot", i);
                } else {
                    plugin.getConfig().set("animal." + i + ".item", null);
                    plugin.getConfig().set("animal." + i + ".slot", null);
                }
            }
            plugin.saveConfig();
        }
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
    public void close2pageEvent(InventoryCloseEvent event) {
        if (event.getView().getTitle().equals("동물킬 경험치 설정 (2 Page)")) {
            inv = event.getInventory();
            for (int i = 0; i < 54; i++) {
                ItemStack item = inv.getItem(i);
                if (item != null) {
                    plugin.getConfig().set("animal2." + i + ".item", item);
                    plugin.getConfig().set("animal2." + i + ".slot", i);
                } else {
                    plugin.getConfig().set("animal2." + i + ".item", null);
                    plugin.getConfig().set("animal2." + i + ".slot", null);
                }
            }
            plugin.saveConfig();
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
}