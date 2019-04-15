package ca.albertlockett.trading.graphql.datafetcher;

import org.ta4j.core.TimeSeries;

import ca.albertlockett.trading.dao.Data;
import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;

public class TimeSeriesDataFetcher implements DataFetcher<Object> {

  private Data dao = new Data();

  @Override
  public Object get(DataFetchingEnvironment environment) throws Exception {
    String from = environment.getArgument("from");
    String to = environment.getArgument("to");
    String symbol = environment.getArgument("symbol");
    TimeSeries series = dao.fetchBarData(from, to, symbol);
    return series.getBarData();
  }

}