package pro.laplacelab.bridge.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import pro.laplacelab.bridge.enums.InputType;
import pro.laplacelab.bridge.model.Input;
import pro.laplacelab.bridge.model.Inputs;
import pro.laplacelab.bridge.model.Ticket;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@SpringBootTest
@WebAppConfiguration
@RunWith(SpringRunner.class)
public class InputsServiceImplTest {

    @Autowired
    InputService inputService;

    @Test
    public void whenSaveSuccessThenReturnTicketUUID() {
        Ticket ticket = inputService.save(1, List.of(
                new Input("key1", "val", InputType.STRING),
                new Input("key2", "val", InputType.STRING)));
        assertNotNull(ticket);
        assertNotNull(ticket.getId());
    }

    @Test(expected = RuntimeException.class)
    public void whenSaveFailThenThrowException() {
        inputService.save(1, List.of(new Input("", "val", InputType.STRING)));
    }

    @Test
    public void whenAddSuccessThenPropertiesSaved() {
        Ticket ticket = inputService.save(1, List.of(
                new Input("key1", "val", InputType.STRING),
                new Input("key2", "1", InputType.NUMBER)));
        Inputs inputs = inputService.get(ticket.getId()).orElseThrow();
        assertEquals("val",
                inputs.findByName("key1").orElseThrow().getStringValue());
        assertEquals(new BigDecimal("1"),
                inputs.findByName("key2").orElseThrow().getBigDecimalValue());
    }

}