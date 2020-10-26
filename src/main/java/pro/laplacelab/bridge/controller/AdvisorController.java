package pro.laplacelab.bridge.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pro.laplacelab.bridge.model.Advisor;
import pro.laplacelab.bridge.model.Input;
import pro.laplacelab.bridge.service.AdvisorService;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/advisor")
public class AdvisorController {

    private final AdvisorService advisorService;

    // TODO: 26.10.2020 wrap magic and inputs to object and get rid from path variable.
    @GetMapping("/add/{magic}")
    public ResponseEntity<Advisor> add(@PathVariable @NotNull final long magic,
                                       @RequestBody @Valid final List<Input> inputs) {
        return ResponseEntity.ok(advisorService.add(magic, inputs));
    }

}
