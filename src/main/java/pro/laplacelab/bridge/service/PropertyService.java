package pro.laplacelab.bridge.service;

import pro.laplacelab.bridge.model.Properties;
import pro.laplacelab.bridge.model.Property;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PropertyService {
    UUID add(List<Property> properties);

    Optional<Properties> get(UUID uuid);
}
