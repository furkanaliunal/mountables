package com.gmail.diviegg;

import com.gmail.diviegg.Commands.CommandBase;
import com.gmail.diviegg.Commands.CommandCall;
import com.gmail.diviegg.Commands.CommandReload;
import com.gmail.diviegg.Commands.TabComplete;
import com.gmail.diviegg.External.CombatLog.Listener.CombatLogListener;
import com.gmail.diviegg.Listeners.BlockPlace;
import com.gmail.diviegg.Listeners.InventoryClick;
import com.gmail.diviegg.Listeners.PlayerInteract;
import com.gmail.diviegg.Listeners.PlayerInteractEntity;
import com.gmail.diviegg.Listeners.VehicleExitEvent;
import com.gmail.diviegg.Versions.Wrappers.Legacy.Wrapper1_12;
import com.gmail.diviegg.Versions.Wrappers.Modern.Wrapper1_15;
import com.gmail.diviegg.Versions.Wrappers.Modern.Wrapper1_16;
import com.gmail.diviegg.Versions.Wrappers.Modern.Wrapper1_17;
import com.gmail.diviegg.Versions.Wrappers.VersionHandler;
import com.sk89q.worldguard.WorldGuard;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionDefault;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class PortableHorses extends JavaPlugin {
  private static VersionHandler versionHandler;
  
  private static PortableHorses ph;
  
  private static boolean hasWorldGuard = false;
  
  private static CommandBase commandBase;
  
  private final FileConfiguration config = getConfig();
  
  private boolean isServerLegacy = false;
  
  public static boolean hasWorldGuard() {
    return hasWorldGuard;
  }
  
  public static PortableHorses getPh() {
    return ph;
  }
  
  public static VersionHandler getVersionHandler() {
    return versionHandler;
  }
  
  public static CommandBase getCommandBase() {
    return commandBase;
  }
  
  public void onEnable() {
    checkWorldGuard();
    //Metrics metrics = new Metrics(this, 10593);
    ph = this;
    setupPerms();
    commandBase = new CommandBase();
    getCommand("PortableHorses").setExecutor(commandBase);
    commandBase.registerCommand("call", new CommandCall() {
        
        });
    commandBase.registerCommand("reload", new CommandReload());
    getCommand("PortableHorses").setTabCompleter(new TabComplete());
    getServer().getPluginManager().registerEvents(new CombatLogListener(), this);
    String version = Bukkit.getServer().getClass().getPackage().getName().split(Pattern.quote("."))[3];
    switch (version) {
      case "v1_17_R1":
        versionHandler = new Wrapper1_17();
        getLogger().info("Version " + version + " detected.");
        registerListeners();
        break;
      case "v1_16_R3":
        versionHandler = new Wrapper1_16();
        getLogger().info("Version " + version + " detected.");
        registerListeners();
        break;
      case "v1_15_R1":
        versionHandler = new Wrapper1_15();
        getLogger().info("Version " + version + " detected.");
        registerListeners();
        break;
      case "v1_13_R2":
      case "v1_12_R1":
        versionHandler = new Wrapper1_12();
        getLogger().info("Version " + version + " detected.");
        registerListeners();
        this.isServerLegacy = true;
        break;
      default:
        getLogger().severe("Version " + version + " detected.");
        getLogger().severe("Unsupported Server Version");
        Bukkit.getPluginManager().disablePlugin(this);
        break;
    } 
    configSetup();
    saveConfig();
  }
  
  private void registerListeners() {
    getServer().getPluginManager().registerEvents(new BlockPlace(), this);
    getServer().getPluginManager().registerEvents(new InventoryClick(), this);
    getServer().getPluginManager().registerEvents(new PlayerInteract(), this);
    getServer().getPluginManager().registerEvents(new PlayerInteractEntity(), this);
    getServer().getPluginManager().registerEvents(new VehicleExitEvent(), this);
  }
  
  private void checkWorldGuard() {
    try {
      WorldGuard.getInstance();
      hasWorldGuard = true;
    } catch (NoClassDefFoundError noClassDefFoundError) {}
  }
  
  private void configSetup() {
    String legacyHeader = this.isServerLegacy ? "\nLore override options are for 1.12.2/1.13 only\nValid Color names can be found here:https://minecraft.gamepedia.com/Formatting_codes (Use the name in the table eg: dark_aqua" : "";
    this.config.options().header("PermissionsDefault: determines whether players permissions are set to true by default.\nCombatCooldown (number): how quickly a player will be able to summon their horse after entering combat (0 for disabled).\nSummonCooldown (number): determines the delay for summoning a horse after it is put inside a saddle (0 for disabled).\nAutoMount (true/false): if true, players will automatically mount their horse on summon.\nCallDistance (number 1-30): max distance a horse can be called from in blocks\nMessagePrefix: if true, messages generated by this plugin, will have the prefix [Portable Horses]" + legacyHeader);
    List<String> horseColors = new ArrayList<>(Arrays.asList(new String[] { "BLACK/Black", "BROWN/Brown", "CHESTNUT/Chestnut", "CREAMY/Cream", "DARK_BROWN/Dark Brown", "GRAY/Gray", "WHITE/White" }));
    List<String> horseStyles = new ArrayList<>(Arrays.asList(new String[] { "BLACK_DOTS/Black Dots", "NONE/No Marking", "WHITE/White", "WHITE_DOTS/White Dots", "WHITEFIELD/Milky Splotches" }));
    this.config.addDefault("PermissionsDefault", Boolean.valueOf(true));
    this.config.addDefault("CombatCooldown", Integer.valueOf(0));
    this.config.addDefault("SummonCooldown", Double.valueOf(3.0D));
    this.config.addDefault("AutoMount", Boolean.valueOf(false));
    this.config.addDefault("CallDistance", Integer.valueOf(10));
    this.config.addDefault("MessagePrefix.Enable", Boolean.valueOf(false));
    this.config.addDefault("MessagePrefix.Text", "[Portable Horses]");
    if (this.isServerLegacy) {
      this.config.addDefault("TextColor", "dark_aqua");
      this.config.addDefault("StatColor", "green");
      this.config.addDefault("ShowType", Boolean.valueOf(false));
      this.config.addDefault("OverrideLore", Boolean.valueOf(false));
      this.config.addDefault("loreText.jumpHeight", "Jump Height");
      this.config.addDefault("loreText.blocks", "Blocks");
      this.config.addDefault("loreText.speed", "Speed");
      this.config.addDefault("loreText.style", "Style");
      this.config.addDefault("loreText.color", "Color");
      for (String s : horseColors)
        this.config.addDefault("loreText.HorseColors." + s.split("/")[0], s.split("/")[1]); 
      for (String s : horseStyles)
        this.config.addDefault("loreText.HorseStyles." + s.split("/")[0], s.split("/")[1]); 
    } 
    this.config.options().copyDefaults(true);
  }
  
  private void setupPerms() {
    PluginManager pm = Bukkit.getServer().getPluginManager();
    pm.addPermission(new Permission("portablehorses.call", getConfig().getBoolean("PermissionsDefault") ? PermissionDefault.TRUE : PermissionDefault.FALSE));
    pm.addPermission(new Permission("portablehorses.reload", this.config.getBoolean("PermissionsDefault") ? PermissionDefault.TRUE : PermissionDefault.FALSE));
    pm.addPermission(new Permission("portablehorses.use", this.config.getBoolean("PermissionsDefault") ? PermissionDefault.TRUE : PermissionDefault.FALSE));
    pm.addPermission(new Permission("portablehorses.create", this.config.getBoolean("PermissionsDefault") ? PermissionDefault.TRUE : PermissionDefault.FALSE));
  }
  
  public void onDisable() {}
}
