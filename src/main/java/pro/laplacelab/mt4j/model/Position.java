package pro.laplacelab.mt4j.model;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import pro.laplacelab.mt4j.enums.SignalType;

import javax.validation.constraints.NotNull;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Position extends Signal {

    @JsonProperty("openAt")
    private Long openAt;

    @JsonProperty("closeAt")
    private Long closeAt;

    @JsonProperty("profit")
    private Double profit;

    public Position(final @NotNull(message = "advisorId required") UUID advisorId,
                    final @NotNull(message = "type required") SignalType type,
                    final @NotNull(message = "positionId required") Long positionId,
                    final @NotNull(message = "lot required") Double lot,
                    final @NotNull(message = "stopLoss required") Integer stopLoss,
                    final @NotNull(message = "takeProfit required") Integer takeProfit,
                    final @NotNull(message = "openAt required") Long openAt,
                    final @NotNull(message = "closeAt required") Long closeAt,
                    final @NotNull(message = "profit required") Double profit) {
        super(advisorId, type, lot, stopLoss, takeProfit);
        this.positionId = positionId;
        this.closeAt = closeAt;
        this.openAt = openAt;
        this.profit = profit;
    }
}

