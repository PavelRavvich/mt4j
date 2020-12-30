package pro.laplacelab.mt4j.model;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import pro.laplacelab.mt4j.BaseTestPreparation;
import pro.laplacelab.mt4j.enums.SignalType;
import pro.laplacelab.mt4j.exception.InvalidSignalException;

@DisplayName("Signal entity test")
public class SignalTest extends BaseTestPreparation {

    @Test(expected = InvalidSignalException.class)
    @DisplayName("When Signal SignalType.CLOSE build withSignalType.BUY type then throw InvalidSignalException")
    public void whenSignalCloseBuildWithBuySignalTypeThenThrowInvalidSignalException() {
        new Signal(advisorId, SignalType.BUY, positionId);
    }

    @Test(expected = InvalidSignalException.class)
    @DisplayName("When Signal SignalType.BUY or SELL build withSignalType.CLOSE type then throw InvalidSignalException")
    public void whenSignalBuySellBuildWithCloseSignalTypeThenThrowInvalidSignalException() {
        new Signal(advisorId, SignalType.CLOSE, lot, stopLoss, takeProfit);
    }

    @Test(expected = InvalidSignalException.class)
    @DisplayName("When Signal SignalType.UPDATE build withSignalType.BUY type then throw InvalidSignalException")
    public void whenSignalUpdateBuildWithBuySignalTypeThenThrowInvalidSignalException() {
        new Signal(advisorId, SignalType.BUY, positionId, lot, stopLoss, takeProfit);
    }

    @Test
    @DisplayName("When Signal Close build successful then state saved")
    public void whenSignalCloseBuildSuccessfulThenStateSaved() {
        // when
        final Signal signal = new Signal(advisorId, SignalType.CLOSE, positionId);

        // then
        Assertions.assertEquals(SignalType.CLOSE, signal.getType());
        Assertions.assertEquals(positionId, signal.getPositionId());
        Assertions.assertEquals(advisorId, signal.getAdvisorId());
    }

    @Test
    @DisplayName("When Signal Buy build successful then state saved")
    public void whenSignalBuyBuildSuccessfulThenStateSaved() {
        // when
        final Signal signal = new Signal(advisorId, SignalType.BUY, lot, stopLoss, takeProfit);

        // then
        Assertions.assertEquals(SignalType.BUY, signal.getType());
        Assertions.assertEquals(advisorId, signal.getAdvisorId());
        Assertions.assertEquals(stopLoss, signal.getStopLoss());
        Assertions.assertEquals(takeProfit, signal.getTakeProfit());
    }

    @Test
    @DisplayName("When Signal Sell build successful then state saved")
    public void whenSignalSellBuildSuccessfulThenStateSaved() {
        // when
        final Signal signal = new Signal(advisorId, SignalType.SELL, lot, stopLoss, takeProfit);

        // then
        Assertions.assertEquals(SignalType.SELL, signal.getType());
        Assertions.assertEquals(advisorId, signal.getAdvisorId());
        Assertions.assertEquals(stopLoss, signal.getStopLoss());
        Assertions.assertEquals(takeProfit, signal.getTakeProfit());
    }


    @Test
    @DisplayName("When Update Sell build successful then state saved")
    public void whenSignalUpdateBuildSuccessfulThenStateSaved() {
        // when
        final Signal signal = new Signal(advisorId, SignalType.UPDATE,
                positionId, lot, stopLoss, takeProfit);

        // then
        Assertions.assertEquals(SignalType.UPDATE, signal.getType());
        Assertions.assertEquals(positionId, signal.getPositionId());
        Assertions.assertEquals(takeProfit, signal.getTakeProfit());
        Assertions.assertEquals(advisorId, signal.getAdvisorId());
        Assertions.assertEquals(stopLoss, signal.getStopLoss());
        Assertions.assertEquals(lot, signal.getLot());
    }
}