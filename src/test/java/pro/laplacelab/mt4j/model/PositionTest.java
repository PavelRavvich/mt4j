package pro.laplacelab.mt4j.model;

import org.junit.Test;
import pro.laplacelab.mt4j.BaseTestPreparation;
import pro.laplacelab.mt4j.enums.PositionType;

import static org.junit.Assert.assertEquals;

public class PositionTest extends BaseTestPreparation {

    @Test
    public void whenPositionConstructedSuccessfulThenStateSaved() {
        final Position position = new Position(PositionType.LONG, positionId,
                lot, stopLoss, takeProfit, openAt, closeAt, profit, swap);
        assertEquals(PositionType.LONG, position.getType());
        assertEquals(positionId, position.getPositionId());
        assertEquals(takeProfit, position.getTakeProfit());
        assertEquals(stopLoss, position.getStopLoss());
        assertEquals(lot, position.getLot());
        assertEquals(openAt, position.getOpenAt());
        assertEquals(closeAt, position.getCloseAt());
    }

}