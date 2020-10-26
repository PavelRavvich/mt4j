package pro.laplacelab.bridge.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import pro.laplacelab.bridge.exception.DuplicateInputException;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Data
@EqualsAndHashCode
@JsonIgnoreProperties(ignoreUnknown = true)
public class Advisor {

    @JsonProperty("id")
    private final UUID id;

    @JsonProperty("magic")
    private final Long magic;

    @JsonProperty("inputs")
    private final List<Input> inputs;

    public Advisor(final @NotNull Long magic, final @NotNull List<Input> inputs) {
        if (inputs.size() != inputs.stream().map(Input::getKey).count()) {
            throw new DuplicateInputException();
        }
        this.id = UUID.randomUUID();
        this.inputs = inputs;
        this.magic = magic;
    }

    public Optional<Input> getInput(final @NotNull String key) {
        return inputs.stream().filter(item -> key.equals(item.getKey())).findFirst();
    }

}
