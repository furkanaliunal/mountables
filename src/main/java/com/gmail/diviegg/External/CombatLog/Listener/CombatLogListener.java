package com.gmail.diviegg.External.CombatLog.Listener;

import com.gmail.diviegg.External.CombatLog.CombatLogBase;
import com.gmail.diviegg.External.CombatLog.Handler.CombatLog;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;

public class CombatLogListener implements Listener {
  CombatLog combatLog = CombatLogBase.getCombatLog();
  
  @EventHandler
  public void CLEnterCombatEvent(EntityDamageByEntityEvent damageByEntityEvent) {
    Player player = null;
    Entity damaged = damageByEntityEvent.getEntity();
    Entity attacker = damageByEntityEvent.getDamager();
    if (damageByEntityEvent.getCause().equals(EntityDamageEvent.DamageCause.PROJECTILE)) {
      if (!(((Projectile)attacker).getShooter() instanceof Player))
        return; 
      player = (Player)((Projectile)attacker).getShooter();
    } 
    if (damaged instanceof Player && player instanceof Player) {
      this.combatLog.setInCombat((Player)damaged);
      this.combatLog.setInCombat(player);
    } 
  }
  
  public CombatLog getCombatLog() {
    return this.combatLog;
  }
}
