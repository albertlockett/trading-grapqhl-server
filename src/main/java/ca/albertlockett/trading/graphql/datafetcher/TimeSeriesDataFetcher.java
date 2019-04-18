package ca.albertlockett.trading.graphql.datafetcher;

import java.util.List;

import org.ta4j.core.Bar;
import org.ta4j.core.TimeSeries;

import ca.albertlockett.trading.dao.Data;
import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;

public class TimeSeriesDataFetcher implements DataFetcher<Object> {

  private Data dao = new Data();

  public static DataFetcher<Object> bars() {
    return env -> {
      TimeSeries series = env.getSource();
      return series.getBarData();
    };
  }

  public static DataFetcher<Object> inflectionPoints() {
    return env -> new InflectionPointsDataFetcher.Source(
      env.getSource(), 
      env.getArgument("localLowDays")
    );
  }

  @Override
  public Object get(DataFetchingEnvironment environment) throws Exception {
    String from = environment.getArgument("from");
    String to = environment.getArgument("to");
    String symbol = environment.getArgument("symbol");
    TimeSeries series = dao.fetchBarData(from, to, symbol);
    return series;
  }

}