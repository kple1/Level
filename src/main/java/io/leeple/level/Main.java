package io.leeple.level;

import io.leeple.level.Listener.CreateFile;
import io.leeple.level.Command.LevelCommand;
import io.leeple.level.Command.LevelRewardManager;
import io.leeple.level.Listener.GetLevelEXP;
import io.leeple.level.Listener.ItemClickCancel;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandExecutor;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static io.leeple.level.Data.PlayerData.config;
import static io.leeple.level.Data.PlayerData.playerFile;

public final class Main extends JavaPlugin implements CommandExecutor {

    private final Map<UUID, BukkitTask> actionBarTasks = new HashMap<>();
    public static Main plugin;
    private File uuidFolder;


    public void Plugins() {
        Bukkit.getPluginManager().registerEvents(new ItemClickCancel(), this);
        Bukkit.getPluginManager().registerEvents(new GetLevelEXP(), this);
        Bukkit.getPluginManager().registerEvents(new LevelRewardManager(), this);
        Bukkit.getPluginManager().registerEvents(new CreateFile(), this);
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
    public void createPlayerDefaults(Player player) {
        YamlConfiguration playerConfig = YamlConfiguration.loadConfiguration(playerFile);
        playerConfig.addDefault("Level", "1");
        playerConfig.addDefault("EXP", "0/10");
        playerConfig.options().copyDefaults(true);
        saveYamlConfiguration(player);
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
        }, 10L); // 0.1초(100 ticks) 후에 실행합니다. */
        // 갱신 작업을 저장해둡니다.
        actionBarTasks.put(player.getUniqueId(), task);
    }

    //저장
    public void saveYamlConfiguration(Player player) {
        try {
            config.save(playerFile);
            player.sendMessage(("정보가 저장 되었습니다"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public File getUuidFolder() {
        return uuidFolder;
    }
}