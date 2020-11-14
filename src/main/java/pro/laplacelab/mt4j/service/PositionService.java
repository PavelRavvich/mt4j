package pro.laplacelab.mt4j.service;

import pro.laplacelab.mt4j.model.Position;

public interface PositionService {
    Position add(final Position position);
    Position update(final Position position);
    Position history(final Position position);
}
