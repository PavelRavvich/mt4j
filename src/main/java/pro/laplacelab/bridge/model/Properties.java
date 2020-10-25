package pro.laplacelab.bridge.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.*;

@Data
@EqualsAndHashCode
@JsonIgnoreProperties(ignoreUnknown = true)
public class Properties {

    private final UUID id = UUID.randomUUID();

    private final Map<String, Property> properties = new HashMap<>();

    public Properties(final List<Property> properties) {
        if (properties.size() != new HashSet<>(properties).size()) {
            throw new RuntimeException();
        }
        properties.forEach(item -> this.properties.put(item.getKey(), item));
    }

    public Optional<Property> findByName(final String name) {
        return Optional.of(properties.get(name));
    }

}
