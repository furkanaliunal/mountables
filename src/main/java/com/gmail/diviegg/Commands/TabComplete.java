package com.gmail.diviegg.Commands;

import com.gmail.diviegg.PortableHorses;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.util.StringUtil;

public class TabComplete implements TabCompleter {
  CommandBase commandBase = PortableHorses.getCommandBase();
  
  public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
    Set<String> commandNames = this.commandBase.getCommands().keySet();
    List<String> completions = new ArrayList<>();
    StringUtil.copyPartialMatches(args[0], commandNames, completions);
    Collections.sort(completions);
    return completions;
  }
}
