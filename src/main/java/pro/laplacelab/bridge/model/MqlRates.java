package pro.laplacelab.bridge.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@JsonIgnoreProperties(ignoreUnknown = true)
public class MqlRates {

    @NotNull(message = "time required")
    @NotBlank(message = "time can't be blank")
    @JsonProperty("time")
    private Long time;

    @NotNull(message = "open required")
    @NotBlank(message = "open can't be blank")
    @JsonProperty("open")
    private Double open;

    @NotNull(message = "high required")
    @NotBlank(message = "high can't be blank")
    @JsonProperty("high")
    private Double high;

    @NotNull(message = "low required")
    @NotBlank(message = "low can't be blank")
    @JsonProperty("low")
    private Double low;

    @NotNull(message = "close required")
    @NotBlank(message = "close can't be blank")
    @JsonProperty("close")
    private Double close;

    @NotNull(message = "tickVolume required")
    @NotBlank(message = "tickVolume can't be blank")
    @JsonProperty("tickVolume")
    private Long tickVolume;

    @NotNull(message = "spread required")
    @NotBlank(message = "spread can't be blank")
    @JsonProperty("spread")
    private Integer spread;

    @NotNull(message = "realVolume required")
    @NotBlank(message = "realVolume can't be blank")
    @JsonProperty("realVolume")
    private Long realVolume;

}
