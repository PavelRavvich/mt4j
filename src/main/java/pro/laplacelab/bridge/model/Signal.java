package pro.laplacelab.bridge.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import pro.laplacelab.bridge.enums.SignalType;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@JsonIgnoreProperties(ignoreUnknown = true)
public class Signal {

    @JsonProperty("type")
    private SignalType type;

    @JsonProperty("lot")
    private BigDecimal lot;

    @JsonProperty("stopLoss")
    private BigDecimal stopLoss;

    @JsonProperty("takeProfit")
    private BigDecimal takeProfit;

}
