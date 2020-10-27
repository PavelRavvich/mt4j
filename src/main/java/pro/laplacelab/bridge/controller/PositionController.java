package pro.laplacelab.bridge.controller;


import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pro.laplacelab.bridge.model.Position;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@RestController
@AllArgsConstructor
@RequestMapping("/api/position")
public class PositionController {


    @PostMapping("/add")
    public ResponseEntity<Position> add(@RequestBody @Valid final Position position) {
        return ResponseEntity.ok(new Position());
    }

    @PostMapping("/{id}/update")
    public ResponseEntity<Position> update(@PathVariable @NotNull final Long id,
                                           @RequestBody @Valid final Position position) {
        return ResponseEntity.ok(new Position());
    }


    @PostMapping("/{id}/delete")
    public ResponseEntity<Position> delete(@PathVariable @NotNull final Long id) {
        return ResponseEntity.ok(new Position());
    }

}
