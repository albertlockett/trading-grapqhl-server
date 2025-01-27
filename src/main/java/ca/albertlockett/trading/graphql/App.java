package ca.albertlockett.trading.graphql;

import java.io.File;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.util.ResourceUtils;

import ca.albertlockett.trading.graphql.datafetcher.BarFieldDataFetchers;
import ca.albertlockett.trading.graphql.datafetcher.InflectionPointsDataFetcher;
import ca.albertlockett.trading.graphql.datafetcher.SymbolsDataFetcher;
import ca.albertlockett.trading.graphql.datafetcher.TimeSeriesDataFetcher;
import graphql.Scalars;
import graphql.schema.GraphQLSchema;
import graphql.schema.idl.RuntimeWiring;
import graphql.schema.idl.SchemaGenerator;
import graphql.schema.idl.SchemaParser;
import graphql.schema.idl.TypeDefinitionRegistry;

@SpringBootApplication
public class App {

  public static void main(String[] args) {
    SpringApplication.run(App.class, args);
  }

  @Bean
  GraphQLSchema schema() throws Exception {
    SchemaParser schemaParser = new SchemaParser();
    SchemaGenerator schemaGenerator = new SchemaGenerator();
    // File schemaFile = new File(this.getClass().getClassLoader().getResource("schema.grapqhl").toString());
    File schemaFile = ResourceUtils.getFile("classpath:schema.graphql");
    TypeDefinitionRegistry typeRegistry = schemaParser.parse(schemaFile);

    RuntimeWiring wiring = RuntimeWiring.newRuntimeWiring()
        .scalar(Scalars.GraphQLLong)
        .type("Query", typeWiring -> typeWiring
          .dataFetcher("symbols", new SymbolsDataFetcher())
          .dataFetcher("timeSeries", new TimeSeriesDataFetcher()))


        .type("Bar", typeWiring -> typeWiring
          .dataFetcher("open", BarFieldDataFetchers.open())
          .dataFetcher("close", BarFieldDataFetchers.close())
          .dataFetcher("volume", BarFieldDataFetchers.volume())
          .dataFetcher("high", BarFieldDataFetchers.high())
          .dataFetcher("low", BarFieldDataFetchers.low())
          .dataFetcher("date", BarFieldDataFetchers.date()))

        .type("BarDate", typeWiring -> typeWiring
          .dataFetcher("startTimestamp", BarFieldDataFetchers.dateStartTimestamp())
          .dataFetcher("endTimestamp", BarFieldDataFetchers.dateEndTimestamp())
          .dataFetcher("name", BarFieldDataFetchers.dateName())
          .dataFetcher("simpleName", BarFieldDataFetchers.dateSimpleName()))

        .type("InflectionPoints", typeWiring -> typeWiring
          .dataFetcher("resistance", InflectionPointsDataFetcher.resistance())
          .dataFetcher("supports", InflectionPointsDataFetcher.supports()))

        .type("Symbol", typeWiring -> typeWiring
          .dataFetcher("name", SymbolsDataFetcher.name()))
        
        .type("TimeSeries", typeWiring -> typeWiring
          .dataFetcher("bars", TimeSeriesDataFetcher.bars())
          .dataFetcher("inflectionPoints", TimeSeriesDataFetcher.inflectionPoints()))

      .build();
      return schemaGenerator.makeExecutableSchema(typeRegistry, wiring);
  }
}