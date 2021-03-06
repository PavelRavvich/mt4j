package pro.laplacelab.mt4j.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import pro.laplacelab.mt4j.enums.Timeframe;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@JsonIgnoreProperties(ignoreUnknown = true)
public class Market {

    @NotNull(message = "advisorId required")
    @JsonProperty("advisorId")
    private UUID advisorId;

    @NotNull(message = "account required")
    @JsonProperty("account")
    private Account account;

    @NotNull(message = "strategyName required")
    @NotBlank(message = "strategyName can't be blank")
    @JsonProperty("strategyName")
    private String strategyName;

    @NotNull(message = "positions required")
    @JsonProperty("positions")
    private List<Position> positions;

    @NotNull(message = "rates required")
    @NotEmpty(message = "rates can't be empty")
    @JsonProperty("rates")
    private Map<Timeframe, List<Rate>> rates;

}
