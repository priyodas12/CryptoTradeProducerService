package com.routemobile.CryptoTradeProducerService.exception;

public class RabbitMqMessageDeliveryException extends RuntimeException {

  public RabbitMqMessageDeliveryException(String message) {
    super(message);
  }
}
