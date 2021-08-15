package com.gmail.diviegg.bstats.charts;

import com.gmail.diviegg.bstats.json.JsonObjectBuilder;
import java.util.Map;
import java.util.concurrent.Callable;

public class AdvancedPie extends CustomChart {
  private final Callable<Map<String, Integer>> callable;
  
  public AdvancedPie(String chartId, Callable<Map<String, Integer>> callable) {
    super(chartId);
    this.callable = callable;
  }
  
  protected JsonObjectBuilder.JsonObject getChartData() throws Exception {
    JsonObjectBuilder valuesBuilder = new JsonObjectBuilder();
    Map<String, Integer> map = this.callable.call();
    if (map == null || map.isEmpty())
      return null; 
    boolean allSkipped = true;
    for (Map.Entry<String, Integer> entry : map.entrySet()) {
      if (((Integer)entry.getValue()).intValue() == 0)
        continue; 
      allSkipped = false;
      valuesBuilder.appendField(entry.getKey(), ((Integer)entry.getValue()).intValue());
    } 
    if (allSkipped)
      return null; 
    return (new JsonObjectBuilder())
      .appendField("values", valuesBuilder.build())
      .build();
  }
}