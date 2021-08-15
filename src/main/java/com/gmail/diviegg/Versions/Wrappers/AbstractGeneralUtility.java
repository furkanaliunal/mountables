package com.gmail.diviegg.Versions.Wrappers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.bukkit.Material;

public abstract class AbstractGeneralUtility implements IGeneralUtiility {
  private final List<String> validColors = new ArrayList<>(Arrays.asList(new String[] { 
          "dark_red", "red", "gold", "yellow", "dark_green", "green", "aqua", "dark_aqua", "dark_blue", "blue", 
          "light_purple", "dark_purple", "white", "gray", "dark_gray", "black" }));
  
  public abstract boolean isInteractable(Material paramMaterial);
  
  public boolean isValidColor(String string) {
    if (string != null)
      return this.validColors.contains(string.toLowerCase()); 
    return false;
  }
}
