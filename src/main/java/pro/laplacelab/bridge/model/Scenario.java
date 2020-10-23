package pro.laplacelab.bridge.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;
import pro.laplacelab.bridge.enums.ScenarioType;

import java.util.UUID;

@Document
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Scenario {
    @MongoId
    private UUID uuid;

    private String name;

    private ScenarioType type;
}
