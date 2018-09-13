[![Build Status](https://travis-ci.org/Zanoshky/CurrencyFair.svg?branch=master)](https://travis-ci.org/Zanoshky/CurrencyFair)

# CurrencyFair
**A simple Market Trade Processor to Visualize Market Trade Volume of Various Currencies**

This is a [proof-of-concept application](https://en.wikipedia.org/wiki/Proof_of_concept), which implements Market Trade Processor for company [CurrencyFair](https://www.currencyfair.com/) interview process by using Spring Boot and Docker.

# Prerequisites
- Manually connect to MySQL Server and create database with name **currency_fair** with type: **utf8_unicode_ci**

## Functional Services
CurrencyFair was decomposed into three core microservices. All of them are independently deployable applications, organized around certain business domains.

<img width="880" alt="Functional Services" src="https://github.com/Zanoshky/CurrencyFair/blob/master/documentation/FunctionalServices.png">

### Message Consumption Service
- Contains public accessible API endpoints.
- User can POST a message (trade) to API endpoint with JSON payload.
- Service validates user inputs.
- Multiple messages in single JSON are not acceptable.
- Service consumes a single message in following acceptable JSON form:
    - JSON Format
        - userId : String
        - currencyFrom : String (3)
        - currencyTo : String (3)
        - amountSell : Double (10,2)
        - amountBuy : Double (10,2)
        - rate : Double (10,5)
        - timePlaced : TimeStamp (dd-MMM-yy HH:mm:ss)
        - originatingCountry : String (2)
    - **Example**
    ```json
    {"userId": "134256", "currencyFrom": "EUR", "currencyTo": "GBP", "amountSell": 1000, "amountBuy": 747.10, "rate": 0.7471, "timePlaced" : "24-JAN-15 10:27:44", "originatingCountry" : "FR"}
    ```

#### Message Consumption Service API's
Method	| Path	| Description	| User authenticated
------------- | ------------------------- | ------------- |:-------------:|
GET	    | /api/messages	        | Returns all information about all messages                             | ×
GET	    | /api/volume-messages  | Returns filtered information about messages (id, currencies, timestamp)| ×
POST    | /api/trade	        | Saves a trade message to the Market Trade Processor                    | ×

Approach taken:
- [x] Easy : Consumed messages are written to disk for rendering in the frontend. 

### Message Processor Service
- No public API's, only internal for communication between services.
- Service consumes **Message-Consumer** to get filtered information about messages from its database.
- Service performs statistics calculation on volume of messages about specific pair of currency trade in time period.
- Service consumes volume messages in following acceptable JSON form:
    - JSON Format
        - id : Long
        - currencyFrom : String (3)
        - currencyTo : String (3)
        - timePlaced : TimeStamp (yyyy-MM-dd'T'HH:mm:ss.SSS'Z')
    - **Example**
    ```json
    {"id": "134256", "currencyFrom": "EUR", "currencyTo": "GBP", "timePlaced" : "2014-12-28T10:27:44.000+0000"}
    ```
- Service exposes POST API for **Message-Frontend** to provide calculated statistical data about currency pair volumes.

#### Message Processor Service API's
Method	| Path	| Description	| User authenticated
------------- | ------------------------- | ------------- |:-------------:|
POST    | /api/statistics       | Post a trade to the Market Trade Processor          | ×

Approach taken:
- [x] Average : Analyse incoming messages for trends, and transform data to prepare for a more visual frontend rendering, e.g. graphing currency volume of messages from one particular currency pair market (EUR/GBP). 