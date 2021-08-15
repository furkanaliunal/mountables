package com.gmail.diviegg.Versions.Wrappers.Modern.HorseHandler;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Steerable;

public class HorseHandler1_16 extends AbstractModernHorseHandler {
  public boolean checkSteerable(Entity e) {
    return e instanceof Steerable;
  }
  
  public boolean hasSaddle(Entity e) {
    return ((Steerable)e).hasSaddle();
  }
}
