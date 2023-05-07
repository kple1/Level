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

    // 수정하기
    @EventHandler
    public void saveClickReward(InventoryClickEvent event) {
        YamlConfiguration config = PlayerData.ClickConfig(event);
        int slot = event.getRawSlot();
        String rewardKey = slot + "번 보상 수령여부";

        if (!event.getView().getTitle().equals("레벨보상")) {
            return;
        }

        if (!config.getString(rewardKey, "").equals("X")) {
            config.set(rewardKey, "X");
            Main.getPlugin().saveEventYamlConfiguration();
        }
    }


    // 수정하기
    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (!event.getView().getTitle().equals("레벨보상")) {
            return;
        }

        Player player = (Player) event.getWhoClicked();
        YamlConfiguration eventConfig = PlayerData.ClickConfig(event);
        int clickedSlot = event.getRawSlot();
        String saveReward = eventConfig.getString(clickedSlot + "번 보상 수령여부");
        int limitLevel = Integer.parseInt(plugin.getConfig().getString("reward." + clickedSlot + ".limitlevel"));
        int playerLevel = Integer.parseInt(eventConfig.getString("Level"));

        for (int i = 0; ; i++) {
            ItemStack reward = plugin.getConfig().getItemStack("reward." + clickedSlot + ".itemReward" + i);
            if (reward == null) {
                break;
            }

            if (playerLevel >= limitLevel) {
                if (saveReward.equals("X")) {
                    player.getInventory().addItem(reward);
                    player.sendMessage("보상을 수령하였습니다.");
                    eventConfig.set(clickedSlot + "번 보상 수령여부", "O");
                    Main.getPlugin().saveEventYamlConfiguration();
                    break; // 보상을 수령하면 루프 종료

                } else if (saveReward.equals("O")) {
                    player.sendMessage(ColorUtils.chat("&c이미 수령한 보상입니다"));
                    event.setCancelled(true);
                    break; // 이미 보상을 수령했으면 루프 종료
                }

            } else {
                player.sendMessage("레벨이 낮아서 수령이 불가능합니다.");
                break; // 레벨이 낮으면 루프 종료
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