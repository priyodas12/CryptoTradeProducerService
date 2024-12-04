package com.routemobile.CryptoTradeProducerService.util;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import com.routemobile.CryptoTradeProducerService.exception.CryptoTradeMappingException;
import com.routemobile.CryptoTradeProducerService.exception.CryptoTradeReportGenerationException;
import com.routemobile.CryptoTradeProducerService.model.CryptoTradeInfo;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Log4j2
@Service
public class CryptoTradeReportReader {

  public List<CryptoTradeInfo> getCryptoTradeInfoList(String cryptoTradeReportFile) {
    try (CSVReader reader = new CSVReader(new FileReader(cryptoTradeReportFile))) {

      List<String[]> rows = reader.readAll();

      //skipping header values
      return rows.stream().skip(1).map(this::mapCryptoTradeInfo).toList();
    } catch (IOException | CsvException e) {
      log.warn("Error Reading Trade Report CSV file: {}, exception: {}", cryptoTradeReportFile,
          e.getMessage());
      throw new CryptoTradeReportGenerationException(
          MessageFormat.format("Error Reading Trade Report CSV file: {0}, exception: {1}",
              cryptoTradeReportFile,
              e.getMessage()));
    }
  }

  private CryptoTradeInfo mapCryptoTradeInfo(String[] row) {
    try {
      return CryptoTradeInfo.builder()
          .transactionId(UUID.fromString(row[0]))
          .conversionPair(row[1])
          .tradeType(row[2])
          .tradeCountry(row[3])
          .tradePrice(new BigDecimal(row[4]))
          .exchangeRate(new BigDecimal(row[5]))
          .isApproved(Boolean.parseBoolean(row[6]))
          .reportCreateDate(LocalDateTime.parse(row[7]))
          .build();
    } catch (Exception e) {
      log.warn("Error while mapping trade report CSV file: {}",
          e.getMessage());
      throw new CryptoTradeMappingException(e.getMessage());
    }
  }

}
