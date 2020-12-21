package pro.laplacelab.mt4j.model;

import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import pro.laplacelab.mt4j.BaseTestPreparation;
import pro.laplacelab.mt4j.enums.SignalType;
import pro.laplacelab.mt4j.exception.InvalidSignalException;

import static org.junit.Assert.assertEquals;

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
        Signal signal = new Signal(advisorId, SignalType.CLOSE, positionId);

        // then
        assertEquals(SignalType.CLOSE, signal.getType());
        assertEquals(positionId, signal.getPositionId());
        assertEquals(advisorId, signal.getAdvisorId());
    }

    @Test
    @DisplayName("When Signal Buy build successful then state saved")
    public void whenSignalBuyBuildSuccessfulThenStateSaved() {
        // when
        Signal signal = new Signal(advisorId, SignalType.BUY, lot, stopLoss, takeProfit);

        // then
        assertEquals(SignalType.BUY, signal.getType());
        assertEquals(advisorId, signal.getAdvisorId());
        assertEquals(stopLoss, signal.getStopLoss());
        assertEquals(takeProfit, signal.getTakeProfit());
    }

    @Test
    @DisplayName("When Signal Sell build successful then state saved")
    public void whenSignalSellBuildSuccessfulThenStateSaved() {
        // when
        Signal signal = new Signal(advisorId, SignalType.SELL, lot, stopLoss, takeProfit);

        // then
        assertEquals(SignalType.SELL, signal.getType());
        assertEquals(advisorId, signal.getAdvisorId());
        assertEquals(stopLoss, signal.getStopLoss());
        assertEquals(takeProfit, signal.getTakeProfit());
    }


    @Test
    @DisplayName("When Update Sell build successful then state saved")
    public void whenSignalUpdateBuildSuccessfulThenStateSaved() {
        // when
        Signal signal = new Signal(advisorId, SignalType.UPDATE,
                positionId, lot, stopLoss, takeProfit);

        // then
        assertEquals(SignalType.UPDATE, signal.getType());
        assertEquals(positionId, signal.getPositionId());
        assertEquals(takeProfit, signal.getTakeProfit());
        assertEquals(advisorId, signal.getAdvisorId());
        assertEquals(stopLoss, signal.getStopLoss());
        assertEquals(lot, signal.getLot());
    }
}