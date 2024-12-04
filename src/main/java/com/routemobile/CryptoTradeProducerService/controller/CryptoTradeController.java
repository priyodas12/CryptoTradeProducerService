package com.routemobile.CryptoTradeProducerService.controller;

import com.routemobile.CryptoTradeProducerService.model.CryptoTradeInfo;
import com.routemobile.CryptoTradeProducerService.service.CryptoTradeService;
import java.util.List;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RequestMapping("/api/v1/crypto-trade-info")
@RestController
public class CryptoTradeController {

  private final CryptoTradeService cryptoTradeService;

  public CryptoTradeController(CryptoTradeService cryptoTradeService) {
    this.cryptoTradeService = cryptoTradeService;
  }

  /**
   * HttpStatus: 200 ,Response:
   * <pre>
   *   {"state": "running"}
   * </pre>
   */
  @GetMapping("/health")
  public ResponseEntity<Map<String, String>> health() {
    return ResponseEntity.ok(Map.of("state", "running"));
  }

  /**
   * HttpStatus: 201, Response:
   * <pre>
   * [
   *   {
   *     "transactionId": "3b9d27db-2562-47aa-8dfc-f87e0d472fb5",
   *     "conversionPair": "SOL/YEN",
   *     "tradeType": "BUY",
   *     "tradeCountry": "IN",
   *     "tradePrice": 435.6243975776997,
   *     "exchangeRate": 147.24248431600714,
   *     "reportCreateDate": "2024-12-05T00:45:37.7771123",
   *     "approved": true
   *   },
   *   {
   *     "transactionId": "8b9d27db-2562-47aa-8dfc-f87e0d472fb2",
   *     "conversionPair": "ETH/EUR",
   *     "tradeType": "BUY",
   *     "tradeCountry": "US",
   *     "tradePrice": 435.6243975776997,
   *     "exchangeRate": 147.24248431600714,
   *     "reportCreateDate": "2024-12-06T00:45:37.7771123",
   *     "approved": true
   *   },
   *   {
   *     "transactionId": "7b9d27db-2562-47aa-8dfc-f87e0d472fb3",
   *     "conversionPair": "BTC/INR",
   *     "tradeType": "SELL",
   *     "tradeCountry": "DK",
   *     "tradePrice": 435.6243975776997,
   *     "exchangeRate": 147.24248431600714,
   *     "reportCreateDate": "2024-12-07T00:45:37.7771123",
   *     "approved": true
   *   }
   * ]</pre>
   */
  @PostMapping("/report")
  public ResponseEntity<List<CryptoTradeInfo>> manualPublishReport(
      @RequestBody List<CryptoTradeInfo> cryptoTradeInfoList) {
    var response = cryptoTradeService.forcePublishLatestCryptoTradeReport(cryptoTradeInfoList);
    return ResponseEntity.status(HttpStatus.CREATED).body(response);
  }

}
