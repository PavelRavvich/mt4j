package pro.laplacelab.bridge.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import pro.laplacelab.bridge.enums.IndicatorType;

import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@JsonIgnoreProperties(ignoreUnknown = true)
public class Sequence {

    @JsonProperty("id")
    private UUID id;

    @JsonProperty("type")
    private IndicatorType type;

    @JsonProperty("buffer")
    private List<Tick> buffer;

}
