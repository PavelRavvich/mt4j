package pro.laplacelab.mt4j.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pro.laplacelab.mt4j.model.Market;
import pro.laplacelab.mt4j.model.Signal;
import pro.laplacelab.mt4j.service.SignalService;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/signal")
@AllArgsConstructor
public class SignalController {

    private final SignalService signalService;

    @GetMapping
    public ResponseEntity<Signal> signal(@RequestBody @Valid final Market market) {
        return ResponseEntity.ok(signalService.onTick(market));
    }

}
