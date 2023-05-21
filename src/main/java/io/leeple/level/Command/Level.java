package io.leeple.level.Command;

import io.leeple.level.Utils.ItemManager;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.jetbrains.annotations.NotNull;

public class Level implements CommandExecutor {

    Inventory inv;

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (sender instanceof Player player) {
            Inventory(player);
        }
        return false;
    }

    public void Inventory(Player player) {
        this.inv = Bukkit.createInventory(null, 27, "Level System");
        inv.setItem(10, ItemManager.levelCommandHelp);
        inv.setItem(12, ItemManager.levelReward);
        inv.setItem(14, ItemManager.levelInfo);
        inv.setItem(16, ItemManager.levelActionBar);
        player.openInventory(inv);
    }
}
