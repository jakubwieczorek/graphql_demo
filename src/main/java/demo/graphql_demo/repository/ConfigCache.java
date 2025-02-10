package demo.graphql_demo.repository;

import demo.graphql_demo.model.config.Config;
import demo.graphql_demo.model.config.PassthroughConfig;
import demo.graphql_demo.model.config.UnusualFrequencyConfig;
import demo.graphql_demo.model.config.VolumeRiskConfig;
import jakarta.annotation.PostConstruct;
import lombok.Getter;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.temporal.TemporalUnit;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.IntStream;
import java.util.stream.LongStream;

@Repository
public class ConfigCache {
    @Getter
    private final Map<Integer, Config> configs = new ConcurrentHashMap<>();

    private final List<String> units = Duration.ZERO.getUnits().stream().map(TemporalUnit::toString).toList();

    @PostConstruct
    void initDB() {
        IntStream.range(0, 10).boxed()
            .forEach(i -> configs.put(i, Config.builder()
                .version(ThreadLocalRandom.current().nextLong(1, 100))
                .passthrough(buildPassthroughs())
                .volumeRisk(buildVolumeRisk())
                    .unusualFrequency(buildUnusualFrequency())
                .build()));
    }

    private List<UnusualFrequencyConfig> buildUnusualFrequency() {
        return LongStream.range(0, 5).boxed()
            .map(it -> UnusualFrequencyConfig.builder()
                .numberOfPayments(ThreadLocalRandom.current().nextLong(3, 20))
                .timeRange(ThreadLocalRandom.current().nextLong(1, 100))
                .unit(units.get(ThreadLocalRandom.current().nextInt(0, units.size() - 1)))
                .build())
            .toList();
    }

    private List<VolumeRiskConfig> buildVolumeRisk() {
        return LongStream.range(0, 5).boxed()
            .map(it -> VolumeRiskConfig.builder()
                .unit(units.get(ThreadLocalRandom.current().nextInt(0, units.size() - 1)))
                .volumeThreshold(BigDecimal.valueOf(ThreadLocalRandom.current().nextDouble(0, 1000)))
                .timeThreshold(ThreadLocalRandom.current().nextLong(3, 60))
                .build())
            .toList();
    }

    private List<PassthroughConfig> buildPassthroughs() {
        return LongStream.range(0, 5).boxed()
            .map(it -> PassthroughConfig.builder()
                .unit(units.get(ThreadLocalRandom.current().nextInt(0, units.size() - 1)))
                .minAmount(BigDecimal.valueOf(ThreadLocalRandom.current().nextDouble(0, 1000)))
                .minNumber(ThreadLocalRandom.current().nextLong(3, 20))
                .timeRange(ThreadLocalRandom.current().nextLong(1, 100))
                .numberOfSmurfs(ThreadLocalRandom.current().nextLong(1, 10))
                .build())
            .toList();
    }
}
