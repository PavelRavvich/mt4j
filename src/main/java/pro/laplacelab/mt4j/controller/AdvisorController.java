package pro.laplacelab.mt4j.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pro.laplacelab.mt4j.model.Advisor;
import pro.laplacelab.mt4j.service.AdvisorService;

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
