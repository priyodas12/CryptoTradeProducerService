package com.routemobile.CryptoTradeProducerService.config;

import static com.routemobile.CryptoTradeProducerService.util.CryptoTradeConstant.HOST_NAME;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMqConfig {

  @Value("${spring.rabbitmq.username}")
  private String username;

  @Value("${spring.rabbitmq.password}")
  private String password;

  @Value("${spring.rabbitmq.port}")
  private String portNumber;

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

  @Bean
  public CachingConnectionFactory connectionFactory() {

    CachingConnectionFactory connectionFactory = new CachingConnectionFactory(HOST_NAME);
    connectionFactory.setPort(Integer.parseInt(portNumber));
    connectionFactory.setUsername(username);
    connectionFactory.setPassword(password);
    return connectionFactory;
  }

  @Bean
  public RabbitTemplate rabbitTemplate(final ConnectionFactory connectionFactory) {
    final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
    rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());
    return rabbitTemplate;
  }
}
