package com.gmail.diviegg.Versions.Wrappers.Modern.General;

import com.gmail.diviegg.Versions.Wrappers.AbstractGeneralUtility;
import org.bukkit.Material;

public abstract class AbstractModernGeneral extends AbstractGeneralUtility {
  public boolean isInteractable(Material material) {
    if (material != null)
      return material.isInteractable(); 
    return false;
  }
}
