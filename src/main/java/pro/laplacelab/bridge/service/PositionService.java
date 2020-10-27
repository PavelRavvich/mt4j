package pro.laplacelab.bridge.service;

import pro.laplacelab.bridge.model.Position;

public interface PositionService {
    Position add(final Position position);

    Position update(final Position position);

    Position history(final Position position);
}
