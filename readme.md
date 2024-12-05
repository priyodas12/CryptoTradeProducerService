# Trading Data Producer Service

This springboot application serves as producer of trading data.Apparently random trading data
has been generated and persisted into a .csv file for a predefined time interval,post that same
.csv data is being read and processed to publish as message in rabbitMQ.Subsequently trading data
is meant for consume by CryptoTradeConsumerService.

### Features

- Random Trading data generated for a time interval.
- Trading Data is being published to rabbitMQ to consume by set of consumer.

### Technologies Used

- **Spring Boot 3.x**
- **Java 17**
- **rabbitMQ**
- **Docker**
- **Maven**
- **Lombok**
- **OpenCsv**

### Setup and Installation

- ** https://start.spring.io **

### Docker Set up for rabbitMQ: used docker desktop

```bash
docker run -d --hostname crypto-trade-report-queue --name trade-report-queue -e RABBITMQ_DEFAULT_USER=<*****> -e RABBITMQ_DEFAULT_PASS=<*****>  -p 5672:5672 -p 15672:15672 rabbitmq:4.0.4-management

docker ps -a

```

```
Manual Data Publish API: 
http://localhost:12340/api/v1/crypto-trade-info/report

REQUEST:
[
  {
    "transactionId": "8b9d27db-2562-47aa-8dfc-f87e0d472fb5",
    "conversionPair": "SOL/YEN",
    "tradeType": "BUY",
    "tradeCountry": "US",
    "tradePrice": 435.6243975776997,
    "exchangeRate": 147.24248431600714,
    "isApproved": true,
    "reportCreateDate": "2024-12-05T00:45:37.777112300"
  }
]

RESPONSE: 201

[
  {
    "transactionId": "8b9d27db-2562-47aa-8dfc-f87e0d472fb5",
    "conversionPair": "SOL/YEN",
    "tradeType": "BUY",
    "tradeCountry": "US",
    "tradePrice": 435.6243975776997,
    "exchangeRate": 147.24248431600714,
    "isApproved": true,
    "reportCreateDate": "2024-12-05T00:45:37.7771123"
  }
]

Health:
http://localhost:12340/api/v1/crypto-trade-info/health

RESPONSE: 200
{
  "state": "running"
}
```
