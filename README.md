[![Build Status](https://travis-ci.org/Zanoshky/CurrencyFair.svg?branch=master)](https://travis-ci.org/Zanoshky/CurrencyFair)

# CurrencyFair
**A simple Market Trade Processor to Visualize Market Trade Volume of Various Currencies**

This is a [proof-of-concept application](https://en.wikipedia.org/wiki/Proof_of_concept), which implements Market Trade Processor for company [CurrencyFair](https://www.currencyfair.com/) interview process by using Spring Boot and Docker.

---

# Prerequisites for development
- Manually connect to MySQL Server and create database with name **currency_fair** with type: **utf8_unicode_ci**
- Manually create new user for database:
    - username: **root** 
    - password: **root** 
    - rights: **ALL_RIGHTS**
- Import project in IDE
    - [How to: Import Maven project in IntelliJ IDEA](https://www.packtpub.com/mapt/book/application_development/9781785286124/2/ch02lvl1sec25/importing-an-existing-maven-project-in-intellij-idea)
    - [How to: Import Maven project in Eclipse](https://books.sonatype.com/m2eclipse-book/reference/creating-sect-importing-projects.html)
    - [How to: Import Maven project in Netbeans](https://www.packtpub.com/mapt/book/application_development/9781785286124/2/ch02lvl1sec23/importing-an-existing-maven-project-in-netbeans)
- Run **ConsumptionApplication.java** in IDE Run Configuration
- Run **ProcessorApplication.java** in IDE Run Configuration
- Run **FrontendApplication.java** in IDE Run Configuration

# Prerequisites for local testing
- Manually connect to MySQL Server and create database with name **currency_fair** with type: **utf8_unicode_ci**
- Manually create new user for database:
    - username: **root** 
    - password: **root** 
    - rights: **ALL_RIGHTS**
- Open console window
- Change dir to root folder of project
- Execute
    ```console
    mvn clean install
    cd deployment/docker/message-consumption/target/
    java -jar message-consumption.jar
    ```
- Open new console window
- Change dir to root folder of project
- Execute
    ```console
    cd deployment/docker/message-processor/target/
    java -jar message-processor.jar
    ```
- Open new console window
- Change dir to root folder of project
- Execute
    ```console
    cd deployment/docker/message-frontend/target/
    java -jar message-frontend.jar
    ```
    
# Prerequisites for Docker deployment
- Install [Docker Toolbox On Windows](https://docs.docker.com/toolbox/toolbox_install_windows/) if on Windows 7
    - Use dockerhost IP address - default **192.168.99.100**
- Install [Docker for Windows](https://docs.docker.com/docker-for-windows/install/#install-docker-for-windows-desktop-app) if on Windows 10
    - You can use localhost
- Open root folder of project
- Execute
    ```console
    mvn clean install
    cd deployment/docker
    docker-compose up --build
    ```

---

## Functional Services
CurrencyFair was decomposed into three core services. 

All of them are independently deployable applications, organized around certain business domains.

<img width="880" alt="Functional Services" src="https://github.com/Zanoshky/CurrencyFair/blob/master/documentation/FunctionalServices.png">

---

## Message Consumption Service
- Service is using Java / Spring Boot / Tomcat.
- Service is running on port 8001.
- Client can POST a message to API endpoint with JSON Payload.
- Service validates clients JSON Payload.
- Multiple messages in single JSON Payload are not acceptable.
- Service consumes a single message in following acceptable JSON Form:
    ```
    userId              : String
    currencyFrom        : String (3)
    currencyTo          : String (3)
    amountSell          : Double (10,2)
    amountBuy           : Double (10,2)
    rate                : Double (10,5)
    timePlaced          : Date (dd-MMM-yy HH:mm:ss)
    originatingCountry  : String (2)
    ```
- **Example**
    ```json
    {
       "userId":"134256",
       "currencyFrom":"EUR",
       "currencyTo":"GBP",
       "amountSell":1000,
       "amountBuy":747.10,
       "rate":0.7471,
       "timePlaced":"24-JAN-15 10:27:44",
       "originatingCountry":"FR"
    }
  ```
- Swagger2 API Documentation:
    - [Windows 7 - Docker](http://192.168.99.100:8001/swagger-ui.html) - http://192.168.99.100:8001/swagger-ui.html
    - [Windows 10 - Docker / localhost](http://localhost:8001/swagger-ui.html) - http://localhost:8001/swagger-ui.html

#### Message Consumption Service API's
Method	| Path	| Description	| User authenticated
------- | ----- | ------------- |:----------------:|
GET	    | /api/message/{messageId}	                   | Returns all information about Message for given Message ID                               |
GET	    | /api/messages	                               | Returns all information about all Messages                                               |
POST    | /api/message	                               | Saves a trade message to the Market Trade Processor                                      |
GET	    | /api/volume-message/{volumeMessageId}        | Returns filtered information (id, currencies, timestamp) for given Volume Message ID     |
GET	    | /api/volume-messages                         | Returns filtered information (id, currencies, timestamp) for all Messages                |
GET	    | /api/volume-messages/above/{lastProcessedId} | Returns filtered information (id, currencies, timestamp) for all Messages above given ID |

Approach taken:
- [x] Easy : Consumed messages are written to disk for rendering in the frontend. 

---

### Message Processor Service
- Service is using Java / Spring Boot / Tomcat.
- Service is running on port 8002.
- Internal intermediate service for communication between remaining services.
- Service communicates with **Message-Consumer** to get filtered information about messages from its database.
- Service communicates with **Message-Consumer** API on scheduled basis every 10 seconds.
- Service processes the information and stores statistical data into its own internal database after consuming **Message-Consumer** API.
- Service performs statistical calculation on volume of messages about specific pair of currency trade in time period on request.
- Service consumes volume messages in following acceptable JSON form:
    ```
    id            : Long
    currencyFrom  : String (3)
    currencyTo    : String (3)
    timePlaced    : TimeStamp (yyyy-MM-ddTHH:mm:ss)
    ```
- **Example**
    ```json
    {
        "id":"134256",
        "currencyFrom":"EUR",
        "currencyTo":"GBP",
        "timePlaced":"2018-09-20T10:27:44"
    }
    ```
- Service exposes POST API for **Message-Frontend** to provide calculated statistical data about currency pair volumes.
- Swagger2 API Documentation:
    - [Windows 7 - Docker](http://192.168.99.100:8002/swagger-ui.html) - http://192.168.99.100:8002/swagger-ui.html
    - [Windows 10 - Docker / localhost](http://localhost:8002/swagger-ui.html) - http://localhost:8002/swagger-ui.html

---

#### Message Processor Service API's
Method	| Path	| Description	| User authenticated
------- | ----- | ------------- |:----------------:|
GET	    | /api/currency-pair/{currencyPairId}	         | Returns all information about Currency Pair for given ID                    |
GET	    | /api/currency-pairs	                         | Returns all information about all Currency Pairs                            |
GET	    | /api/currency-pair-details	                 | Returns all information about all Currency Pair Details                     |
GET     | /api//charts-for-last-minutes/{numberOfMinutes}| Returns statistical information about currency pairs for last given minutes |

Approach taken:
- [x] Average : Analyse incoming messages for trends, and transform data to prepare for a more visual frontend rendering, e.g. graphing currency volume of messages from one particular currency pair market (EUR/GBP).

---

### Message Frontend Service
- Service is using Java / Spring Boot / Tomcat / ChartJS.
- Service is running on port 8080.
- Frontend service to visually represents statistical information from the communication between remaining services and their internal processing.
- Service communicates with **Message-Processor** to get statistical information about messages from its database.
- Swagger2 API Documentation:
    - [Windows 7 - Docker](http://192.168.99.100:8080/swagger-ui.html) - http://192.168.99.100:8080/swagger-ui.html
    - [Windows 10 - Docker / localhost](http://localhost:8080/swagger-ui.html) - http://localhost:8080/swagger-ui.html

Approach taken:
- [x] Average : Render a graph of processed data from the messages consumed.