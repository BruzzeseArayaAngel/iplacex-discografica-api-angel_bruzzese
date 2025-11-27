FROM gradle:8.14.3-jdk21 AS builder
WORKDIR /app

COPY . .

RUN gradle clean build -x test

FROM eclipse-temurin:21-jdk
WORKDIR /app

COPY --from=builder /app/build/libs/discografia-1.jar /app/app.jar

EXPOSE 8080

CMD ["java", "-jar", "/app/app.jar"]