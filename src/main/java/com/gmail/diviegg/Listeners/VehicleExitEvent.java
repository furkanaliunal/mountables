package com.gmail.diviegg.Listeners;

import com.gmail.diviegg.Compatability.WorldGuard.WorldGuardUtils;
import com.gmail.diviegg.PortableHorses;
import com.gmail.diviegg.Versions.Wrappers.AbstractHorseHandler;
import com.sk89q.worldguard.protection.flags.Flags;
import java.util.HashMap;
import org.bukkit.Material;
import org.bukkit.entity.AbstractHorse;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Vehicle;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;

public class VehicleExitEvent implements Listener {
  private final AbstractHorseHandler horseHandler = PortableHorses.getVersionHandler().getHorseHandler();
  
  @EventHandler(priority = EventPriority.HIGHEST)
  public void onHorseDismount(org.bukkit.event.vehicle.VehicleExitEvent entityEvent) {
    Vehicle vehicle = entityEvent.getVehicle();
    LivingEntity exited = entityEvent.getExited();
    boolean allowed = true;
    if ((vehicle instanceof org.bukkit.entity.Horse || vehicle instanceof org.bukkit.entity.SkeletonHorse) && exited instanceof Player && exited
      .hasPermission("portablehorses.create") && ((AbstractHorse)vehicle)
      .getInventory().contains(Material.SADDLE) && 
      !vehicle.isDead() && !exited.isDead()) {
      if (PortableHorses.hasWorldGuard())
        allowed = WorldGuardUtils.checkFlag(exited.getLocation(), (Player)exited, Flags.RIDE); 
      if (allowed) {
        if (!(PortableHorses.getVersionHandler() instanceof com.gmail.diviegg.Versions.Wrappers.Legacy.Wrapper1_12)) {
          entityEvent.setCancelled(false);
          vehicle.eject();
        } 
        if (((AbstractHorse)vehicle).isLeashed()) {
          Entity leashHolder = ((AbstractHorse)vehicle).getLeashHolder();
          if (!(leashHolder instanceof Player))
            leashHolder.remove(); 
          vehicle.getWorld().dropItemNaturally(leashHolder.getLocation(), new ItemStack(Material.LEAD));
        } 
        for (PotionEffect pe : ((AbstractHorse)vehicle).getActivePotionEffects())
          ((AbstractHorse)vehicle).removePotionEffect(pe.getType()); 
        ItemStack saddle = this.horseHandler.packageHorse((AbstractHorse)vehicle);
        if (saddle != null) {
          if (PortableHorses.getPh().getConfig().getDouble("SummonCooldown") > 0.0D)
            ((Player)exited).setCooldown(Material.SADDLE, (int)(PortableHorses.getPh().getConfig().getDouble("SummonCooldown") * 20.0D)); 
          HashMap<Integer, ItemStack> e = ((Player)exited).getInventory().addItem(new ItemStack[] { saddle });
          if (!e.isEmpty())
            exited.getWorld().dropItem(exited.getLocation(), e.get(Integer.valueOf(0))); 
        } 
      } 
    } 
  }
}
