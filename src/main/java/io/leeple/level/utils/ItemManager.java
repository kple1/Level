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

    YamlConfiguration config = PlayerDataUtil.GetConfig();
    private Player player;

    private static ItemStack Result1(Material type, int amount, String displayName, String... lore) {
        ItemStack stack = new ItemStack(type, amount);
        ItemMeta meta = stack.getItemMeta();
        meta.setDisplayName(displayName);
        meta.setLore(Arrays.asList(lore));
        stack.setItemMeta(meta);
        return stack;
    }

    private static ItemStack Result2(Material type, int amount) {
        ItemStack stack = new ItemStack(type, amount);
        return stack;
    }

    public static final ItemStack ItemList = Result2(Material.CHEST_MINECART, 1);
    public static final ItemStack NextPage = Result2(Material.OAK_BUTTON, 1);
    public static final ItemStack RewardDesigned = Result2(Material.BLACK_STAINED_GLASS_PANE, 1);
    public static final ItemStack NameTag = Result2(Material.NAME_TAG, 1);
    public static final ItemStack Seal = Result2(Material.STRING, 1);
    public static final ItemStack Reward = Result2(Material.ENCHANTING_TABLE, 1);
    public static final ItemStack Icon = Result2(Material.GLASS, 1);
    public static final ItemStack LimitLevel = Result2(Material.DIAMOND, 1);
}