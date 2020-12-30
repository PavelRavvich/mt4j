package pro.laplacelab.mt4j.model;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import pro.laplacelab.mt4j.BaseTestPreparation;
import pro.laplacelab.mt4j.enums.PositionType;

@DisplayName("Position entity test")
public class PositionTest extends BaseTestPreparation {

    @Test
    @DisplayName("When position constructed successful then state saved")
    public void whenPositionConstructedSuccessfulThenStateSaved() {
        // when
        final Position position = new Position(PositionType.LONG, positionId, lot, stopLoss,
                takeProfit, openPrice, closePrice, openAt, closeAt, profit, swap, commission);
        //then
        Assertions.assertEquals(PositionType.LONG, position.getType());
        Assertions.assertEquals(positionId, position.getPositionId());
        Assertions.assertEquals(takeProfit, position.getTakeProfit());
        Assertions.assertEquals(commission, position.getCommission());
        Assertions.assertEquals(closePrice, position.getClosePrice());
        Assertions.assertEquals(openPrice, position.getOpenPrice());
        Assertions.assertEquals(stopLoss, position.getStopLoss());
        Assertions.assertEquals(closeAt, position.getCloseAt());
        Assertions.assertEquals(openAt, position.getOpenAt());
        Assertions.assertEquals(swap, position.getSwap());
        Assertions.assertEquals(lot, position.getLot());
    }
}