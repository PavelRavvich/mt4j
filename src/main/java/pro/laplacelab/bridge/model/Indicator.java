package pro.laplacelab.bridge.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import pro.laplacelab.bridge.enums.IndicatorType;
import pro.laplacelab.bridge.enums.Timeframe;

import java.util.List;
import java.util.UUID;

@Data
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Indicator {

    @JsonProperty("id")
    private UUID id;

    @JsonProperty("type")
    private IndicatorType type;

    @JsonProperty("timeframe")
    private Timeframe timeframe;

    @JsonProperty("buffer")
    private List<Tick> buffer;

}
