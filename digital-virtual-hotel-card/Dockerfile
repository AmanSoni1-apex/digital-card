# Use Java 21 official image
FROM eclipse-temurin:21-jdk-alpine

# Working directory inside container
WORKDIR /app

# Copy Maven wrapper and pom.xml first
COPY mvnw pom.xml ./
COPY .mvn .mvn

# Copy source code
COPY src ./src

# Make Maven wrapper executable
RUN chmod +x mvnw

# Build the Spring Boot jar (skip tests)
RUN ./mvnw clean package -DskipTests

# Expose the port
EXPOSE 8080

# Run the jar
ENTRYPOINT ["java","-jar","target/digital-virtual-card-0.0.1-SNAPSHOT.jar"]
