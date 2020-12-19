package pro.laplacelab.mt4j.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import pro.laplacelab.mt4j.enums.SignalType;
import pro.laplacelab.mt4j.exception.InvalidSignalException;

import javax.validation.constraints.NotNull;
import java.util.UUID;

@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Signal {

    @NotNull(message = "type required")
    @JsonProperty("type")
    protected SignalType type;

    @NotNull(message = "advisorId required")
    @JsonProperty("advisorId")
    protected UUID advisorId;

    @JsonProperty("positionId")
    protected Long positionId;

    @JsonProperty("lot")
    protected Double lot;

    @JsonProperty("stopLoss")
    protected Integer stopLoss;

    @JsonProperty("takeProfit")
    protected Integer takeProfit;

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

    public Signal(final @NotNull UUID advisorId,
                  final @NotNull SignalType type,
                  final @NotNull(message = "lot required") Double lot,
                  final @NotNull(message = "stopLoss required") Integer stopLoss,
                  final @NotNull(message = "takeProfit required") Integer takeProfit) {
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
                  final @NotNull(message = "lot required") Double lot,
                  final @NotNull(message = "stopLoss required") Integer stopLoss,
                  final @NotNull(message = "takeProfit required") Integer takeProfit) {
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