package hawk.graphql_demo.repository;

import com.github.javafaker.Faker;
import hawk.graphql_demo.model.Address;
import hawk.graphql_demo.model.Transaction;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.LongStream;

@Repository
public class TransactionDB {
    private final Map<Long, Transaction> transactions = new ConcurrentHashMap<>();
    private final Faker faker = new Faker();

    public Map<Long, Transaction> getTransactionMap() {
        return transactions;
    }

    @PostConstruct
    void initDB() {
        LongStream.range(0, 100).boxed()
            .forEach(i -> transactions.put(i, Transaction.builder()
                .firstName(faker.name().firstName())
                .surname(faker.name().lastName())
                .beneficiaryAccountNumber(UUID.randomUUID().toString())
                .originatorAccountNumber(UUID.randomUUID().toString())
                .originatorAddress(Address.builder()
                    .city(faker.address().city())
                    .country(faker.address().country())
                    .street(faker.address().streetAddress())
                    .build())
                .beneficiaryAddress(Address.builder()
                    .city(faker.address().city())
                    .country(faker.address().country())
                    .street(faker.address().streetAddress())
                    .build())
                .settledAmount(BigDecimal.valueOf(ThreadLocalRandom.current().nextDouble(1, 1000)))
                .build()));
    }
}
