FROM eclipse-temurin:17-jdk-alpine

WORKDIR /app

# Make mvnw executable
COPY mvnw .
RUN chmod +x mvnw

COPY .mvn .mvn
COPY pom.xml .

# Download dependencies
RUN ./mvnw dependency:go-offline

# Copy source code and build
COPY src src
RUN ./mvnw package -DskipTests

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "target/*.jar"]