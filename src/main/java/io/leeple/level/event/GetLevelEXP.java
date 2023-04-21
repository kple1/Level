package io.leeple.level.event;

import io.leeple.level.Main;
import io.leeple.level.utils.ColorUtils;
import org.bukkit.Sound;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Pig;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

public class GetLevelEXP implements Listener {

    Main plugin = Main.getPlugin();
    String Level = "&f[ &aLevel &f] ";
    String LevelUP = "&a[ Level UP! ] ";

    @EventHandler
    public void onEntityDeath(EntityDeathEvent event) {

        LivingEntity entity = event.getEntity();
        Player player = entity.getKiller();

        if (!(entity instanceof Pig) || player == null) {
            return;
        }

        //UUID 파일 가져오기
        UUID uuid = player.getUniqueId();
        File playerFile = new File(plugin.getUuidFolder(), uuid.toString() + ".yml");

        //UUID 파일이 없으면 생성
        if (!playerFile.exists()) {
            try {
                playerFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if (entity instanceof Pig) {
            player.sendMessage(ColorUtils.chat(Level + "돼지를 죽여서 3XP를 획득하셨습니다."));
        }

        FileConfiguration playerConfig = YamlConfiguration.loadConfiguration(playerFile);
        int level = playerConfig.getInt("Level", 1);
        String expString = playerConfig.getString("EXP");
        String[] expSplit = expString.split("/");
        int currentExp = Integer.parseInt(expSplit[0]);
        int maxExp = Integer.parseInt(expSplit[1]);

        int newExp = currentExp + 3;
        float multiplier = 1.1f;

        if (newExp >= maxExp) {
            int remainder = newExp % maxExp; // 나머지 EXP
            int quotient = newExp / maxExp; // 레벨 업 횟수
            level += quotient; // 레벨 업
            maxExp = (int) (maxExp * multiplier); // 최대 EXP 업데이트
            currentExp = remainder; // 나머지 EXP를 새로운 currentExp로 지정
            player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1, 1);
            player.sendMessage(ColorUtils.chat(Level + "&c&ka &a" + level + "&f레벨이 되었습니다. &c&ka&f"));
            player.sendTitle(ColorUtils.chat(LevelUP), "", 10, 70, 20);
        } else {
            currentExp = newExp; // 그 외에는 그대로 저장
        }

        playerConfig.set("Level", level);
        playerConfig.set("EXP", currentExp + "/" + maxExp);
        try {
            playerConfig.save(playerFile);
        } catch (IOException e) {
            e.printStackTrace();
        }

        plugin.updateActionBar(player, ColorUtils.chat("[ &b" + player.getName() + "&f님의 레벨정보: " + "&aLevel&f: " + level + " / " + "&aEXP&f: " + currentExp + "/" + maxExp + " ]"));
    }
}