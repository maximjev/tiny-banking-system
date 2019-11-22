FROM openjdk:11 AS builder
WORKDIR /app

COPY gradlew /app
COPY gradle /app/gradle

COPY build.gradle settings.gradle /app/
RUN ./gradlew build || return 0

COPY . .
RUN ./gradlew bootJar
RUN mv /app/banking-domain/build/libs/banking-domain-0.0.1-SNAPSHOT-boot.jar /app/banking-domain/build/libs/banking-domain.jar

FROM openjdk:11-jdk-slim as runner

WORKDIR /app

COPY --from=builder /app/banking-domain/build/libs/banking-domain.jar .
COPY --from=builder /app/banking-domain/build/resources/main/. .

EXPOSE 8081
RUN ["chmod", "+x", "banking-domain.jar"]
CMD ["java","-jar", "banking-domain.jar"]