package pro.laplacelab.mt4j;

import java.util.UUID;

public class BaseTestPreparation {

    protected final Long openAt = System.currentTimeMillis();

    protected final Long closeAt = System.currentTimeMillis();

    protected final Double profit = 1d;

    protected final UUID advisorId = UUID.randomUUID();

    protected final Long positionId = 1_000_000L;

    protected final Double openPrice = 1.12345;

    protected final Double closePrice = 1.01234;

    protected final Double lot = 0.01;

    protected final Integer stopLoss = 100;

    protected final Integer takeProfit = 50;

    protected final Double swap = 0D;

    protected final Double commission = 10.11;

}
