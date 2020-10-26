package pro.laplacelab.bridge.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pro.laplacelab.bridge.model.Advisor;
import pro.laplacelab.bridge.service.AdvisorService;

import javax.validation.Valid;

@RestController
@AllArgsConstructor
@RequestMapping("/api/advisor")
public class AdvisorController {

    private final AdvisorService advisorService;

    @PostMapping("/add")
    public ResponseEntity<Advisor> add(@RequestBody @Valid final Advisor advisor) {
        return ResponseEntity.ok(advisorService.add(advisor));
    }

}
