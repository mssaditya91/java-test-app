FROM openjdk:17-jdk-slim

# Install Maven
RUN apt-get update && apt-get install -y maven

# Set working directory
WORKDIR /app

# Copy Maven settings with GitHub token
COPY settings.xml /root/.m2/settings.xml

# (Optional) Verify settings.xml in image during build
RUN cat /root/.m2/settings.xml

# Download the JAR from GitHub Packages using proper server ID
RUN mvn -s /root/.m2/settings.xml dependency:get \
    -Dartifact=com.example:java-test-app:1.0.0:jar \
    -DremoteRepositories=github::default::https://maven.pkg.github.com/mssaditya91/java-test-app \
    -Dtransitive=false

# Copy the downloaded JAR into the container
RUN cp /root/.m2/repository/com/example/java-test-app/1.0.0/java-test-app-1.0.0.jar app.jar

# Expose app port and run
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
