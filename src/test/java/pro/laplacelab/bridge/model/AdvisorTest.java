package pro.laplacelab.bridge.model;

import org.junit.Test;
import pro.laplacelab.bridge.enums.InputType;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class AdvisorTest {

    @Test
    public void whenAdvisorBuildSuccessfullyDataSaved() {
        Advisor advisor = new Advisor(1L, List.of(
                new Input("key1", "val", InputType.STRING),
                new Input("key2", "1", InputType.NUMBER)));
        assertEquals(Long.valueOf(1), advisor.getMagic());
        assertEquals("val",
                advisor.getInput("key1").orElseThrow().getStringValue());
        assertEquals(new BigDecimal("1"),
                advisor.getInput("key2").orElseThrow().getBigDecimalValue());
    }


    @Test(expected = RuntimeException.class)
    public void whenAdvisorBuildFailThenThrowRuntimeException() {
        new Input("", "val", InputType.STRING);
    }

}