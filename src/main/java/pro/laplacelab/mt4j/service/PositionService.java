package pro.laplacelab.mt4j.service;

import pro.laplacelab.mt4j.model.Position;

import java.util.UUID;

public interface PositionService {
    // TODO: 12/19/2020 add return value test #155
    Position add(final UUID advisorId, final Position position);
    Position update(final UUID advisorId, final Position position);
    Position history(final UUID advisorId, final Position position);
}
