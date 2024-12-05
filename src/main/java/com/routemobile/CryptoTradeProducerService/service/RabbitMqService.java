package com.routemobile.CryptoTradeProducerService.service;

import static com.routemobile.CryptoTradeProducerService.util.CryptoTradeConstant.CRYPTO_TRADE_REPORT_FILE;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.routemobile.CryptoTradeProducerService.exception.RabbitMqMessageDeliveryException;
import com.routemobile.CryptoTradeProducerService.model.CryptoTradeInfo;
import lombok.extern.log4j.Log4j2;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Log4j2
@Service
public class RabbitMqService {

  private final RabbitTemplate rabbitTemplate;

  private final ObjectMapper objectMapper;

  @Value("${spring.rabbitmq.template.routing-key}")
  private String routingKey;

  @Value("${spring.rabbitmq.template.default-receive-queue}")
  private String defaultReceiveQueue;

  @Value("${spring.rabbitmq.template.exchange}")
  private String exchangeInfo;


  @Autowired
  public RabbitMqService(RabbitTemplate rabbitTemplate, ObjectMapper objectMapper) {
    this.rabbitTemplate = rabbitTemplate;
    this.objectMapper = objectMapper;
  }

  public void publishMessage(CryptoTradeInfo cryptoTradeReport) {
    try {
      log.info("Publishing to Queue: {}, message context: {}", defaultReceiveQueue,
          cryptoTradeReport);
      rabbitTemplate.convertAndSend(exchangeInfo, routingKey, cryptoTradeReport);
    } catch (Exception e) {
      log.error("Error publishing latest crypto trade report: {}, exception: {}",
          CRYPTO_TRADE_REPORT_FILE, e.getMessage());
      throw new RabbitMqMessageDeliveryException(e.getMessage());
    }
  }
}
