package demo.graphql_demo.model.config;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
@Builder
@AllArgsConstructor
public class PassthroughConfig {
    private Long minNumber;
    private BigDecimal minAmount;
    private Long numberOfSmurfs;
    private Long timeRange;
    private String unit;
}
