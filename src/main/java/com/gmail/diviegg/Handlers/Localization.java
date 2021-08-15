package com.gmail.diviegg.Handlers;

import com.gmail.diviegg.PortableHorses;
import java.text.MessageFormat;
import java.util.Arrays;
import java.util.Locale;
import java.util.ResourceBundle;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class Localization {
  public static String getMessage(Player p, String key) {
    Locale locale = new Locale(p.getLocale().split("_")[0], p.getLocale().split("_")[1].toUpperCase());
    ResourceBundle langBundle = ResourceBundle.getBundle("messages", locale);
    return langBundle.getString(key);
  }
  
  public static String getMessage(Player p, String key, String... args) {
    Locale locale = new Locale(p.getLocale().split("_")[0], p.getLocale().split("_")[1].toUpperCase());
    ResourceBundle langBundle = ResourceBundle.getBundle("messages", locale);
    return MessageFormat.format(langBundle.getString(key), Arrays.<String>stream(args).toArray());
  }
  
  public static String getPrefix() {
    String prefixText = (PortableHorses.getPh().getConfig().isString("MessagePrefix.Text") && !PortableHorses.getPh().getConfig().getString("MessagePrefix.Text").isEmpty()) ? (ChatColor.translateAlternateColorCodes('&', PortableHorses.getPh().getConfig().getString("MessagePrefix.Text")) + " ") : (ChatColor.GOLD + "[Portable Horses] ");
    return PortableHorses.getPh().getConfig().getBoolean("MessagePrefix.Enable") ? (
      prefixText + ChatColor.RESET) : "";
  }
}
