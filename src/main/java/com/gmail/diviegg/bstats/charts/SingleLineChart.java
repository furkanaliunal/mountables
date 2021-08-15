package com.gmail.diviegg.bstats.charts;

import com.gmail.diviegg.bstats.json.JsonObjectBuilder;
import java.util.concurrent.Callable;

public class SingleLineChart extends CustomChart {
  private final Callable<Integer> callable;
  
  public SingleLineChart(String chartId, Callable<Integer> callable) {
    super(chartId);
    this.callable = callable;
  }
  
  protected JsonObjectBuilder.JsonObject getChartData() throws Exception {
    int value = ((Integer)this.callable.call()).intValue();
    if (value == 0)
      return null; 
    return (new JsonObjectBuilder())
      .appendField("value", value)
      .build();
  }
}
