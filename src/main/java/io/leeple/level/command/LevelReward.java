package io.leeple.level.command;

import io.leeple.level.Main;
import io.leeple.level.utils.ColorUtils;
import io.leeple.level.utils.ItemManager;
import io.leeple.level.utils.PlayerDataUtil;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;
import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.util.*;

public class LevelReward implements CommandExecutor {

    /** Get function */
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (sender instanceof Player player) {
            if (args.length > 0) {
                String playerName = args[0];
                Player targetPlayer = Bukkit.getPlayerExact(playerName);
                UUID targetUUID;

                // 플레이어가 현재 온라인 상태인 경우, 온라인 플레이어의 UUID를 사용
                if (targetPlayer != null) {
                    targetUUID = targetPlayer.getUniqueId();
                } else { // 오프라인 플레이어의 경우, 플레이어 데이터 파일에서 UUID를 가져옴
                    OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(playerName);
                    if (!offlinePlayer.hasPlayedBefore()) {
                        sender.sendMessage(ChatColor.RED + "해당 플레이어가 한 번도 접속한 적이 없습니다.");
                        return true;
                    }
                    targetUUID = offlinePlayer.getUniqueId();
                }
                // 해당 UUID의 폴더에서 플레이어 데이터 파일을 가져옴
                File playerFile = new File(Main.getPlugin().getUuidFolder(), targetUUID.toString() + ".yml");

                // 해당 플레이어의 데이터 파일이 없는 경우 초기값 생성
                if (!playerFile.exists()) {
                    UUID uuid = player.getUniqueId();
                    Main.getPlugin().createPlayerDefaults(uuid);
                }

                // 플레이어의 Level과 EXP 정보를 추가
                YamlConfiguration config = YamlConfiguration.loadConfiguration(playerFile);
                String Level = config.getString("Level");
                String EXP = config.getString("EXP");

                String playername = player.getName();
                userLevel = Result1(Material.EXPERIENCE_BOTTLE, 1, (ColorUtils.chat("&f[ " + playername + "님의 &k1 &a레벨 &f&k1&f 정보 ]")), ColorUtils.chat("&fLevel : &a" + Level + " &f/ &fEXP : &b" + EXP));
                Inventory(player);
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


    private Inventory inv;
    ItemStack userLevel;

    /** Inventory Open & Settings */
    public void Inventory(Player player) {
        this.inv = Bukkit.createInventory(null, 54, "레벨보상");
        inv.setItem(46, ItemManager.NextPage);
        inv.setItem(52, ItemManager.NextPage);
        inv.setItem(49, userLevel);
        player.openInventory(inv);
    }

    /** Inventory Item List */
    public void ItemList() {
        ItemStack Designed = ItemManager.RewardDesigned;
        ItemStack Item = ItemManager.ItemList;
        int i;
        /** Item Reward */

        /** Item Designed */
        for (i = 36; i < 45; i++) {
            inv.setItem(i, Designed);
        }
    }
}