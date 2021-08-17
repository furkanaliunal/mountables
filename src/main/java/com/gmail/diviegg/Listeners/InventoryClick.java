package com.gmail.diviegg.Listeners;

import com.gmail.diviegg.Handlers.Localization;
import com.gmail.diviegg.PortableHorses;
import com.gmail.diviegg.Versions.Wrappers.AbstractHorseHandler;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class InventoryClick implements Listener {
  private final AbstractHorseHandler horseHandler = PortableHorses.getVersionHandler().getHorseHandler();
  
  @EventHandler(priority = EventPriority.LOWEST)
  public void onSaddleEquipFromInventory(InventoryClickEvent e) {
    switch (e.getAction()) {
      case PLACE_ALL:
      case PLACE_ONE:
      case PLACE_SOME:
        if (e.getCursor() != null && e.getCursor().getType() == Material.SADDLE && e.getInventory().getHolder() instanceof org.bukkit.entity.AbstractHorse && this.horseHandler.containsHorse(e.getCursor())) {
          e.getWhoClicked().sendMessage(Localization.getPrefix() + ChatColor.RED + Localization.getMessage((Player)e.getWhoClicked(), "saddleInUse"));
          e.setCancelled(true);
          e.setResult(Event.Result.DENY);
        } 
        break;
      case MOVE_TO_OTHER_INVENTORY:
        if (e.getCurrentItem() != null && 
          e.getCurrentItem().getType() != Material.AIR && e.getInventory().getHolder() instanceof org.bukkit.entity.AbstractHorse && this.horseHandler
          .containsHorse(e.getCurrentItem())) {
          e.getWhoClicked().sendMessage(Localization.getPrefix() + ChatColor.RED + Localization.getMessage((Player)e.getWhoClicked(), "saddleInUse"));
          e.setCancelled(true);
        } 
        break;
    } 
  }
}
