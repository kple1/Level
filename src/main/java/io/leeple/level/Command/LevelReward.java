package io.leeple.level.Command;

import io.leeple.level.Main;
import io.leeple.level.Utils.ColorUtils;
import io.leeple.level.Utils.ItemManager;
import io.leeple.level.Data.PlayerData;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;
import org.yaml.snakeyaml.Yaml;

import java.util.*;

import static io.leeple.level.Main.plugin;

public class LevelReward implements CommandExecutor, Listener {

    private Inventory inv;
    ItemStack userLevel;

    /**
     * Get function
     */
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (sender instanceof Player player) {
            if (args.length > 0) {

                YamlConfiguration config = PlayerData.Config(args, sender);

                String Level = config.getString("Level");
                String EXP = config.getString("EXP");

                String playername = player.getName();
                userLevel = Result1(Material.EXPERIENCE_BOTTLE, 1, (ColorUtils.chat("&f[ " + playername + "님의 &k1 &a레벨 &f&k1&f 정보 ]")), ColorUtils.chat("&fLevel : &a" + Level + " &f/ &fEXP : &b" + EXP));
                Inventory(player);
                ItemReward();
                ItemList();
            }
        }
        return false;
    }

    //** ItemStack */
    private static ItemStack Result1(Material type, int amount, String displayName, String... lore) {
        ItemStack stack = new ItemStack(type, amount);
        ItemMeta meta = stack.getItemMeta();
        meta.setDisplayName(displayName);
        meta.setLore(Arrays.asList(lore));
        stack.setItemMeta(meta);
        return stack;
    }

    /**
     * Inventory Open & Settings
     */
    public void Inventory(Player player) {
        this.inv = Bukkit.createInventory(null, 54, "레벨보상");
        inv.setItem(46, ItemManager.NextPage);
        inv.setItem(52, ItemManager.NextPage);
        inv.setItem(49, userLevel);
        player.openInventory(inv);
    }

    public void ItemReward() {
        for (int i = 0; i < 36; i++) {
            ItemStack item = plugin.getConfig().getItemStack("reward." + i + ".item");
            inv.setItem(i, item);
        }
    }

    @EventHandler
    public void ClickReward(InventoryClickEvent event) {
        YamlConfiguration config = PlayerData.ClickConfig(event);
        Player player = (Player) event.getWhoClicked();
        if (event.getView().getTitle().equals("레벨보상")) {
            int clickedSlot = event.getRawSlot();
            boolean canReceive = false; // 레벨이 충분해서 보상을 받을 수 있는지 여부를 저장하는 변수
            for (int i = 0; i < 54; i++) {
                ItemStack reward = plugin.getConfig().getItemStack("reward." + clickedSlot + ".itemReward" + i);
                int limitLevel = Integer.parseInt(plugin.getConfig().getString("reward." + clickedSlot + ".limitlevel"));
                int playerLevel = Integer.parseInt(config.getString("Level"));
                if (reward != null) {
                    if (playerLevel >= limitLevel) {
                        canReceive = true;
                        player.getInventory().addItem(reward);
                    }
                }
            }
            if (canReceive) {
                player.sendMessage("보상을 수령하였습니다.");
            } else {
                player.sendMessage(ColorUtils.chat("&c레벨이 낮아서 수령이 불가능합니다"));
            }
        }
    }



    /**
     * Item Designed
     */
    public void ItemList() {
        ItemStack Designed = ItemManager.RewardDesigned;
        int i;
        for (i = 36; i < 45; i++) {
            inv.setItem(i, Designed);
        }
    }
}