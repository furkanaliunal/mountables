package com.gmail.diviegg.Commands;

import com.gmail.diviegg.Handlers.Localization;
import com.gmail.diviegg.PortableHorses;
import java.util.List;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.AbstractHorse;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

public class CommandCall extends SubCommand {
  public void onCommand(CommandSender sender, Command command, String[] args) {
    if (!(sender instanceof Player)) {
      sender.sendMessage("This command can only be used by players!");
      return;
    } 
    Player player = (Player)sender;
    int callDistance = PortableHorses.getPh().getConfig().getInt("CallDistance");
    callDistance = Math.min(callDistance, 30);
    List<Entity> nearby = player.getNearbyEntities(callDistance, callDistance, callDistance);
    for (Entity entity : nearby) {
      if ((entity instanceof org.bukkit.entity.Horse || entity instanceof org.bukkit.entity.SkeletonHorse) && (
        (AbstractHorse)entity).isTamed()) {
        if (!entity.getPassengers().isEmpty()) {
          player.sendMessage(Localization.getPrefix() + ChatColor.RED + Localization.getMessage(player, "horseHasPassenger"));
          return;
        } 
        entity.teleport(((Player)sender).getLocation().add(0.0D, 0.0D, 0.5D));
        return;
      } 
    } 
    player.sendMessage(Localization.getPrefix() + ChatColor.RED + Localization.getMessage(player, "horseTooFar"));
  }
  
  public String getPermission() {
    return "call";
  }
}
