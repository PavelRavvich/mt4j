package pro.laplacelab.bridge.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pro.laplacelab.bridge.model.Input;
import pro.laplacelab.bridge.model.Ticket;
import pro.laplacelab.bridge.service.InputService;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/advisor")
public class AdvisorController {

    private final InputService inputService;

    @GetMapping("/{magic}/ticket")
    public ResponseEntity<Ticket> save(@PathVariable @NotNull final long magic,
                                       @RequestBody @Valid final List<Input> inputs) {
        return ResponseEntity.ok(inputService.save(magic, inputs));
    }

}
