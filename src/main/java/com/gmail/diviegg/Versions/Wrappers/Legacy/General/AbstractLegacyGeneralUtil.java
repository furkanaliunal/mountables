package com.gmail.diviegg.Versions.Wrappers.Legacy.General;

import com.gmail.diviegg.Versions.Wrappers.AbstractGeneralUtility;
import java.util.Arrays;
import java.util.List;
import org.bukkit.Material;

public abstract class AbstractLegacyGeneralUtil extends AbstractGeneralUtility {
  private static final List<String> interactables = Arrays.asList(new String[] { 
        "DOOR", "BUTTON", "LEVER", "BED", "CHEST", "DISPENSER", "BEACON", "WORKBENCH", "BOAT", "ENCHANTMENT_TABLE", 
        "FURNACE", "FENCE_GATE", "COMPARATOR", "DIODE", "DAYLIGHT" });
  
  public boolean isInteractable(Material material) {
    for (String s : interactables) {
      if (material.toString().contains(s))
        return true; 
    } 
    return false;
  }
}
