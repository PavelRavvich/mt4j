package pro.laplacelab.bridge.model;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Position extends Signal implements Comparable<Long> {

    @JsonProperty("openAt")
    private Long openAt;

    @JsonProperty("closeAt")
    private Long closeAt;

    @JsonProperty("profit")
    private BigDecimal profit;

    @Override
    public int compareTo(final Long closeAt) {
        return this.closeAt.hashCode() - closeAt.hashCode();
    }

}

