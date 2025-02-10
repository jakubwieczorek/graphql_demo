package demo.graphql_demo.web;

import demo.graphql_demo.model.config.Config;
import demo.graphql_demo.model.transaction.Transaction;
import demo.graphql_demo.repository.ConfigCache;
import demo.graphql_demo.repository.TransactionDB;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

/**
 * 1. User provides a list of fields.
 * 2. Calls backend (proxy)
 * 3. backend calls csv-exporter
 * Two options:
 * 1. csv-exporter fetches requested fields from backend
 *  a. Backend exposes /graphql API
 *  b. csv-exporter forwards query prepared in FE
 * 2. csv-exporter fetches all fields and filters itself
 *  a. csv-exporter exposes /graphql API. csv-exporter gets in backend data model domain.
 *     Not good.
 *
 * Why I would use graphql:
 * 1. We don't reinvent a wheel
 *      fetching inner fields is ot so easy in ObjectNode
 * 2. Opened for changes: what if sb wants to add config version for every transaction?
 * 3. Schema validation
 *
 * Why not:
 * 1. Maintaining the schema. I think there is a tool which automatically generates schema in runtime
 * */
@Controller
@RequiredArgsConstructor
public class GraphQLController {
    private final TransactionDB transactionDB;
    private final ConfigCache configCache;

    @QueryMapping
    public List<Transaction> getTransactions() {
        return transactionDB.getTransactions().values().stream().toList();
    }

    @QueryMapping
    // No additional endpoints
    // Schema maintenance. Bad and good. Bad because of maintenance, good because FE can use it
    public Config getConfig(@Argument Integer configVersion) {
        return configCache.getConfigs().get(configVersion);
    }
}
