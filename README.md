[![Build Status](https://travis-ci.org/Zanoshky/CurrencyFair.svg?branch=master)](https://travis-ci.org/Zanoshky/CurrencyFair)

# CurrencyFair
**A simple Market Trade Processor to Vizualize Market Trade Volume of Various Currencies**

This is a [proof-of-concept application](https://en.wikipedia.org/wiki/Proof_of_concept), which implements Market Trade Processor for company CurrencyFair interview process by using Spring Boot and Docker.

## Functional Services

CurrenctFair was decomposed into three core microservices. All of them are independently deployable applications, organized around certain business domains.

<img width="880" alt="Functional Services" src="https://github.com/Zanoshky/CurrencyFair/blob/master/FunctionalServices.png">

### Message Consumption Service
- Exposes an endpoint which consumes market messages.
- Contains general user input logic and validation.
- Trade messages are acceptable in following JSON form:

```json
{"userId": "134256", "currencyFrom": "EUR", "currencyTo": "GBP", "amountSell": 1000, "amountBuy": 747.10, "rate": 0.7471, "timePlaced" : "24-JAN-15 10:27:44", "originatingCountry" : "FR"}
```

###$ Message Consumption Service API's
Method	| Path	| Description	| User authenticated
------------- | ------------------------- | ------------- |:-------------:|
GET	  | /api/trades	| Get all information about all trades            | ×
GET	  | /api/volume-trades | Get only volume information about trades | ×
POST	| /api/trade	| Post a trade to the Market Trade Processor      | ×


