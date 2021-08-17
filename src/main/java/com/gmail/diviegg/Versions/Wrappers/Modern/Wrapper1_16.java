package com.gmail.diviegg.Versions.Wrappers.Modern;

import com.gmail.diviegg.Versions.Wrappers.AbstractGeneralUtility;
import com.gmail.diviegg.Versions.Wrappers.AbstractHorseHandler;
import com.gmail.diviegg.Versions.Wrappers.Modern.General.GeneralUtility1_16;
import com.gmail.diviegg.Versions.Wrappers.Modern.HorseHandler.HorseHandler1_16;
import com.gmail.diviegg.Versions.Wrappers.VersionHandler;

public class Wrapper1_16 implements VersionHandler {
  GeneralUtility1_16 generalUtility = new GeneralUtility1_16();
  
  HorseHandler1_16 horseHandler = new HorseHandler1_16();
  
  public AbstractHorseHandler getHorseHandler() {
    return this.horseHandler;
  }
  
  public AbstractGeneralUtility getGeneralUtil() {
    return this.generalUtility;
  }
}
