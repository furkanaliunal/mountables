package com.gmail.diviegg.External.CombatLog.Handler;

import com.gmail.diviegg.PortableHorses;
import java.util.Date;
import java.util.HashMap;
import java.util.UUID;
import org.bukkit.entity.Player;

public class CombatLog {
  private static final int combatTime = PortableHorses.getPh().getConfig().getInt("CombatCooldown");
  
  HashMap<UUID, Long> inCombat = new HashMap<>();
  
  HashMap<UUID, Long> warnings = new HashMap<>();
  
  public void setInCombat(Player player) {
    this.inCombat.put(player.getUniqueId(), Long.valueOf((new Date()).getTime()));
  }
  
  public void setLastWarning(Player player) {
    this.warnings.put(player.getUniqueId(), Long.valueOf((new Date()).getTime()));
  }
  
  public boolean isInCombat(Player player) {
    if (this.inCombat.containsKey(player.getUniqueId())) {
      boolean result = ((new Date()).getTime() - ((Long)this.inCombat.get(player.getUniqueId())).longValue() < combatTime * 1000L);
      if (!result)
        this.inCombat.remove(player.getUniqueId()); 
      return result;
    } 
    return false;
  }
  
  public int timeLeftInCombat(Player player) {
    if (isInCombat(player))
      return combatTime - (int)Math.round(((new Date()).getTime() - ((Long)this.inCombat.get(player.getUniqueId())).longValue()) / 1000.0D); 
    return 0;
  }
  
  public boolean canWarn(Player player) {
    if (this.warnings.containsKey(player.getUniqueId()))
      return ((int)Math.round(((new Date()).getTime() - ((Long)this.warnings.get(player.getUniqueId())).longValue()) / 1000.0D) > 1); 
    return true;
  }
}
