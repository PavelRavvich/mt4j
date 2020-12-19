package pro.laplacelab.mt4j.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import pro.laplacelab.mt4j.enums.InputType;
import pro.laplacelab.mt4j.exception.DuplicateAdvisorException;
import pro.laplacelab.mt4j.model.Advisor;
import pro.laplacelab.mt4j.model.Input;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@SpringBootTest
@WebAppConfiguration
@RunWith(SpringRunner.class)
public class AdvisorServiceImplTest {

    @Autowired
    AdvisorService advisorService;

    @Test(expected = DuplicateAdvisorException.class)
    public void whenAddAdvisorWithExistedMagicNumberThenThrowDuplicateAdvisorException() {
        advisorService.add(new Advisor(1L, List.of()));
        advisorService.add(new Advisor(1L, List.of()));
    }

    @Test
    public void whenAddSuccessThenReturnAdvisorWithId() {
        Advisor advisor = advisorService.add(
                new Advisor(2L, List.of(
                        new Input("key1", "val", InputType.STRING),
                        new Input("key2", "val", InputType.STRING)
                ))
        );
        assertNotNull(advisor);
        assertNotNull(advisor.getId());
    }

    @Test
    public void whenAddSuccessThenAdvisorInputsSaved() {
        Advisor save = advisorService.add(
                new Advisor(3L, List.of(
                        new Input("key1", "val", InputType.STRING),
                        new Input("key2", "1", InputType.NUMBER),
                        new Input("key3", "true", InputType.BOOLEAN)
                ))
        );
        Advisor advisor = advisorService.get(save.getId()).orElseThrow();
        assertEquals(InputType.STRING, advisor.getInput("key1").orElseThrow().getType());
        assertEquals("val", advisor.getInput("key1").orElseThrow().asString());
        assertEquals(InputType.NUMBER, advisor.getInput("key2").orElseThrow().getType());
        assertEquals(Double.valueOf("1"), advisor.getInput("key2").orElseThrow().asDouble());
        assertEquals(InputType.BOOLEAN, advisor.getInput("key3").orElseThrow().getType());
        assertEquals(true, advisor.getInput("key3").orElseThrow().asBoolean());
    }
}