package com.routemobile.CryptoTradeProducerService.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.Builder;
import lombok.Data;

/**
 * <pre>
 * {
 * "transactionId": "8b9d27db-2562-47aa-8dfc-f87e0d472fb5",
 * "conversionPair": "SOL/YEN",
 * "tradeType": "BUY",
 * "tradeCountry": "US",
 * "tradePrice": 435.6243975776997,
 * "exchangeRate": 147.24248431600714,
 * "isApproved": true,
 * "reportCreateDate": "2024-12-05T00:45:37.777112300"
 * }
 * </pre>
 */

@Builder
@Data
public class CryptoTradeInfo implements Serializable {

  @JsonProperty("transactionId")
  private UUID transactionId;

  @JsonProperty("conversionPair")
  private String conversionPair;

  @JsonProperty("tradeType")
  private String tradeType;

  @JsonProperty("tradeCountry")
  private String tradeCountry;

  @JsonProperty("tradePrice")
  private BigDecimal tradePrice;

  @JsonProperty("exchangeRate")
  private BigDecimal exchangeRate;

  @JsonProperty("isApproved")
  private boolean isApproved;

  @JsonProperty("reportCreateDate")
  private LocalDateTime reportCreateDate;
}

