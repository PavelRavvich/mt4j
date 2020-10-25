package pro.laplacelab.bridge.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import pro.laplacelab.bridge.enums.PropertyType;
import pro.laplacelab.bridge.model.Properties;
import pro.laplacelab.bridge.model.Property;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@SpringBootTest
@WebAppConfiguration
@RunWith(SpringRunner.class)
public class PropertiesServiceImplTest {

    @Autowired
    PropertyService propertyService;

    @Test
    public void whenRegisterSuccessThenReturnConfigUUID() {
        UUID uuid = propertyService.add(List.of(
                new Property("key1", "val", PropertyType.STRING),
                new Property("key2", "val", PropertyType.STRING)));
        assertNotNull(uuid);
    }

    @Test(expected = RuntimeException.class)
    public void whenRegisterFailThenThrowException() {
        propertyService.add(List.of(new Property("", "val", PropertyType.STRING)));
    }

    @Test
    public void whenAddSuccessThenPropertiesSaved() {
        UUID uuid = propertyService.add(List.of(
                new Property("key1", "val", PropertyType.STRING),
                new Property("key2", "1", PropertyType.NUMBER)));
        Properties properties = propertyService.get(uuid).orElseThrow();
        assertEquals("val", properties.findByName("key1").orElseThrow().getStringValue());
        assertEquals(new BigDecimal("1"), properties.findByName("key2").orElseThrow().getBigDecimalValue());
    }

}