package pro.laplacelab.bridge.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pro.laplacelab.bridge.model.Market;
import pro.laplacelab.bridge.model.Signal;
import pro.laplacelab.bridge.service.SignalService;

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
