package pro.laplacelab.bridge.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pro.laplacelab.bridge.service.ConfigService;

import java.util.UUID;

@RestController
@AllArgsConstructor
@RequestMapping("/api/config")
public class ConfigController {

    private final ConfigService configService;

    @PostMapping
    public ResponseEntity<UUID> set(@RequestBody String config) {
        return ResponseEntity.ok(configService.configure(config));
    }
}
