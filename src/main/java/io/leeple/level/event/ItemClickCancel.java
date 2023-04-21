package io.leeple.level.event;

import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class ItemClickCancel implements Listener {

    @EventHandler
    public void CancelEvent(InventoryClickEvent event) {
        if(event.getClickedInventory() == null)
            return;
        if(ChatColor.stripColor(event.getView().getTitle()).equalsIgnoreCase("레벨보상")) {
            event.setCancelled(true);
        }
    }
}
