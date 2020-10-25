package pro.laplacelab.bridge.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.*;

@Data
@EqualsAndHashCode
@JsonIgnoreProperties(ignoreUnknown = true)
public class Inputs {

    private final long magic;

    private final UUID id = UUID.randomUUID();

    private final Map<String, Input> properties = new HashMap<>();

    public Inputs(final long magic, final List<Input> properties) {
        if (properties.size() != new HashSet<>(properties).size()) {
            throw new RuntimeException();
        }
        properties.forEach(item -> this.properties.put(item.getKey(), item));
        this.magic = magic;
    }

    public Optional<Input> findByName(final String name) {
        return Optional.of(properties.get(name));
    }

}
