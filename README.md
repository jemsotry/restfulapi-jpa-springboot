### Stack
- Spring Boot
- Java 8
- Maven 3
- JPA - MySQL
- Integration test
- VueJS

### Tools you need
- Java 8
- Maven 3
- Docker

### Build and run
`mvn clean spring-boot:run`

### Run the tests only
`mvn clean verify`

### Test the APIs
- Get all products `curl http://localhost:8080/api/v1/products`
- Get stock 1 `curl http://localhost:8080/api/v1/products/1`
- Update price of stock 1 `curl -X PUT -d 'currentPrice=123' http://localhost:8080/api/v1/products/1`
- Update price of stock 1 `curl -X PUT -d 'name=rubik' 'currentPrice=123' 'description=test2' http://localhost:8080/api/v1/products/1`
- Create a new stock `curl -H "Content-Type: application/json" -X POST -d '{"name":"S11","currentPrice":"11"}' http://localhost:8080/api/v1/products`
- Stock list front end: access to `http://localhost:8080`

curl -H "Content-Type: application/json" -X POST -d '{"name":"S12","currentPrice":"12"}' http://localhost:8080/api/v1/products

### Run Dockerfile - expose 8087 port
`docker-compose up`