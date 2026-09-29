package br.com.spacemining.command;

import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import java.util.Map;

@RestController
@RequestMapping("/command")
public class CommandController {
    private final RestTemplate restTemplate = new RestTemplate();

    @PostMapping
    public ResponseEntity<?> send(@RequestBody Map<String, String> body) {
        try {
            return restTemplate.postForEntity("http://localhost:8082/validate",
                    body, Map.class);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
                    .body(Map.of("error", "Validator indisponível", "detail", e.getMessage()));
        }
    }
}
