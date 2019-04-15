package ca.albertlockett.trading.graphql.datafetcher;

import org.ta4j.core.Bar;

import graphql.schema.DataFetcher;

public class BarFieldDataFetchers {

  public static DataFetcher<Object> close() {
    return env -> {
      Bar bar = env.getSource();
      return bar.getClosePrice().toString();
    };
  }

  public static DataFetcher<Object> open() {
    return env -> {
      Bar bar = env.getSource();
      return bar.getOpenPrice().toString();
    };
  }

  public static DataFetcher<Object> high() {
    return env -> {
      Bar bar = env.getSource();
      return bar.getMaxPrice().toString();
    };
  }

  public static DataFetcher<Object> low() {
    return env -> {
      Bar bar = env.getSource();
      return bar.getMinPrice().toString();
    };
  }

  public static DataFetcher<Object> volume() {
    return env -> {
      Bar bar = env.getSource();
      return bar.getVolume().toString();
    };
  }

  public static DataFetcher<Object> date() {
    return env -> {
      Bar bar = env.getSource();
      return bar.getDateName();
    };
  }
}