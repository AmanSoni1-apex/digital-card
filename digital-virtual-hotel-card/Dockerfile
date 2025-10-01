# Use Java 17 runtime
FROM openjdk:17-jdk-slim

# Set working directory
WORKDIR /app

# Copy the JAR built by Maven
COPY target/digital-virtual-hotel-card-0.0.1-SNAPSHOT.jar app.jar

# Expose port
EXPOSE 8080

# Run the app
ENTRYPOINT ["java", "-jar", "app.jar"]
