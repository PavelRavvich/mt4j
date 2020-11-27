package pro.laplacelab.mt4j.model;

import org.junit.Test;
import pro.laplacelab.mt4j.BaseTestPreparation;
import pro.laplacelab.mt4j.enums.SignalType;
import pro.laplacelab.mt4j.exception.InvalidSignalException;

import static org.junit.Assert.assertEquals;

public class SignalTest extends BaseTestPreparation {

    @Test
    public void whenSignalNoActionBuildSuccessfulThenStateSaved() {
        Signal signal = new Signal(advisorId, SignalType.NO_ACTION);
        assertEquals(SignalType.NO_ACTION, signal.getType());
        assertEquals(advisorId, signal.getAdvisorId());
    }

    @Test(expected = InvalidSignalException.class)
    public void whenSignalNoActionBuildWithWrongSignalTypeThenThrowException() {
        new Signal(advisorId, SignalType.BUY);
    }

    @Test(expected = InvalidSignalException.class)
    public void whenSignalNoActionBuild1WithWrongSignalTypeThenThrowException() {
        new Signal(advisorId, SignalType.BUY, positionId);
    }

    @Test
    public void whenSignalCloseBuildSuccessfulThenStateSaved() {
        Signal signal = new Signal(advisorId, SignalType.CLOSE, positionId);
        assertEquals(SignalType.CLOSE, signal.getType());
        assertEquals(positionId, signal.getPositionId());
        assertEquals(advisorId, signal.getAdvisorId());
    }

    @Test(expected = InvalidSignalException.class)
    public void whenSignalCloseBuildWithWrongSignalTypeThenThrowException() {
        new Signal(advisorId, SignalType.BUY);
    }

    @Test
    public void whenSignalBuyBuildSuccessfulThenStateSaved() {
        Signal signal = new Signal(advisorId, SignalType.BUY, lot, stopLoss, takeProfit);
        assertEquals(SignalType.BUY, signal.getType());
        assertEquals(advisorId, signal.getAdvisorId());
        assertEquals(stopLoss, signal.getStopLoss());
        assertEquals(takeProfit, signal.getTakeProfit());
    }

    @Test(expected = InvalidSignalException.class)
    public void whenSignalBuyBuildWithWrongSignalTypeThenThrowException() {
        new Signal(advisorId, SignalType.CLOSE, lot, stopLoss, takeProfit);
    }

    @Test
    public void whenSignalSellBuildSuccessfulThenStateSaved() {
        Signal signal = new Signal(advisorId, SignalType.SELL, lot, stopLoss, takeProfit);
        assertEquals(SignalType.SELL, signal.getType());
        assertEquals(advisorId, signal.getAdvisorId());
        assertEquals(stopLoss, signal.getStopLoss());
        assertEquals(takeProfit, signal.getTakeProfit());
    }

    @Test(expected = InvalidSignalException.class)
    public void whenSignalSellBuildWithWrongSignalTypeThenThrowException() {
        new Signal(advisorId, SignalType.CLOSE, lot, stopLoss, takeProfit);
    }

    @Test
    public void whenSignalUpdateBuildSuccessfulThenStateSaved() {
        Signal signal = new Signal(advisorId, SignalType.UPDATE,
                positionId, lot, stopLoss, takeProfit);
        assertEquals(SignalType.UPDATE, signal.getType());
        assertEquals(positionId, signal.getPositionId());
        assertEquals(takeProfit, signal.getTakeProfit());
        assertEquals(advisorId, signal.getAdvisorId());
        assertEquals(stopLoss, signal.getStopLoss());
        assertEquals(lot, signal.getLot());
    }

    @Test(expected = InvalidSignalException.class)
    public void whenSignalUpdateBuildWithWrongSignalTypeThenThrowException() {
        new Signal(advisorId, SignalType.BUY, positionId, lot, stopLoss, takeProfit);
    }
}