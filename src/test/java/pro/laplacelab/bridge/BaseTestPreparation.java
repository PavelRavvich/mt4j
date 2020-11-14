package pro.laplacelab.bridge;

import java.util.UUID;

public class BaseTestPreparation {

    protected final Long openAt = System.currentTimeMillis();

    protected final Long closeAt = System.currentTimeMillis();

    protected final Double profit = 1d;

    protected final UUID advisorId = UUID.randomUUID();

    protected final Long positionId = 1_000_000L;

    protected final Double lot = 0.01;

    protected final Integer stopLoss = 100;

    protected final Integer takeProfit = 50;

}
