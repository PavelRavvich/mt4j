package pro.laplacelab.bridge.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@JsonIgnoreProperties(ignoreUnknown = true)
public class Market {

    @NotNull(message = "advisorId required")
    @JsonProperty("advisorId")
    private UUID advisorId;

    @NotNull(message = "strategyName required")
    @NotBlank(message = "strategyName can't be blank")
    @JsonProperty("strategyName")
    private String strategyName;

    @NotNull(message = "indicators required")
    @NotEmpty(message = "indicators can't be empty")
    @JsonProperty("indicators")
    private List<Indicator> indicators;

}
