package pro.laplacelab.bridge.model;

import org.junit.Test;
import pro.laplacelab.bridge.enums.PropertyType;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class PropertiesTest {

    @Test
    public void whenPropertiesBuildSuccessfullyDataSaved() {
        Properties properties = new Properties(List.of(
                new Property("key1", "val", PropertyType.STRING),
                new Property("key2", "1", PropertyType.NUMBER)));
        assertEquals("val", properties.findByName("key1").orElseThrow().getStringValue());
        assertEquals(new BigDecimal("1"), properties.findByName("key2").orElseThrow().getBigDecimalValue());
    }


    @Test(expected = RuntimeException.class)
    public void whenPropertiesBuildFailThenThrowRuntimeException() {
        new Property("", "val", PropertyType.STRING);
    }

}