package pro.laplacelab.bridge.model;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import pro.laplacelab.bridge.enums.PropertyType;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Objects;

@Data
@EqualsAndHashCode
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Property {

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
    private PropertyType type;

    public String getStringValue() {
        return value;
    }

    public BigDecimal getBigDecimalValue() {
        return new BigDecimal(value);
    }

    public Property(final String key, final String value, final PropertyType type) {
        if (Objects.isNull(key) || key.isEmpty() || Objects.isNull(value) || value.isEmpty() || Objects.isNull(type)) {
            throw new RuntimeException();
        }
        this.key = key;
        this.value = value;
        this.type = type;
    }

}
