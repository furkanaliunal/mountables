package com.gmail.diviegg.External.CombatLog;

import com.gmail.diviegg.External.CombatLog.Handler.CombatLog;

public class CombatLogBase {
  private static final CombatLog combatLog = new CombatLog();
  
  public static CombatLog getCombatLog() {
    return combatLog;
  }
}
