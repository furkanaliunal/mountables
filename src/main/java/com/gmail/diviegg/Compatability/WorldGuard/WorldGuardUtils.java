package com.gmail.diviegg.Compatability.WorldGuard;

import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldguard.LocalPlayer;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.protection.flags.Flag;
import com.sk89q.worldguard.protection.flags.StateFlag;
import com.sk89q.worldguard.protection.regions.RegionContainer;
import com.sk89q.worldguard.protection.regions.RegionQuery;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class WorldGuardUtils {
  public static boolean checkFlag(Location location, Player p, StateFlag flags) {
    if (p.isOp())
      return true; 
    WorldGuard wg = WorldGuard.getInstance();
    RegionContainer container = wg.getPlatform().getRegionContainer();
    RegionQuery query = container.createQuery();
    LocalPlayer lp = WorldGuardPlugin.inst().wrapPlayer(p);
    Object result = query.queryValue(BukkitAdapter.adapt(location), lp, (Flag)flags);
    return (result == null || 
      !result.equals(StateFlag.State.DENY));
  }
}
