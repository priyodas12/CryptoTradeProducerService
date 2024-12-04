package com.routemobile.CryptoTradeProducerService.service;

import static com.routemobile.CryptoTradeProducerService.util.CryptoTradeConstant.CRYPTO_TRADE_REPORT_FILE;
import static com.routemobile.CryptoTradeProducerService.util.CryptoTradeConstant.DEFAULT_REFRESH_TIME;

import com.routemobile.CryptoTradeProducerService.model.CryptoTradeInfo;
import com.routemobile.CryptoTradeProducerService.util.CryptoTradeReportGenerator;
import com.routemobile.CryptoTradeProducerService.util.CryptoTradeReportReader;
import java.time.Instant;
import java.util.List;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@Log4j2
public class CryptoTradeService {

  private final CryptoTradeReportGenerator cryptoTradeReportGenerator;

  private final CryptoTradeReportReader cryptoTradeReportReader;

  private final RabbitMqService rabbitMqService;

  @Autowired
  public CryptoTradeService(CryptoTradeReportGenerator cryptoTradeReportGenerator,
      CryptoTradeReportReader cryptoTradeReportReader, RabbitMqService rabbitMqService) {
    this.cryptoTradeReportGenerator = cryptoTradeReportGenerator;
    this.cryptoTradeReportReader = cryptoTradeReportReader;
    this.rabbitMqService = rabbitMqService;
  }

  private void createCryptoTradeReport() {
    cryptoTradeReportGenerator.persistCsvFile(CRYPTO_TRADE_REPORT_FILE);
  }

  private List<CryptoTradeInfo> readCryptoTradeReport() {
    return cryptoTradeReportReader.getCryptoTradeInfoList(CRYPTO_TRADE_REPORT_FILE);
  }

  @Scheduled(fixedRate = DEFAULT_REFRESH_TIME)
  public List<CryptoTradeInfo> publishLatestCryptoTradeReport() {
    log.info("Publishing latest crypto trade report at: {}", Instant.now());
    createCryptoTradeReport();
    List<CryptoTradeInfo> latestReport = readCryptoTradeReport();

    System.out.println("Latest crypto trade report: " + latestReport);
    latestReport.forEach(
        cryptoTradeReportRow -> rabbitMqService.publishMessage(
            String.valueOf(cryptoTradeReportRow)));
    return latestReport;
  }

  public List<CryptoTradeInfo> forcePublishLatestCryptoTradeReport(
      List<CryptoTradeInfo> cryptoTradeInfoList) {
    log.info("Force Publishing latest crypto trade report at: {}", Instant.now());

    cryptoTradeInfoList.forEach(
        cryptoTradeReportRow -> rabbitMqService.publishMessage(
            String.valueOf(cryptoTradeReportRow)));
    return cryptoTradeInfoList;
  }

}
