package io.leeple.level.Utils;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

public class ItemManager {

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

    public static ItemStack result_NameType(Material type, int amount, String displayName) {
        ItemStack stack = new ItemStack(type, amount);
        ItemMeta meta = stack.getItemMeta();
        meta.setDisplayName(displayName);
        return stack;
    }

    static String level = "&f[ &aLevel &f] ";

    public static final ItemStack barrier = result_LongType(Material.BARRIER, 1, (ColorUtils.chat("&c채울 수 없는 칸 입니다.")));
    public static final ItemStack NextPage = result_ShortType(Material.OAK_BUTTON, 1);
    public static final ItemStack RewardDesigned = result_ShortType(Material.BLACK_STAINED_GLASS_PANE, 1);
    public static final ItemStack NameTag = result_LongType(Material.NAME_TAG, 1, (ColorUtils.chat("&f[&7 보상이름 설정 &f]")));
    public static final ItemStack Reward = result_LongType(Material.ENCHANTING_TABLE, 1, (ColorUtils.chat("&f[&7 보상 설정 &f]")));
    public static final ItemStack LimitLevel = result_LongType(Material.DIAMOND, 1, (ColorUtils.chat("&f[&7 보상수령 &c레벨제한 &7설정 &f]")));
    public static final ItemStack levelCommandHelp = result_LongType(Material.AMETHYST_SHARD, 1, (ColorUtils.chat(level + "명령어")));
    public static final ItemStack levelReward = result_LongType(Material.EXPERIENCE_BOTTLE, 1, (ColorUtils.chat(level + "레벨보상")));
    public static final ItemStack levelInfo = result_LongType(Material.NAME_TAG, 1, (ColorUtils.chat(level + "레벨정보")));
    public static final ItemStack levelActionBar = result_LongType(Material.BLAZE_ROD, 1, (ColorUtils.chat(level + "엑션바 활성화 및 비활성화")), (ColorUtils.chat("현재 엑션바가 켜져있는 가정아래, 한번 클릭하면 비활성화 한번 더 클릭하면 활성화 됩니다.")));
}