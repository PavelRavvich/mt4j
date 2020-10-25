package pro.laplacelab.bridge.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pro.laplacelab.bridge.model.Properties;
import pro.laplacelab.bridge.model.Property;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Service
public class PropertyServiceImpl implements PropertyService {

    private final Map<UUID, Properties> propertiesCache = new ConcurrentHashMap<>();

    @Override
    public UUID add(final @NotNull List<Property> propertyList) {
        log.debug("Attempt to add property list: {}", propertyList);
        Properties properties = new Properties(propertyList);
        propertiesCache.put(properties.getId(), properties);
        log.debug("Add advisor Properties {}", properties);
        return properties.getId();
    }

    @Override
    public Optional<Properties> get(final @NotNull UUID advisorId) {
        return Optional.of(propertiesCache.get(advisorId));
    }

}
