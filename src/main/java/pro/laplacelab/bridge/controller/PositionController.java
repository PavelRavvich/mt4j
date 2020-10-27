package pro.laplacelab.bridge.controller;


import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pro.laplacelab.bridge.model.Position;
import pro.laplacelab.bridge.service.PositionService;

import javax.validation.Valid;

@RestController
@AllArgsConstructor
@RequestMapping("/api/position")
public class PositionController {

    private final PositionService positionService;

    @PostMapping("/add")
    public ResponseEntity<Position> add(@RequestBody @Valid final Position position) {
        return ResponseEntity.ok(positionService.add(position));
    }

    @PostMapping("/update")
    public ResponseEntity<Position> update(@RequestBody @Valid final Position position) {
        return ResponseEntity.ok(positionService.update(position));
    }

    @PostMapping("/history")
    public ResponseEntity<Position> history(@RequestBody @Valid final Position position) {
        return ResponseEntity.ok(positionService.history(position));
    }

}
