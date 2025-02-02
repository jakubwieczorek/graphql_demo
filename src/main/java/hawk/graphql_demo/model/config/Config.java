package hawk.graphql_demo.model.config;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@Builder
@AllArgsConstructor
public class Config {
    private Long version;
    private List<VolumeRiskConfig> volumeRisk;
    private List<PassthroughConfig> passthrough;
    private List<UnusualFrequencyConfig> unusualFrequency;
}
