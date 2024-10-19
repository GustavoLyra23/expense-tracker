
FROM openjdk:21


WORKDIR /app

COPY target/expense-tracker-1.0-SNAPSHOT.jar /app/expense-tracker.jar


ENTRYPOINT ["java", "-jar", "expense-tracker.jar"]
