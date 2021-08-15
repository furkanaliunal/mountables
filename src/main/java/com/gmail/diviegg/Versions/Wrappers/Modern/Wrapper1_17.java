package com.gmail.diviegg.Versions.Wrappers.Modern;

import com.gmail.diviegg.Versions.Wrappers.AbstractGeneralUtility;
import com.gmail.diviegg.Versions.Wrappers.AbstractHorseHandler;
import com.gmail.diviegg.Versions.Wrappers.Modern.General.GeneralUtility1_17;
import com.gmail.diviegg.Versions.Wrappers.Modern.HorseHandler.HorseHandler1_17;
import com.gmail.diviegg.Versions.Wrappers.VersionHandler;

public class Wrapper1_17 implements VersionHandler {
  GeneralUtility1_17 generalUtility = new GeneralUtility1_17();
  
  HorseHandler1_17 horseHandler = new HorseHandler1_17();
  
  public AbstractHorseHandler getHorseHandler() {
    return (AbstractHorseHandler)this.horseHandler;
  }
  
  public AbstractGeneralUtility getGeneralUtil() {
    return (AbstractGeneralUtility)this.generalUtility;
  }
}
