package pro.laplacelab.bridge.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import pro.laplacelab.bridge.model.Sequence;
import pro.laplacelab.bridge.model.Signal;
import pro.laplacelab.bridge.service.SignalService;

import java.util.List;

@RestController("/api/signal")
@AllArgsConstructor
public class SignalController {

    private final SignalService signalService;

    @GetMapping
    public ResponseEntity<Signal> get(@RequestBody List<Sequence> sequences) {
        return ResponseEntity.ok(signalService.get(sequences));
    }
}
