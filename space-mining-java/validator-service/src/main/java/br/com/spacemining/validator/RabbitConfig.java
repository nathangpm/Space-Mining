package br.com.spacemining.validator;
import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
@Configuration
public class RabbitConfig {
 @Bean public Queue miningQueue() { return QueueBuilder.durable("mining.commands").build(); }
}
