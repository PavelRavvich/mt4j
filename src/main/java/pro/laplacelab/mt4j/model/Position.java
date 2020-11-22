package pro.laplacelab.mt4j.model;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pro.laplacelab.mt4j.enums.PositionType;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Position  {

    @NotNull(message = "type required")
    @JsonProperty("type")
    private PositionType type;

    @NotNull(message = "positionId required")
    @JsonProperty("positionId")
    protected Long positionId;

    @NotNull(message = "lot required")
    @JsonProperty("lot")
    protected Double lot;

    @NotNull(message = "stopLoss required")
    @JsonProperty("stopLoss")
    protected Integer stopLoss;

    @NotNull(message = "takeProfit required")
    @JsonProperty("takeProfit")
    protected Integer takeProfit;

    @NotNull(message = "openAt required")
    @JsonProperty("openAt")
    private Long openAt;

    @JsonProperty("closeAt")
    private Long closeAt;

    @NotNull(message = "profit required")
    @JsonProperty("profit")
    private Double profit;

    @NotNull(message = "swap required")
    @JsonProperty("swap")
    private Double swap;

}

