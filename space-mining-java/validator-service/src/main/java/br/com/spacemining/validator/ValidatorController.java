package br.com.spacemining.validator;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/validate")
public class ValidatorController {
 private static final Set<String> ALLOWED = Set.of("RIGHT","LEFT","FRONT","BACK","OPEN","CLOSE");
 private final RabbitTemplate rabbit;
 private final Random random = new Random();
 public ValidatorController(RabbitTemplate rabbit) { this.rabbit = rabbit; }

 @PostMapping
 public ResponseEntity<?> validate(@RequestBody Map<String,String> body) {
   String command = body.getOrDefault("command", "").trim().toUpperCase(Locale.ROOT);
   if (!ALLOWED.contains(command))
     return ResponseEntity.badRequest().body(Map.of("error","Comando inválido","allowed",ALLOWED));
   for (int attempt=1; attempt<=3; attempt++) {
     if (random.nextBoolean()) {
       rabbit.convertAndSend("mining.commands", command);
       return ResponseEntity.accepted().body(Map.of("status","Enviado para mineração","command",command,"attempt",attempt));
     }
     if (attempt < 3) {
       try { Thread.sleep(250L * (1L << (attempt-1))); }
       catch (InterruptedException e) { Thread.currentThread().interrupt(); return ResponseEntity.status(503).body(Map.of("error","Retry interrompido")); }
     }
   }
   return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
       .body(Map.of("error","Falha simulada após 3 tentativas; tente novamente"));
 }
}
