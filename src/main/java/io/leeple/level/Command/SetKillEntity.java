package io.leeple.level.Command;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import static io.leeple.level.Main.plugin;

public class SetKillEntity implements CommandExecutor {

    private Inventory inv;

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (sender instanceof Player player) {
            if (args.length == 1) {
                if (player.isOp()) {
                    inventory(player);
                    designed();
                }
            }
        }
        return false;
    }

    public void inventory(Player player) {
            this.inv = Bukkit.createInventory(null, 54, "동물킬 경험치 설정");
            player.openInventory(inv);
    }

    public void designed() {
        for (int i = 0; i < 46; i++) {
            ItemStack item = plugin.getConfig().getItemStack("animal." + i + ".item");
            inv.setItem(i, item);
        }
    }
}