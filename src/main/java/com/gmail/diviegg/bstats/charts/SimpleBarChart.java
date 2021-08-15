package com.gmail.diviegg.bstats.charts;

import com.gmail.diviegg.bstats.json.JsonObjectBuilder;
import java.util.Map;
import java.util.concurrent.Callable;

public class SimpleBarChart extends CustomChart {
  private final Callable<Map<String, Integer>> callable;
  
  public SimpleBarChart(String chartId, Callable<Map<String, Integer>> callable) {
    super(chartId);
    this.callable = callable;
  }
  
  protected JsonObjectBuilder.JsonObject getChartData() throws Exception {
    JsonObjectBuilder valuesBuilder = new JsonObjectBuilder();
    Map<String, Integer> map = this.callable.call();
    if (map == null || map.isEmpty())
      return null; 
    for (Map.Entry<String, Integer> entry : map.entrySet()) {
      valuesBuilder.appendField(entry.getKey(), new int[] { ((Integer)entry.getValue()).intValue() });
    } 
    return (new JsonObjectBuilder())
      .appendField("values", valuesBuilder.build())
      .build();
  }
}
