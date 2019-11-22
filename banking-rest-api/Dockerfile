FROM openjdk:11 AS builder
WORKDIR /app

COPY gradlew /app
COPY gradle /app/gradle

COPY build.gradle settings.gradle /app/
RUN ./gradlew build || return 0

COPY . .
RUN ./gradlew build
RUN mv /app/banking-rest-api/build/libs/banking-rest-api-0.0.1-SNAPSHOT-boot.jar /app/banking-rest-api/build/libs/banking-rest-api.jar

FROM openjdk:11-jdk-slim as runner

WORKDIR /app

COPY --from=builder /app/banking-rest-api/build/libs/banking-rest-api.jar .
COPY --from=builder /app/banking-rest-api/build/resources/main/. .

EXPOSE 8080
RUN ["chmod", "+x", "banking-rest-api.jar"]
CMD ["java","-jar", "banking-rest-api.jar"]