FROM openjdk:17-alpine

EXPOSE 8085

WORKDIR /app

COPY build/libs/app.jar .

ENTRYPOINT ["java", "-jar", "app.jar"]