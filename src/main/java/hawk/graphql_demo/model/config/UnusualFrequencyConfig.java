package hawk.graphql_demo.model.config;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
@AllArgsConstructor
public class UnusualFrequencyConfig {
    private Long numberOfPayments;
    private Long timeRange;
    private String unit;
}
