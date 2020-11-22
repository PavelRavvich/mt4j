package pro.laplacelab.mt4j.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import pro.laplacelab.mt4j.JsonMapper;
import pro.laplacelab.mt4j.enums.Timeframe;
import pro.laplacelab.mt4j.model.Market;
import pro.laplacelab.mt4j.model.MqlRates;
import pro.laplacelab.mt4j.model.Signal;
import pro.laplacelab.mt4j.service.SignalService;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(SignalController.class)
@RunWith(SpringRunner.class)
public class SignalControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SignalService signalService;

    private final JsonMapper mapper = new JsonMapper();

    @Test
    public void testGetSignal() throws Exception {
        Market market = new Market();
        market.setAdvisorId(UUID.randomUUID());
        market.setRates(Map.of(Timeframe.M_1, List.of(new MqlRates(1L, 1D, 1D, 1D, 1D, 1L, 1, 1L))));
        market.setPositions(List.of());
        market.setStrategyName("TEST");
        final String requestJson = mapper.toJson(market);
        Signal signal = new Signal();

        mockMvc.perform(MockMvcRequestBuilders.get("/api/signal")
                .content(requestJson)
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk());

        when(signalService.onTick(market)).thenReturn(signal);
        verify(signalService, times(1)).onTick(market);
    }

}