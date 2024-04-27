# Stage 1: Build the application (uses Gradle)
FROM gradle:8-jdk17 AS builder
COPY --from= . . /home/gradle/src
WORKDIR /home/gradle/src
RUN gradle build

# Stage 2: Runtime image (no Gradle)
FROM eclipse-temurin:17-jdk-jammy
EXPOSE 80

# Copy application jar from build stage
COPY --from=builder /home/gradle/src/build/libs/carehub-0.0.1-SNAPSHOT.jar app.jar

# Set working directory and entrypoint
ENTRYPOINT ["java", "-jar", "app.jar"]