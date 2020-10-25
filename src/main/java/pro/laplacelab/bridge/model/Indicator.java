package pro.laplacelab.bridge.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import pro.laplacelab.bridge.enums.IndicatorType;
import pro.laplacelab.bridge.enums.Timeframe;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Indicator {

    @NotNull(message = "number required")
    @JsonProperty("number")
    private Integer number;

    @NotNull(message = "type required")
    @JsonProperty("type")
    private IndicatorType type;

    @NotNull(message = "timeframe required")
    @JsonProperty("timeframe")
    private Timeframe timeframe;

    @Min(value = 2, message = "minimum buffer size equals 2")
    @NotNull(message = "buffer required")
    @JsonProperty("buffer")
    private List<Bar> buffer;

}
