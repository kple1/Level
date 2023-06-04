package io.leeple.level.Listener;

import io.leeple.level.Main;
import io.leeple.level.Utils.ColorUtils;
import org.bukkit.Sound;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

import java.util.List;

import static io.leeple.level.Data.PlayerData.config;

public class GetLevelEXP implements Listener {

    private Main plugin = Main.getPlugin();
    private String levelPrefix = "&f[ &aLevel &f] ";
    private String levelUpPrefix = "&a[ Level UP! ] ";

    @EventHandler
    public void onEntityDeath(EntityDeathEvent event) {
        LivingEntity entity = event.getEntity();
        Player player = entity.getKiller();
        List<String> existingAnimals = plugin.getConfig().getStringList("setKillEntity");
        EntityType killEntityType = EntityType.valueOf(existingAnimals.toString().toUpperCase());

        if (entity.getType() != killEntityType) {
            return;
        }

        int maxLevel = plugin.getConfig().getInt("maxLevel");
        int level = config.getInt("Level");
        String expString = config.getString("EXP");
        String[] expSplit = expString.split("/");
        int currentExp = Integer.parseInt(expSplit[0]);
        int maxExp = Integer.parseInt(expSplit[1]);
        double multiple = plugin.getConfig().getDouble("expMultiple");
        int killEntityExp = plugin.getConfig().getInt("KillEntityExp");

        int newExp = (int) (currentExp + killEntityExp);

        if (level >= maxLevel) {
            player.sendMessage("최대레벨에 도달하였습니다.");
            return;
        }

        if (newExp >= maxExp) {
            int remainder = newExp % maxExp; // 나머지 EXP
            int quotient = newExp / maxExp; // 레벨 업 횟수
            level += quotient; // 레벨 업
            maxExp = (int) (maxExp * multiple); // 최대 EXP 업데이트
            currentExp = remainder; // 나머지 EXP를 새로운 currentExp로 지정
            player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1, 1);
            player.sendMessage(ColorUtils.chat(levelPrefix + "&c&ka &a" + level + "&f레벨이 되었습니다. &c&ka&f"));
            player.sendTitle(ColorUtils.chat(levelUpPrefix), "", 10, 70, 20);
        } else {
            currentExp = newExp; // 그 외에는 그대로 저장
        }

        config.set("Level", level);
        config.set("EXP", currentExp + "/" + maxExp);
        Main.getPlugin().noMessageSaveConfig();

        plugin.updateActionBar(player, ColorUtils.chat("[ &b" + player.getName() + "&f님의 레벨정보: " + "&aLevel&f: " + level + " / " + "&aEXP&f: " + currentExp + "/" + maxExp + " ]"));
    }
}
