package pro.laplacelab.bridge.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
public class SignalResponse {

    private SignalType type;

    private BigDecimal value;

    private BigDecimal stopLoss;

    private BigDecimal takeProfit;

}
