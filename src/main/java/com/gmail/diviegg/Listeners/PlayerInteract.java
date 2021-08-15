package com.gmail.diviegg.Listeners;

import com.gmail.diviegg.Compatability.WorldGuard.WorldGuardUtils;
import com.gmail.diviegg.External.CombatLog.CombatLogBase;
import com.gmail.diviegg.Handlers.Localization;
import com.gmail.diviegg.PortableHorses;
import com.gmail.diviegg.Versions.Wrappers.AbstractHorseHandler;
import com.gmail.diviegg.Versions.Wrappers.IGeneralUtiility;
import com.sk89q.worldguard.protection.flags.Flags;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

public class PlayerInteract implements Listener {
  private final AbstractHorseHandler horseHandler = PortableHorses.getVersionHandler().getHorseHandler();
  
  private final IGeneralUtiility generalUtility = (IGeneralUtiility)PortableHorses.getVersionHandler().getGeneralUtil();
  
  @EventHandler(priority = EventPriority.HIGHEST)
  public void onSaddleUse(PlayerInteractEvent e) {
    if (!this.horseHandler.containsHorse(e.getItem()))
      return; 
    Block clicked = e.getClickedBlock();
    Player p = e.getPlayer();
    boolean allowed = true;
    if (PortableHorses.hasWorldGuard() && 
      clicked != null)
      allowed = WorldGuardUtils.checkFlag(clicked.getLocation(), p, Flags.INTERACT); 
    if (e.getItem() == null || !this.horseHandler.containsHorse(e.getItem()))
      return; 
    if (clicked != null && allowed && 
      !this.generalUtility.isInteractable(clicked.getType())) {
      PlayerInventory playerInv = p.getInventory();
      if (p.hasPermission("portablehorses.use") && e
        .getAction() == Action.RIGHT_CLICK_BLOCK && p.getCooldown(Material.SADDLE) <= 0) {
        if (PortableHorses.getPh().getConfig().getInt("CombatCooldown") > 0 && 
          CombatLogBase.getCombatLog().isInCombat(p)) {
          if (CombatLogBase.getCombatLog().canWarn(p)) {
            p.sendMessage(Localization.getPrefix() + ChatColor.RED + Localization.getMessage(p, "inCombatWarning", new String[] { String.valueOf(CombatLogBase.getCombatLog().timeLeftInCombat(p)) }));
            CombatLogBase.getCombatLog().setLastWarning(p);
          } 
          return;
        } 
        if (this.horseHandler.unpackageHorse(e.getItem(), p.getWorld(), clicked.getLocation().add(0.0D, 1.0D, 0.0D), p))
          if (e.getHand() == EquipmentSlot.OFF_HAND) {
            playerInv.setItemInOffHand(new ItemStack(Material.AIR));
          } else {
            playerInv.remove(e.getItem());
          }  
      } 
    } 
  }
}
