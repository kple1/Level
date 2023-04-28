package io.leeple.level.event;

import io.leeple.level.Main;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChatEvent;

public class ChatListener implements Listener {

    private final Main plugin;
    private final Player player;
    private final int slot;

    public ChatListener(Player player, int slot) {
        this.plugin = Main.getPlugin();
        this.player = player;
        this.slot = slot;
    }

    @EventHandler
    public void onChat(PlayerChatEvent event) {
        // 채팅 메시지가 유효한 경우에만 처리 ㅋ
        String message = event.getMessage();
        if (message != null && !message.isEmpty()) {
            // config에 아이템 이름을 설정 ㅋ
            plugin.getConfig().set("reward." + slot + ".itemName", message);
            plugin.saveConfig();
            player.sendMessage("이름 설정이 완료되었습니다.");
        }
        // 채팅 이벤트를 제거 ㅋ
        HandlerList.unregisterAll(this);
        event.setCancelled(true);
    }
}