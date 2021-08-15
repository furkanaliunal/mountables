package com.gmail.diviegg.Commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public abstract class SubCommand {
  public abstract void onCommand(CommandSender paramCommandSender, Command paramCommand, String[] paramArrayOfString);
  
  public abstract String getPermission();
}
