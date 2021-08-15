package com.gmail.diviegg.Commands;

import com.gmail.diviegg.Handlers.Localization;
import java.util.HashMap;
import java.util.Map;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandBase implements CommandExecutor {
  private final Map<String, SubCommand> commands = new HashMap<>();
  
  public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
    if (args.length < 1)
      return true; 
    if (!this.commands.containsKey(args[0].toLowerCase())) {
      if (sender instanceof Player)
        sender.sendMessage(Localization.getPrefix() + ChatColor.RED + Localization.getMessage((Player)sender, "commandNotFound")); 
      return true;
    } 
    if (sender instanceof org.bukkit.command.ConsoleCommandSender || sender.isOp() || sender.hasPermission("portablehorses." + ((SubCommand)this.commands.get(args[0].toLowerCase())).getPermission()))
      ((SubCommand)this.commands.get(args[0].toLowerCase())).onCommand(sender, command, args); 
    return true;
  }
  
  public void registerCommand(String cmd, SubCommand subCommand) {
    this.commands.put(cmd, subCommand);
  }
  
  public Map<String, SubCommand> getCommands() {
    return this.commands;
  }
}
