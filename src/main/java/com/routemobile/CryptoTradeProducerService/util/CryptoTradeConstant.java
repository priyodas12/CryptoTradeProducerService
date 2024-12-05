package com.routemobile.CryptoTradeProducerService.util;

import java.time.format.DateTimeFormatter;

public class CryptoTradeConstant {

  public static final DateTimeFormatter DEFAULT_FILE_NAME_FORMAT = DateTimeFormatter.ofPattern(
      "yyyy-MM-dd_HH_mm_ss");

  public static final String CRYPTO_TRADE_REPORT_FILE = "Final_Trade_Report_IN.csv";

  //scheduler refresh time
  public static final int DEFAULT_REFRESH_TIME = 60 * 1000;

  //localhost
  public static final String HOST_NAME = "localhost";

}
