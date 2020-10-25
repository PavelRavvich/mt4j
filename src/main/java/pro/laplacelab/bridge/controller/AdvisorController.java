package pro.laplacelab.bridge.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pro.laplacelab.bridge.model.Property;
import pro.laplacelab.bridge.service.PropertyService;

import java.util.List;
import java.util.UUID;

@RestController
@AllArgsConstructor
@RequestMapping("/api/advisor")
public class AdvisorController {

    private final PropertyService propertyService;

    @PostMapping("/connect")
    public ResponseEntity<UUID> set(@RequestBody List<Property> properties) {
        return ResponseEntity.ok(propertyService.add(properties));
    }

}
