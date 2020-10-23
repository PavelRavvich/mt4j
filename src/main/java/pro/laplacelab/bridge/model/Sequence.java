package pro.laplacelab.bridge.model;

import lombok.Data;
import pro.laplacelab.bridge.enums.IndicatorType;

import java.util.List;

@Data
public class Sequence {

    private String id;

    private IndicatorType type;

    private List<Tick> ticks;

}
