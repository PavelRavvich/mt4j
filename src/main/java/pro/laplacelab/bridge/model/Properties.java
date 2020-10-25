package pro.laplacelab.bridge.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Data
@EqualsAndHashCode
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Properties {

    private final UUID id = UUID.randomUUID();

    private final Map<String, Property> properties = new HashMap<>();

    public Properties(final List<Property> properties) {
        properties.forEach(item -> this.properties.put(item.getKey(), item));
    }

    public Property findByName(final String name) {
        return properties.get(name);
    }

}
