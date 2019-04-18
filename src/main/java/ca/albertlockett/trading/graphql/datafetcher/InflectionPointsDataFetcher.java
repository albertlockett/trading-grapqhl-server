package ca.albertlockett.trading.graphql.datafetcher;

import java.util.stream.Collectors;

import org.ta4j.core.Bar;
import org.ta4j.core.TimeSeries;

import ca.albertlockett.trading.trends.InflectionPointFinder;
import graphql.schema.DataFetcher;

public class InflectionPointsDataFetcher {

  public static class Source {
    public final TimeSeries series; 
    public final Integer localLowDays;

    public Source(TimeSeries series, Integer localLowDays) {
      this.series = series;
      this.localLowDays = localLowDays;
    }
  }

  public static class InflectionPoint {
    public final Bar bar;
    public final Integer index;

    public InflectionPoint(Bar bar, Integer index) {
      this.bar = bar;
      this.index = index;
    }
  }

  public static DataFetcher<Object> resistance() {
    return env -> {
      Source source = env.getSource();
      return new InflectionPointFinder(source.series, false, source.localLowDays)
        .find()
        .stream()
        .map(i -> new InflectionPoint(source.series.getBar(i), i))
        .collect(Collectors.toList());
    };
  }

  public static DataFetcher<Object> supports() {
    return env -> {
      Source source = env.getSource();
      return new InflectionPointFinder(source.series, true, source.localLowDays)
        .find()
        .stream()
        .map(i -> new InflectionPoint(source.series.getBar(i), i))
        .collect(Collectors.toList());
    };
  }
}