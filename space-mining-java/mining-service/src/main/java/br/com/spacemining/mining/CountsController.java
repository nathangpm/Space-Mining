package br.com.spacemining.mining;
import org.springframework.web.bind.annotation.*;
import java.util.*;
import java.util.stream.Collectors;
@RestController
@RequestMapping("/commands")
public class CountsController {
 private final CommandCountRepository repository;
 public CountsController(CommandCountRepository repository){this.repository=repository;}
 @GetMapping("/counts")
 public Map<String,Long> counts(){
   return repository.findAll().stream().collect(Collectors.toMap(CommandCount::getCommand,CommandCount::getCount));
 }
}
