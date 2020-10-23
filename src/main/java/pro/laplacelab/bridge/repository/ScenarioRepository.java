package pro.laplacelab.bridge.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import pro.laplacelab.bridge.enums.ScenarioType;
import pro.laplacelab.bridge.model.Scenario;

import java.util.UUID;

@Repository
public interface ScenarioRepository
        extends MongoRepository<Scenario, UUID> {
    Scenario findDistinctByName(String name);
    Scenario findDistinctByType(ScenarioType type);
}
