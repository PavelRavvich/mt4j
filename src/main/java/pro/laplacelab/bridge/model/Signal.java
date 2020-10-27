package pro.laplacelab.bridge.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import pro.laplacelab.bridge.enums.SignalType;
import pro.laplacelab.bridge.exception.InvalidSignalException;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.UUID;

@Data
@NoArgsConstructor
@EqualsAndHashCode
@JsonIgnoreProperties(ignoreUnknown = true)
public class Signal {

    @NotNull(message = "type required")
    @JsonProperty("type")
    private SignalType type;

    @NotNull(message = "advisorId required")
    @JsonProperty("advisorId")
    private UUID advisorId;

    @JsonProperty("positionId")
    private Long positionId;

    @JsonProperty("lot")
    private BigDecimal lot;

    @JsonProperty("stopLoss")
    private BigDecimal stopLoss;

    @JsonProperty("takeProfit")
    private BigDecimal takeProfit;

    public Signal(final @NotNull UUID advisorId, final @NotNull SignalType type) {
        if (type != SignalType.NO_ACTION) {
            throw new InvalidSignalException();
        }
        this.type = type;
        this.advisorId = advisorId;

    }

    public Signal(final @NotNull UUID advisorId,
                  final @NotNull SignalType type,
                  final @NotNull(message = "positionId required") Long positionId) {
        if (type != SignalType.CLOSE) {
            throw new InvalidSignalException();
        }
        this.type = type;
        this.advisorId = advisorId;
        this.positionId = positionId;
    }

    public Signal(final @NotNull UUID advisorId, @NotNull final SignalType type,
                  final @NotNull(message = "lot required") BigDecimal lot,
                  final @NotNull(message = "stopLoss required") BigDecimal stopLoss,
                  final @NotNull(message = "takeProfit required") BigDecimal takeProfit) {
        if (type != SignalType.BUY && type != SignalType.SELL) {
            throw new InvalidSignalException();
        }
        this.lot = lot;
        this.type = type;
        this.stopLoss = stopLoss;
        this.advisorId = advisorId;
        this.takeProfit = takeProfit;
    }

    public Signal(final UUID advisorId, final SignalType type,
                  final @NotNull(message = "positionId required") Long positionId,
                  final @NotNull(message = "lot required") BigDecimal lot,
                  final @NotNull(message = "stopLoss required") BigDecimal stopLoss,
                  final @NotNull(message = "takeProfit required") BigDecimal takeProfit) {
        if (type != SignalType.UPDATE) {
            throw new InvalidSignalException();
        }
        this.lot = lot;
        this.type = type;
        this.advisorId = advisorId;
        this.positionId = positionId;
        this.takeProfit = takeProfit;
        this.stopLoss = stopLoss;
    }

}