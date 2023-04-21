package io.leeple.level;

import io.leeple.level.command.LevelCommand;
import io.leeple.level.event.GetLevelEXP;
import io.leeple.level.event.ItemClickCancel;
import io.leeple.level.event.RewardClick;
import io.leeple.level.utils.ColorUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public final class Main extends JavaPlugin implements Listener, CommandExecutor {

    private final Map<UUID, BukkitTask> actionBarTasks = new HashMap<>();
    public static Main plugin;
    private File uuidFolder;


    public void Plugins() {
        Bukkit.getPluginManager().registerEvents(new ItemClickCancel(), this);
        Bukkit.getPluginManager().registerEvents(new GetLevelEXP(), this);
        Bukkit.getPluginManager().registerEvents(new RewardClick(),this);
        Bukkit.getPluginManager().registerEvents(this, this);
    }

    public void Config() {
        saveConfig();
    }


    public void Command() {
        Bukkit.getPluginCommand("Level").setExecutor(new LevelCommand());
    }

    @Override
    public void onEnable() {
        plugin = this;
        Command();
        Config();
        Plugins();
        uuidFolder = new File(getDataFolder(), "UUIDs");
        if (!uuidFolder.exists()) {
            uuidFolder.mkdirs();
        }
    }

    @Override
    public void onDisable() {
        saveDefaultConfig();
        actionBarTasks.values().forEach(BukkitTask::cancel);
        actionBarTasks.clear();
    }

    public static Main getPlugin() {
        return plugin;
    }

    //플레이어의 uuid명으로 생성된 config에 레벨정보를 기본적으로 추가
    public void createPlayerDefaults(UUID uuid) {
        File playerFile = new File(uuidFolder, uuid.toString() + ".yml");
        YamlConfiguration playerConfig = YamlConfiguration.loadConfiguration(playerFile);
        playerConfig.addDefault("Level", "1");
        playerConfig.addDefault("EXP", "0/10");
        playerConfig.options().copyDefaults(true);
        saveYamlConfiguration(playerConfig, playerFile);
    }

    public void updateActionBar(Player player, String message) {
        // 기존 ActionBar 갱신 작업을 취소합니다.
        BukkitTask task = actionBarTasks.remove(player.getUniqueId());
        if (task != null) {
            task.cancel();
        }

        // ActionBar를 갱신합니다.
        player.sendActionBar(message);

        // 일정 시간(여기에서는 0.1초) 후에 다시 ActionBar를 갱신합니다.
        task = getServer().getScheduler().runTaskLater(this, () -> {
            updateActionBar(player, message);
        }, 10L); // 0.1초(100 ticks) 후에 실행합니다.

        // 갱신 작업을 저장해둡니다.
        actionBarTasks.put(player.getUniqueId(), task);
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        UUID uuid = player.getUniqueId();
        File playerFile = new File(uuidFolder, uuid.toString() + ".yml");
        if (!playerFile.exists()) {
            createPlayerDefaults(uuid);
        }
        YamlConfiguration playerConfig = YamlConfiguration.loadConfiguration(playerFile);
        String Level = playerConfig.getString("Level");
        String EXP = playerConfig.getString("EXP");
        String message = (ColorUtils.chat("[ &b" + player.getName() + "&f님의 레벨정보: " + "&aLevel&f: " + Level + " / " + "&aEXP&f: " + EXP + " ]"));
        updateActionBar(player, message);
    }

    //저장
    public void saveYamlConfiguration(YamlConfiguration config, File file) {
        try {
            config.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public File getUuidFolder() {
        return uuidFolder;
    }
}