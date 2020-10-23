package pro.laplacelab.bridge.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController("/api/config")
@AllArgsConstructor
public class ConfigController {
    @PostMapping
    void set(@RequestBody String config) {

    }
}
