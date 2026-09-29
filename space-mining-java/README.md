# Space Mining — Java Advanced

Projeto didático baseado no Exercício 06 - Space Mining.

## Requisitos
- JDK 21
- Maven 3.9+
- Docker e Docker Compose
- IntelliJ IDEA
- Postman (opcional)

## Arquitetura
- `eureka-server`: descoberta de serviços (porta 8761)
- `command-service`: API `POST /command` (porta 8081)
- `validator-service`: valida comandos, simula falha de 50%, aplica retry com exponential backoff e publica no RabbitMQ (porta 8082)
- `mining-service`: consome a fila, registra execução e persiste contagens em H2 (porta 8083)
- RabbitMQ: `localhost:5672`; painel `http://localhost:15672` (guest/guest)

## Executar
1. Inicie o RabbitMQ: `docker compose up -d`
2. Abra cada pasta como projeto Maven no IntelliJ e aguarde o download das dependências.
3. Execute nesta ordem: `EurekaServerApplication`, `ValidatorServiceApplication`, `MiningServiceApplication`, `CommandServiceApplication`.
4. Acesse o Eureka em `http://localhost:8761`.

## Teste
Envie `POST http://localhost:8081/command` com `Content-Type: application/json`:
```json
{ "command": "LEFT" }
```
Comandos válidos: `RIGHT`, `LEFT`, `FRONT`, `BACK`, `OPEN`, `CLOSE`.
Consulte as contagens em `GET http://localhost:8083/commands/counts`.

A falha de validação é intencionalmente simulada em 50% das tentativas. O retry usa backoff exponencial limitado a 3 tentativas. Como é uma simulação didática, o serviço responde com erro se todas as tentativas falharem.

## Apresentação
O Command Service recebe a ordem e chama o Validator Service via HTTP. O Validator valida o texto, trata falhas transitórias com retry/backoff e publica a ordem na fila. O Mining Service consome a mensagem de forma assíncrona, simula o robô com log e atualiza a contagem no H2. O Eureka registra os serviços. O RabbitMQ desacopla o recebimento da execução, permitindo que o consumidor processe mensagens no seu ritmo.

## Observação sobre backpressure
O consumidor está configurado com concorrência limitada (`concurrency=1`) e prefetch baixo (`prefetch=1`), ajudando a evitar sobrecarga. Para produção, seriam necessários também limites de fila, métricas, idempotência, política de dead-letter e segurança.
