package pro.laplacelab.mt4j.model;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import pro.laplacelab.mt4j.enums.InputType;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@Data
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Input {

    @NotNull(message = "key required")
    @NotEmpty(message = "key can't be empty")
    @JsonProperty("key")
    private String key;

    @NotNull(message = "value required")
    @NotEmpty(message = "value can't be empty")
    @JsonProperty("value")
    private String value;

    @NotNull(message = "type required")
    @NotEmpty(message = "type can't be empty")
    @JsonProperty("type")
    private InputType type;

    public String asString() {
        return value;
    }

    public Double asDouble() {
        return Double.valueOf(value);
    }

    public LocalTime asLocalTime() {
        return LocalTime.parse(value, DateTimeFormatter.ofPattern("HH:mm"));
    }

}
