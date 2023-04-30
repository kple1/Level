package io.leeple.level.Listener;

import io.leeple.level.Main;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChatEvent;

public class ChatListener implements Listener {

    private final Main plugin;
    private final Player player;
    private int slot;

    public ChatListener(Player player, int slot) {
        this.plugin = Main.getPlugin();
        this.player = player;
        this.slot = slot;
    }

    @EventHandler
    public void onChat(PlayerChatEvent event) {
        String message = event.getMessage();
        if (message != null && !message.isEmpty()) {
            plugin.getConfig().set("reward." + slot + ".itemName", message);
            plugin.saveConfig();
            player.sendMessage("이름 설정이 완료되었습니다.");
        }

        // 이벤트 핸들러를 제거합니다.
        HandlerList.unregisterAll(this);
        event.setCancelled(true);
    }
}