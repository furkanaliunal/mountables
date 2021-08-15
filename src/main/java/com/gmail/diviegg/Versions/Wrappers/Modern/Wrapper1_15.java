package com.gmail.diviegg.Versions.Wrappers.Modern;

import com.gmail.diviegg.Versions.Wrappers.AbstractGeneralUtility;
import com.gmail.diviegg.Versions.Wrappers.AbstractHorseHandler;
import com.gmail.diviegg.Versions.Wrappers.Modern.General.GeneralUtility1_15;
import com.gmail.diviegg.Versions.Wrappers.Modern.HorseHandler.HorseHandler1_15;
import com.gmail.diviegg.Versions.Wrappers.VersionHandler;

public class Wrapper1_15 implements VersionHandler {
  HorseHandler1_15 horseHandler = new HorseHandler1_15();
  
  GeneralUtility1_15 generalUitility = new GeneralUtility1_15();
  
  public AbstractHorseHandler getHorseHandler() {
    return (AbstractHorseHandler)this.horseHandler;
  }
  
  public AbstractGeneralUtility getGeneralUtil() {
    return (AbstractGeneralUtility)this.generalUitility;
  }
}
