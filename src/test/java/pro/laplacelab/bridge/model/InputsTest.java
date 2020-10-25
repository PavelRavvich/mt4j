package pro.laplacelab.bridge.model;

import org.junit.Test;
import pro.laplacelab.bridge.enums.InputType;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class InputsTest {

    @Test
    public void whenInputsBuildSuccessfullyDataSaved() {
        Inputs inputs = new Inputs(1, List.of(
                new Input("key1", "val", InputType.STRING),
                new Input("key2", "1", InputType.NUMBER)));
        assertEquals(1, inputs.getMagic());
        assertEquals("val",
                inputs.findByName("key1").orElseThrow().getStringValue());
        assertEquals(new BigDecimal("1"),
                inputs.findByName("key2").orElseThrow().getBigDecimalValue());
    }


    @Test(expected = RuntimeException.class)
    public void whenInputsBuildFailThenThrowRuntimeException() {
        new Input("", "val", InputType.STRING);
    }

}