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
public class VolumeRiskConfig {
    private BigDecimal volumeThreshold;
    private String unit;
    private Long timeThreshold;
}
