package com.routemobile.CryptoTradeProducerService;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class CryptoTradeProducerServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(CryptoTradeProducerServiceApplication.class, args);
	}

}
