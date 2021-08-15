package com.gmail.diviegg.Versions.Wrappers.Legacy;

import com.gmail.diviegg.Versions.Wrappers.AbstractGeneralUtility;
import com.gmail.diviegg.Versions.Wrappers.AbstractHorseHandler;
import com.gmail.diviegg.Versions.Wrappers.Legacy.General.GeneralUtility1_12;
import com.gmail.diviegg.Versions.Wrappers.Legacy.HorseHandler.HorseHandler1_12;
import com.gmail.diviegg.Versions.Wrappers.VersionHandler;

public class Wrapper1_12 implements VersionHandler {
  HorseHandler1_12 horseHandler = new HorseHandler1_12();
  
  GeneralUtility1_12 generalUtility1_12 = new GeneralUtility1_12();
  
  public AbstractHorseHandler getHorseHandler() {
    return (AbstractHorseHandler)this.horseHandler;
  }
  
  public AbstractGeneralUtility getGeneralUtil() {
    return (AbstractGeneralUtility)this.generalUtility1_12;
  }
}
