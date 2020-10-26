package pro.laplacelab.bridge.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import pro.laplacelab.bridge.exception.DuplicateInputException;

import javax.validation.constraints.NotNull;
import java.util.*;

@Data
@EqualsAndHashCode
@JsonIgnoreProperties(ignoreUnknown = true)
public class Advisor {

    @JsonProperty("id")
    private final UUID id = UUID.randomUUID();

    @JsonIgnore
    private final Long magic;

    @JsonIgnore
    private final Map<String, Input> inputs = new HashMap<>();

    public Advisor(final @NotNull Long magic, final @NotNull List<Input> inputList) {
        if (inputList.size() != new HashSet<>(inputList).size()) {
            throw new DuplicateInputException();
        }
        inputList.forEach(item -> inputs.put(item.getKey(), item));
        this.magic = magic;
    }

    public Optional<Input> getInput(final @NotNull String name) {
        return Optional.of(inputs.get(name));
    }

}
