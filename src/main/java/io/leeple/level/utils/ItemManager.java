package io.leeple.level.utils;

import io.leeple.level.Main;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.UUID;

public class ItemManager {

    private Player player;

    private static ItemStack result_LongType(Material type, int amount, String displayName, String... lore) {
        ItemStack stack = new ItemStack(type, amount);
        ItemMeta meta = stack.getItemMeta();
        meta.setDisplayName(displayName);
        meta.setLore(Arrays.asList(lore));
        stack.setItemMeta(meta);
        return stack;
    }

    private static ItemStack result_ShortType(Material type, int amount) {
        ItemStack stack = new ItemStack(type, amount);
        return stack;
    }

    public static final ItemStack barrier = result_LongType(Material.BARRIER,1,(ColorUtils.chat("&c채울 수 없는 칸 입니다.")));
    public static final ItemStack NextPage = result_ShortType(Material.OAK_BUTTON, 1);
    public static final ItemStack RewardDesigned = result_ShortType(Material.BLACK_STAINED_GLASS_PANE, 1);
    public static final ItemStack NameTag = result_ShortType(Material.NAME_TAG, 1);
    public static final ItemStack Seal = result_ShortType(Material.STRING, 1);
    public static final ItemStack Reward = result_ShortType(Material.ENCHANTING_TABLE, 1);
    public static final ItemStack Icon = result_ShortType(Material.GLASS, 1);
    public static final ItemStack LimitLevel = result_ShortType(Material.DIAMOND, 1);
}