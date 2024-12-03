package com.routemobile.CryptoTradeProducerService.model;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * <pre>
 * Sample CryptoTradeInfo
 * {
 * "tradeId": "1e9f3b82-2cbf-4e6b-93a8-5b9d5c21a8f0",
 * "timestamp": "2024-12-03T10:00:00Z",
 * "pair": "BTC/USDT",
 * "tradeType": "buy",
 * "price": 40500.75,
 * "amount": 0.0125,
 * "total": 506.259375
 * }
 * </pre>
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CryptoTradeInfo {

  private UUID tradeId;
  private Instant timestamp;
  private String conversionPair;
  private String tradeType;
  private String tradeCountry;
  private BigDecimal tradePrice;
  private BigDecimal exchangeRate;
  private boolean isApproved;

}

