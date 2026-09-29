
package br.com.spacemining.mining;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {

    @Bean
    public Queue miningQueue() {
        return QueueBuilder
                .durable("mining.commands")
                .build();
    }
}