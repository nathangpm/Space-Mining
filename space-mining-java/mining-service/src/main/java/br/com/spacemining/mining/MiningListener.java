package br.com.spacemining.mining;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
@Component
public class MiningListener {
 private final CommandCountRepository repository;
 public MiningListener(CommandCountRepository repository){this.repository=repository;}
 @RabbitListener(queues="mining.commands", concurrency="1")
 @Transactional
 public void execute(String command) {
   System.out.println("🤖 Robô executando comando: " + command);
   CommandCount counter=repository.findByCommand(command).orElseGet(()->new CommandCount(command));
   counter.increment();
   repository.save(counter);
   System.out.println("✅ Comando " + command + " executado. Total: " + counter.getCount());
 }
}
