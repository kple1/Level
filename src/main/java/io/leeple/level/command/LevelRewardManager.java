package io.leeple.level.command;

import io.leeple.level.utils.ItemManager;
import org.bukkit.Bukkit;
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
import org.jetbrains.annotations.NotNull;

import static io.leeple.level.Main.plugin;

public class LevelRewardManager implements CommandExecutor, Listener {

    private Inventory inventory;
    private Inventory Rinventory;

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (sender instanceof Player player) {
            if (args.length > 0) {
                if (player.isOp()) {
                    ManagerSettings(player);
                    ItemList();
                }
            }
        }
        return false;
    }

    @EventHandler
    public void RightClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        for (int i = 0; i < 36; i++) {
            if (event.getView().getTitle().equals("관리자설정")) {
                if (event.getSlot() == i) {
                    this.Rinventory = Bukkit.createInventory(null, 27, "설정");
                    Rinventory.setItem(11, ItemManager.NameTag);
                    Rinventory.setItem(13, ItemManager.Reward);
                    Rinventory.setItem(15, ItemManager.LimitLevel);
                    player.openInventory(Rinventory);
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

    public void ManagerSettings(Player player) {
        this.inventory = Bukkit.createInventory(null, 54, "관리자설정");
        for (int i = 0; i < 36; i++) {
            ItemStack item = plugin.getConfig().getItemStack("reward." + i + ".item");
            inventory.setItem(i, item);
        }
        player.openInventory(inventory);
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
}
