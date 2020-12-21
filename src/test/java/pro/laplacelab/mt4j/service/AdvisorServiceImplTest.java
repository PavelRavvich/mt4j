package pro.laplacelab.mt4j.service;

import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
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
@DisplayName("AdvisorService test")
public class AdvisorServiceImplTest {

    @Autowired
    AdvisorService advisorService;

    @Test(expected = DuplicateAdvisorException.class)
    @DisplayName("When add advisor with existed MagicNumber then throw DuplicateAdvisorException")
    public void whenAddAdvisorWithExistedMagicNumberThenThrowDuplicateAdvisorException() {
        advisorService.add(new Advisor(1L, List.of()));
        advisorService.add(new Advisor(1L, List.of()));
    }

    @Test
    @DisplayName("When add advisor successfully then return added advisor")
    public void whenAddAdvisorSuccessfullyThenReturnAddedAdvisor() {
        // given
        final Advisor advisor = new Advisor(2L, List.of(
                new Input("key1", "val", InputType.STRING),
                new Input("key2", "val", InputType.STRING)
        ));

        // when
        final Advisor savedAdvisor = advisorService.add(advisor);

        // then
        assertNotNull(savedAdvisor);
        assertNotNull(savedAdvisor.getId());
    }

    @Test
    @DisplayName("When advisor add successfully then advisor Inputs saved")
    public void whenAdvisorAddSuccessfullyThenAdvisorInputsSaved() {
        // given
        final Advisor advisor = new Advisor(3L, List.of(
                new Input("key1", "val", InputType.STRING),
                new Input("key2", "1", InputType.NUMBER),
                new Input("key3", "true", InputType.BOOLEAN)
        ));

        // when
        final Advisor added = advisorService.add(advisor);

        //then
        final Advisor fined = advisorService.findByAdvisorId(added.getId()).orElseThrow();
        assertEquals(InputType.STRING, fined.getInput("key1").orElseThrow().getType());
        assertEquals("val", fined.getInput("key1").orElseThrow().asString());
        assertEquals(InputType.NUMBER, fined.getInput("key2").orElseThrow().getType());
        assertEquals(Double.valueOf("1"), fined.getInput("key2").orElseThrow().asDouble());
        assertEquals(InputType.BOOLEAN, fined.getInput("key3").orElseThrow().getType());
        assertEquals(true, fined.getInput("key3").orElseThrow().asBoolean());
    }
}