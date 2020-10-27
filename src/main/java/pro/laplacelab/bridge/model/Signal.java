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

@Data
@NoArgsConstructor
@EqualsAndHashCode
@JsonIgnoreProperties(ignoreUnknown = true)
public class Signal {

    @NotNull
    @JsonProperty("type")
    private SignalType type;

    @JsonProperty("positionId")
    private Long positionId;

    @JsonProperty("lot")
    private BigDecimal lot;

    @JsonProperty("stopLoss")
    private BigDecimal stopLoss;

    @JsonProperty("takeProfit")
    private BigDecimal takeProfit;

    public Signal(final SignalType type) {
        if (type != SignalType.NO_ACTION) {
            throw new InvalidSignalException();
        }
        this.type = type;
    }

    public Signal(final SignalType type, final Long positionId) {
        if (type != SignalType.CLOSE) {
            throw new InvalidSignalException();
        }
        this.type = type;
        this.positionId = positionId;
    }

    public Signal(final SignalType type,
                  final BigDecimal lot, final BigDecimal stopLoss, final BigDecimal takeProfit) {
        if (type != SignalType.BUY && type != SignalType.SELL) {
            throw new InvalidSignalException();
        }
        this.lot = lot;
        this.type = type;
        this.stopLoss = stopLoss;
        this.takeProfit = takeProfit;
    }

    public Signal(final SignalType type, final Long positionId,
                  final BigDecimal lot, final BigDecimal stopLoss, final BigDecimal takeProfit) {
        if (type != SignalType.UPDATE) {
            throw new InvalidSignalException();
        }
        this.lot = lot;
        this.type = type;
        this.positionId = positionId;
        this.stopLoss = stopLoss;
        this.takeProfit = takeProfit;
    }

}