package pro.laplacelab.bridge.model;

import org.junit.Test;
import pro.laplacelab.bridge.enums.SignalType;
import pro.laplacelab.bridge.exception.InvalidSignalException;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;

public class PositionTest extends SignalTest {

    protected final Long openAt = System.currentTimeMillis();

    protected final Long closeAt = System.currentTimeMillis();

    protected final BigDecimal profit = new BigDecimal("1");

    @Test
    public void whenPositionConstructedSuccessfulThenStateSaved() {
        final Position position = new Position(advisorId, SignalType.BUY, positionId,
                lot, stopLoss, takeProfit, openAt, closeAt, profit);
        assertEquals(SignalType.BUY, position.getType());
        assertEquals(positionId, position.getPositionId());
        assertEquals(takeProfit, position.getTakeProfit());
        assertEquals(advisorId, position.getAdvisorId());
        assertEquals(stopLoss, position.getStopLoss());
        assertEquals(lot, position.getLot());
        assertEquals(openAt, position.getOpenAt());
        assertEquals(closeAt, position.getCloseAt());
    }

    @Test(expected = InvalidSignalException.class)
    public void whenPositionBuildWithWrongSignalTypeThenThrowException() {
        new Position(advisorId, SignalType.UPDATE, positionId,
                lot, stopLoss, takeProfit, openAt, closeAt, profit);
    }

}