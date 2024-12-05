package com.routemobile.CryptoTradeProducerService.util;

import static com.routemobile.CryptoTradeProducerService.util.CryptoTradeConstant.DEFAULT_FILE_NAME_FORMAT;

import com.opencsv.CSVWriter;
import com.routemobile.CryptoTradeProducerService.exception.CryptoTradeReportGenerationException;
import com.routemobile.CryptoTradeProducerService.model.CryptoTradeInfo;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.random.RandomGenerator;
import java.util.stream.IntStream;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Log4j2
@Service
public class CryptoTradeReportGenerator {

  public void persistCsvFile(String cryptoTradeReportFile) {

    String timestamp = LocalDateTime.now().format(DEFAULT_FILE_NAME_FORMAT);

    List<String[]> data = getRandomTradeData();
    try (CSVWriter writer = new CSVWriter(new FileWriter(cryptoTradeReportFile))) {
      writer.writeAll(data);
      log.info("Trade Report Updated: {}, timestamp: {}", cryptoTradeReportFile, timestamp);
    } catch (IOException e) {
      log.warn("Error writing Trade Report CSV file: {}, exception: {}", cryptoTradeReportFile,
          e.getMessage());
      throw new CryptoTradeReportGenerationException(
          MessageFormat.format("Error writing Trade Report CSV file: {0}, exception: {1}",
              cryptoTradeReportFile,
              e));
    }
  }


  private List<String[]> getRandomTradeData() {
    List<String[]> randomData = new ArrayList<>();

    String[] headers = new String[]{"TransactionId", "TradeTimestamp", "ConversionPair",
        "TradeType", "TradeCountry", "TradePrice", "ExchangeRate", "ApprovalStatus", "CreateDate"};
    randomData.add(headers);
    IntStream.rangeClosed(1, 100).forEachOrdered(num -> {
      CryptoTradeInfo cd = randomCryptoData();
      randomData.add(new String[]{
          String.valueOf(cd.getTransactionId()),
          String.valueOf(cd.getConversionPair()),
          cd.getTradeType(),
          cd.getTradeCountry(),
          String.valueOf(cd.getTradePrice()),
          String.valueOf(cd.getExchangeRate()),
          String.valueOf(cd.isApproved()),
          cd.getReportCreateDate().toString()
      });
    });
    return randomData;
  }

  private CryptoTradeInfo randomCryptoData() {
    return CryptoTradeInfo.builder()
        .conversionPair(getConversionPair())
        .transactionId(UUID.randomUUID())
        .tradeCountry(getCountry())
        .tradeType(getTradeType())
        .tradePrice(BigDecimal.valueOf(RandomGenerator.getDefault().nextDouble(100, 500)))
        .exchangeRate(BigDecimal.valueOf(RandomGenerator.getDefault().nextDouble(100, 500)))
        .isApproved(true)
        .reportCreateDate(LocalDateTime.now())
        .build();
  }

  private String getConversionPair() {
    return List.of("ETH-USD", "LTC-EUR", "SOL-YEN", "BTC-INR")
        .get(RandomGenerator.getDefault().nextInt(0, 3));
  }

  private String getCountry() {
    return List.of("US", "IN", "JP", "CH")
        .get(RandomGenerator.getDefault().nextInt(0, 3));
  }

  private String getTradeType() {
    return List.of("BUY", "SELL", "HOLD")
        .get(RandomGenerator.getDefault().nextInt(0, 2));
  }
}
