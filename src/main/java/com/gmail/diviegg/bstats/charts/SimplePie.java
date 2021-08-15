package com.gmail.diviegg.bstats.charts;

import com.gmail.diviegg.bstats.json.JsonObjectBuilder;
import java.util.concurrent.Callable;

public class SimplePie extends CustomChart {
  private final Callable<String> callable;
  
  public SimplePie(String chartId, Callable<String> callable) {
    super(chartId);
    this.callable = callable;
  }
  
  protected JsonObjectBuilder.JsonObject getChartData() throws Exception {
    String value = this.callable.call();
    if (value == null || value.isEmpty())
      return null; 
    return (new JsonObjectBuilder())
      .appendField("value", value)
      .build();
  }
}
