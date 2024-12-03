package com.routemobile.CryptoTradeProducerService.util;

import static com.routemobile.CryptoTradeProducerService.util.CryptoTradeConstant.FILE_NAME_FORMAT;

import com.opencsv.CSVWriter;
import com.routemobile.CryptoTradeProducerService.exception.CryptoTradeReportGenerationException;
import com.routemobile.CryptoTradeProducerService.model.CryptoTradeInfo;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.MessageFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.random.RandomGenerator;
import java.util.stream.IntStream;
import lombok.extern.log4j.Log4j2;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Log4j2
@Service
public class CryptoTradeGenerator {

  @Scheduled(fixedRate = 3600000)
  private void createCsvFile() {
    String timestamp = LocalDateTime.now().format(FILE_NAME_FORMAT);
    String fileName = MessageFormat.format("Trade_Report_IN{0}.csv", timestamp);
    List<String[]> data = getRandomTradeData();
    try (CSVWriter writer = new CSVWriter(new FileWriter(fileName))) {
      writer.writeAll(data);
      log.info("CSV file created: {}, timestamp: {}", fileName, timestamp);
    } catch (IOException e) {
      log.warn("Error writing CSV file: {}, exception: {}", fileName, e.getMessage());
      throw new CryptoTradeReportGenerationException(
          MessageFormat.format("Error writing CSV file: {0}, exception: {1}", fileName, e));
    }
  }

  private List<String[]> getRandomTradeData() {
    List<String[]> randomData = new ArrayList<>();

    String[] headers = new String[]{"TradeId", "TradeTimestamp", "ConversionPair", "TradeType",
        "TradeCountry", "TradePrice", "ExchangeRate", "ApprovalStatus"};
    randomData.add(headers);
    IntStream.rangeClosed(1, 100).forEachOrdered(num -> {
      CryptoTradeInfo cd = randomCryptoData();
      randomData.add(new String[]{
          String.valueOf(cd.getTradeId()),
          String.valueOf(cd.getTimestamp()),
          String.valueOf(cd.getConversionPair()),
          cd.getTradeType(),
          cd.getTradeCountry(),
          String.valueOf(cd.getTradePrice()),
          String.valueOf(cd.getExchangeRate()),
          String.valueOf(cd.isApproved())
      });
    });
    return randomData;
  }

  private CryptoTradeInfo randomCryptoData() {
    return CryptoTradeInfo.builder()
        .conversionPair("XTC/USD")
        .tradeId(UUID.randomUUID())
        .tradeCountry("IN")
        .tradeType("SELL")
        .tradePrice(BigDecimal.valueOf(RandomGenerator.getDefault().nextDouble(100, 500)))
        .exchangeRate(BigDecimal.valueOf(RandomGenerator.getDefault().nextDouble(100, 500)))
        .timestamp(Instant.now())
        .isApproved(true)
        .build();
  }
}
