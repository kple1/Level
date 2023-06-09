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

import java.util.*;

import static io.leeple.level.Main.plugin;

public class LevelReward implements CommandExecutor, Listener {

    private Inventory inv;
    ItemStack userLevel;

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (sender instanceof Player player) {
            if (args.length > 0) {

                YamlConfiguration config = PlayerData.getPlayerConfig(player);

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

    private static ItemStack Result1(Material type, int amount, String displayName, String... lore) {
        ItemStack stack = new ItemStack(type, amount);
        ItemMeta meta = stack.getItemMeta();
        meta.setDisplayName(displayName);
        meta.setLore(Arrays.asList(lore));
        stack.setItemMeta(meta);
        return stack;
    }

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
    public void saveClickReward(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        YamlConfiguration config = PlayerData.getPlayerConfig(player);
        int slot = event.getRawSlot();

        if (!event.getView().getTitle().equals("레벨보상")) {
            return;
        }

        if (!config.contains(String.valueOf(slot))) {
            config.set(String.valueOf(slot), "X");
            Main.getPlugin().saveYamlConfiguration();
        }
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (!event.getView().getTitle().equals("레벨보상")) {
            return;
        }

        Player player = (Player) event.getWhoClicked();
        YamlConfiguration eventConfig = PlayerData.getPlayerConfig(player);
        int clickedSlot = event.getRawSlot();
        String saveReward = eventConfig.getString(String.valueOf(clickedSlot));
        int limitLevel = Integer.parseInt(plugin.getConfig().getString("reward." + clickedSlot + ".limitlevel"));
        int playerLevel = Integer.parseInt(eventConfig.getString("Level"));

        boolean receivedReward = false; // 보상을 이미 수령했는지 여부를 나타내는 변수

        for (int i = 0; ; i++) {
            ItemStack reward = plugin.getConfig().getItemStack("reward." + clickedSlot + ".itemReward" + i);
            if (reward == null) {
                break;
            }

            if (Objects.equals(saveReward, "O")) {
                player.sendMessage(ColorUtils.chat("&c이미 수령한 보상입니다"));
                event.setCancelled(true);
                break;
            }

            if (playerLevel >= limitLevel) {
                if (Objects.equals(saveReward, "X")) {
                    player.getInventory().addItem(reward);
                    eventConfig.set(String.valueOf(clickedSlot), "O");
                    Main.getPlugin().saveYamlConfiguration();
                    receivedReward = true; // 보상을 수령했음을 표시
                }
            } else {
                player.sendMessage("레벨이 낮아서 수령이 불가능합니다.");
                break;
            }
        }

        /* loop에서 벗어 나는 코드 */

        if (receivedReward) {
            player.sendMessage("보상을 수령하셨습니다!");
        }
    }

    public void ItemList() {
        ItemStack Designed = ItemManager.RewardDesigned;
        int i;
        for (i = 36; i < 45; i++) {
            inv.setItem(i, Designed);
        }
    }
}