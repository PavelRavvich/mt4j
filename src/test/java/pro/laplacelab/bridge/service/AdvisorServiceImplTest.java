package pro.laplacelab.bridge.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import pro.laplacelab.bridge.enums.InputType;
import pro.laplacelab.bridge.model.Advisor;
import pro.laplacelab.bridge.model.Input;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@SpringBootTest
@WebAppConfiguration
@RunWith(SpringRunner.class)
public class AdvisorServiceImplTest {

    @Autowired
    AdvisorService advisorService;

    @Test
    public void whenAddSuccessThenReturnAdvisorWithId() {
        Advisor advisor = advisorService.add(
                new Advisor(1L, List.of(
                        new Input("key1", "val", InputType.STRING),
                        new Input("key2", "val", InputType.STRING)
                ))
        );
        assertNotNull(advisor);
        assertNotNull(advisor.getId());
    }

    @Test(expected = RuntimeException.class)
    public void whenAddFailThenThrowException() {
        advisorService.add(
                new Advisor(1L, List.of(
                        new Input("", "val", InputType.STRING)
                ))
        );
    }

    @Test
    public void whenAddSuccessThenAdvisorInputsSaved() {
        Advisor save = advisorService.add(
                new Advisor(1L, List.of(
                        new Input("key1", "val", InputType.STRING),
                        new Input("key2", "1", InputType.NUMBER)
                ))
        );
        Advisor advisor = advisorService.get(save.getId()).orElseThrow();
        assertEquals(InputType.STRING,
                advisor.getInput("key1").orElseThrow().getType());
        assertEquals("val",
                advisor.getInput("key1").orElseThrow().valueAsString());
        assertEquals(InputType.NUMBER,
                advisor.getInput("key2").orElseThrow().getType());
        assertEquals(new BigDecimal("1"),
                advisor.getInput("key2").orElseThrow().valueAsDecimal());
    }

}