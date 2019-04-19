package ca.albertlockett.trading.graphql.datafetcher;

import ca.albertlockett.trading.dao.Data;
import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;

public class SymbolsDataFetcher implements DataFetcher<Object> {

  private Data dao = new Data();
  
  @Override
  public Object get(DataFetchingEnvironment environment) throws Exception {
    return dao.fectchAvailableSymobls();
  }


  public static DataFetcher<Object> name() {
    return env -> env.getSource();
  }
}