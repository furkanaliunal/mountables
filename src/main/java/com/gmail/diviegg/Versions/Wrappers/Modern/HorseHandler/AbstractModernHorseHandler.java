package com.gmail.diviegg.Versions.Wrappers.Modern.HorseHandler;

import com.gmail.diviegg.Handlers.ItemStackHandler;
import com.gmail.diviegg.PortableHorses;
import com.gmail.diviegg.Versions.Wrappers.AbstractHorseHandler;
import java.util.UUID;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.World;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.AbstractHorse;
import org.bukkit.entity.AnimalTamer;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Horse;
import org.bukkit.entity.Player;
import org.bukkit.entity.SkeletonHorse;
import org.bukkit.inventory.HorseInventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffectType;

public abstract class AbstractModernHorseHandler extends AbstractHorseHandler {
  private final Plugin ph = PortableHorses.getPlugin(PortableHorses.class);
  
  private final NamespacedKey hType = new NamespacedKey(this.ph, "hType");
  
  private final NamespacedKey hName = new NamespacedKey(this.ph, "hName");
  
  private final NamespacedKey hOwnerID = new NamespacedKey(this.ph, "hOwner");
  
  private final NamespacedKey hArmor = new NamespacedKey(this.ph, "hArmor");
  
  private final NamespacedKey hAppearance = new NamespacedKey(this.ph, "hAppearance");
  
  private final NamespacedKey hColor = new NamespacedKey(this.ph, "hColor");
  
  private final NamespacedKey hMaxHealth = new NamespacedKey(this.ph, "hMaxHealth");
  
  private final NamespacedKey hCurrentHealth = new NamespacedKey(this.ph, "hCurrentHealth");
  
  private final NamespacedKey hJumpStrength = new NamespacedKey(this.ph, "hJumpStrength");
  
  private final NamespacedKey hSpeed = new NamespacedKey(this.ph, "hSpeed");
  
  public ItemStack packageHorse(AbstractHorse horse) {
    ItemStack saddlePackage = new ItemStack(Material.SADDLE);
    ItemMeta saddleMeta = saddlePackage.getItemMeta();
    PersistentDataContainer cont = saddleMeta.getPersistentDataContainer();
    if (horse.getCustomName() != null)
      cont.set(this.hName, PersistentDataType.STRING, horse.getCustomName()); 
    if (horse.getOwner() != null)
      cont.set(this.hOwnerID, PersistentDataType.STRING, horse.getOwner().getUniqueId().toString()); 
    if (horse instanceof Horse) {
      HorseInventory hInv = ((Horse)horse).getInventory();
      if (hInv.getArmor() != null)
        cont.set(this.hArmor, PersistentDataType.STRING, ItemStackHandler.ItemStackToString(hInv.getArmor())); 
      cont.set(this.hAppearance, PersistentDataType.STRING, ((Horse)horse).getStyle().toString());
      cont.set(this.hColor, PersistentDataType.STRING, ((Horse)horse).getColor().toString());
      cont.set(this.hType, PersistentDataType.STRING, "Normal");
    } else {
      cont.set(this.hType, PersistentDataType.STRING, "Skeleton");
    } 
    cont.set(this.hMaxHealth, PersistentDataType.DOUBLE, Double.valueOf(horse.getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue()));
    cont.set(this.hCurrentHealth, PersistentDataType.DOUBLE, Double.valueOf(horse.getHealth()));
    cont.set(this.hJumpStrength, PersistentDataType.DOUBLE, Double.valueOf(horse.getJumpStrength()));
    cont.set(this.hSpeed, PersistentDataType.DOUBLE, Double.valueOf(horse.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).getBaseValue()));
    saddlePackage.setItemMeta(saddleMeta);
    saddlePackage = setSaddleNBT(horse, saddlePackage);
    horse.remove();
    if (!horse.isValid())
      return saddlePackage; 
    return null;
  }
  
  public boolean unpackageHorse(ItemStack saddle, World world, Location loc, Player player) {
    if (saddle.getType() == Material.SADDLE) {
      ItemMeta saddleMeta = saddle.getItemMeta();
      PersistentDataContainer cont = null;
      if (saddleMeta != null)
        cont = saddleMeta.getPersistentDataContainer(); 
      ItemStack freshSaddle = new ItemStack(Material.SADDLE);
      if (cont != null && cont.has(this.hType, PersistentDataType.STRING)) {
        String type = cont.get(this.hType, PersistentDataType.STRING);
        if (type != null) {
          Class vehicle = type.equals("Normal") ? Horse.class : SkeletonHorse.class;
          PersistentDataContainer finalCont = cont;
          AbstractHorse packaged = (AbstractHorse)world.spawn(loc, vehicle, horse -> {
                AbstractHorse builder = (AbstractHorse)horse;
                builder.addPotionEffect(PotionEffectType.INVISIBILITY.createEffect(999, 1));
                if (finalCont.has(this.hName, PersistentDataType.STRING))
                  horse.setCustomName(finalCont.get(this.hName, PersistentDataType.STRING));
                if (finalCont.has(this.hOwnerID, PersistentDataType.STRING))
                  builder.setOwner(Bukkit.getPlayer(UUID.fromString(finalCont.get(this.hOwnerID, PersistentDataType.STRING))));
                if (finalCont.has(this.hArmor, PersistentDataType.STRING))
                  try {
                    ItemStack armor = ItemStackHandler.ItemStackFromString(finalCont.get(this.hArmor, PersistentDataType.STRING));
                    ((Horse)builder).getInventory().setArmor(armor);
                  } catch (Exception e) {
                    ((Horse)horse).getInventory().setArmor(new ItemStack(Material.valueOf(finalCont.get(this.hArmor, PersistentDataType.STRING))));
                  }  
                if (finalCont.has(this.hColor, PersistentDataType.STRING))
                  ((Horse)builder).setColor(Horse.Color.valueOf(finalCont.get(this.hColor, PersistentDataType.STRING)));
                if (finalCont.has(this.hAppearance, PersistentDataType.STRING))
                  ((Horse)builder).setStyle(Horse.Style.valueOf(finalCont.get(this.hAppearance, PersistentDataType.STRING)));
                getStatsFromContainer(builder, finalCont);
                builder.setTamed(true);
                builder.getInventory().setSaddle(freshSaddle);
              });
          if (isColliding(packaged))
            return false; 
          if (this.ph.getConfig().getBoolean("AutoMount"))
            packaged.addPassenger(player);
          packaged.removePotionEffect(PotionEffectType.INVISIBILITY);
          return true;
        } 
      } 
      return false;
    } 
    return false;
  }
  
  public boolean containsHorse(ItemStack saddle) {
    if (saddle != null && 
      saddle.getItemMeta() != null)
      return saddle.getItemMeta().getPersistentDataContainer().has(this.hType, PersistentDataType.STRING); 
    return false;
  }
  
  public boolean checkSteerable(Entity e) {
    return false;
  }
  
  public boolean hasSaddle(Entity e) {
    return false;
  }
  
  public void getStatsFromContainer(AbstractHorse h, PersistentDataContainer cont) {
    Double jumpStrength = cont.has(this.hJumpStrength, PersistentDataType.DOUBLE) ? cont.get(this.hJumpStrength, PersistentDataType.DOUBLE) : Double.valueOf(0.0D);
    Double maxHealth = cont.has(this.hMaxHealth, PersistentDataType.DOUBLE) ? cont.get(this.hMaxHealth, PersistentDataType.DOUBLE) : Double.valueOf(0.0D);
    Double currentHealth = cont.has(this.hCurrentHealth, PersistentDataType.DOUBLE) ? cont.get(this.hCurrentHealth, PersistentDataType.DOUBLE) : Double.valueOf(0.0D);
    Double speed = cont.has(this.hSpeed, PersistentDataType.DOUBLE) ? cont.get(this.hSpeed, PersistentDataType.DOUBLE) : Double.valueOf(0.0D);
    setHorseStats(h, jumpStrength, maxHealth, currentHealth, speed);
  }
}
