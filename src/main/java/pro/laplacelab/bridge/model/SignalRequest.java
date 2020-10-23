package pro.laplacelab.bridge.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@JsonIgnoreProperties(ignoreUnknown = true)
public class SignalRequest {

    @NotNull(message = "advisorId required")
    @JsonProperty("advisorId")
    private UUID advisorId;

    @NotNull(message = "scenarioSysName required")
    @NotBlank(message = "scenarioSysName can't be blank")
    @JsonProperty("scenarioSysName")
    private String scenarioSysName;

    @NotNull(message = "sequences required")
    @NotEmpty(message = "sequences can't be empty")
    @JsonProperty("sequences")
    private List<Sequence> sequences;

}
