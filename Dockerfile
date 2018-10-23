FROM java:8
FROM maven:alpine

WORKDIR /app

COPY . /app

RUN mvn -v
RUN mvn clean install -DskipTests

EXPOSE 8080
LABEL maintainer=“jemsotry@gmail.com”
ADD ./target/restfulapi-jpa-springboot-0.1.0.jar restfulapi-jpa-springboot-0.1.0.jar
ENTRYPOINT ["java","-jar","restfulapi-jpa-springboot-0.1.0.jar"]

# docker build -f Dockerfile -t spring-jpa-app .
# docker-compose up