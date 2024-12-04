package com.routemobile.CryptoTradeProducerService.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMqConfig {

  @Value("${spring.rabbitmq.template.routing-key}")
  private String routingKey;

  @Value("${spring.rabbitmq.template.default-receive-queue}")
  private String defaultReceiveQueue;

  @Value("${spring.rabbitmq.template.exchange}")
  private String exchangeInfo;

  @Bean
  public Exchange defaultExchange() {
    return new DirectExchange(exchangeInfo);
  }

  @Bean
  public Queue defaultPublicQueue() {
    return new Queue(defaultReceiveQueue, true);
  }

  @Bean
  public Binding defaultBinding(Queue queue, Exchange exchange) {
    return BindingBuilder.bind(queue).to(exchange).with(routingKey).noargs();
  }
}
