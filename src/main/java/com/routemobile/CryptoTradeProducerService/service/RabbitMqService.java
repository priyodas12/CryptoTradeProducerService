package com.routemobile.CryptoTradeProducerService.service;

import static com.routemobile.CryptoTradeProducerService.util.CryptoTradeConstant.CRYPTO_TRADE_REPORT_FILE;

import com.routemobile.CryptoTradeProducerService.exception.RabbitMqMessageDeliveryException;
import lombok.extern.log4j.Log4j2;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Log4j2
@Service
public class RabbitMqService {

  private final AmqpTemplate amqpTemplate;

  @Value("${spring.rabbitmq.template.routing-key}")
  private String routingKey;

  @Value("${spring.rabbitmq.template.default-receive-queue}")
  private String defaultReceiveQueue;

  @Value("${spring.rabbitmq.template.exchange}")
  private String exchangeInfo;


  @Autowired
  public RabbitMqService(AmqpTemplate amqpTemplate) {
    this.amqpTemplate = amqpTemplate;
  }

  public void publishMessage(String cryptoTradeReport) {
    try {
      log.info("Publishing to Queue: {}, message context: {}", defaultReceiveQueue,
          cryptoTradeReport);
      amqpTemplate.convertAndSend(exchangeInfo, routingKey, cryptoTradeReport);
    } catch (Exception e) {
      log.error("Error publishing latest crypto trade report: {}, exception: {}",
          CRYPTO_TRADE_REPORT_FILE, e.getMessage());
      throw new RabbitMqMessageDeliveryException(e.getMessage());
    }
  }
}
