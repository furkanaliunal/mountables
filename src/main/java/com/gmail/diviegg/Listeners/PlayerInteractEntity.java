package com.gmail.diviegg.Listeners;

import com.gmail.diviegg.Handlers.Localization;
import com.gmail.diviegg.PortableHorses;
import com.gmail.diviegg.Versions.Wrappers.AbstractHorseHandler;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.AbstractHorse;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffectType;

public class PlayerInteractEntity implements Listener {
  private final AbstractHorseHandler horseHandler = PortableHorses.getVersionHandler().getHorseHandler();
  
  @EventHandler
  public void onSaddleEquipUse(PlayerInteractEntityEvent ie) {
    Entity clicked = ie.getRightClicked();
    Player player = ie.getPlayer();
    ItemStack item = player.getInventory().getItemInMainHand();
    if (item.getType() == Material.SADDLE)
      if (this.horseHandler.checkSteerable(clicked)) {
        if (!this.horseHandler.hasSaddle(clicked) && this.horseHandler
          .containsHorse(item) && 
          !((LivingEntity)clicked).hasPotionEffect(PotionEffectType.INVISIBILITY)) {
          ie.setCancelled(true);
          player.sendMessage(Localization.getPrefix() + ChatColor.RED + Localization.getMessage(player, "saddleInUse"));
        } 
      } else if (clicked instanceof AbstractHorse && ((AbstractHorse)clicked)
        .getInventory().getSaddle() == null && this.horseHandler
        .containsHorse(item)) {
        ie.setCancelled(true);
        player.sendMessage(Localization.getPrefix() + ChatColor.RED + Localization.getMessage(player, "saddleInUse"));
      }  
  }
}
