package pro.laplacelab.mt4j.model;

import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import pro.laplacelab.mt4j.BaseTestPreparation;
import pro.laplacelab.mt4j.enums.PositionType;

import static org.junit.Assert.assertEquals;

@DisplayName("Position entity test")
public class PositionTest extends BaseTestPreparation {

    @Test
    @DisplayName("When position constructed successful then state saved")
    public void whenPositionConstructedSuccessfulThenStateSaved() {
        // when
        final Position position = new Position(PositionType.LONG, positionId, lot, stopLoss,
                takeProfit, openPrice, closePrice, openAt, closeAt, profit, swap, commission);
        //then
        assertEquals(PositionType.LONG, position.getType());
        assertEquals(positionId, position.getPositionId());
        assertEquals(takeProfit, position.getTakeProfit());
        assertEquals(commission, position.getCommission());
        assertEquals(closePrice, position.getClosePrice());
        assertEquals(openPrice, position.getOpenPrice());
        assertEquals(stopLoss, position.getStopLoss());
        assertEquals(closeAt, position.getCloseAt());
        assertEquals(openAt, position.getOpenAt());
        assertEquals(swap, position.getSwap());
        assertEquals(lot, position.getLot());
    }
}