package com.gmail.diviegg.Commands;

import com.gmail.diviegg.Handlers.Localization;
import com.gmail.diviegg.PortableHorses;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandReload extends SubCommand {
  public void onCommand(CommandSender sender, Command command, String[] args) {
    PortableHorses.getPh().reloadConfig();
    if (sender instanceof Player) {
      sender.sendMessage(Localization.getPrefix() + Localization.getMessage((Player)sender, "pluginReload"));
      return;
    } 
    sender.sendMessage("Plugin reloaded.");
  }
  
  public String getPermission() {
    return "reload";
  }
}
