# Trading Data Producer Service

This springboot application serves as producer of trading data.Apparently random trading data
has been generated and persisted into a .csv file for a predefined time interval,post that same
.csv data is being read and processed to publish as message in rabbitMQ.Subsequently trading data
is meant for consume by CryptoTradeConsumerService.

### Features

- Random Trading data generated for a time interval.
- Data is being published to rabbitMQ to consume by set of consumer.

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
