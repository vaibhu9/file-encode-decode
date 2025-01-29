# Build stage
FROM gradle:8.5-jdk21 AS build
WORKDIR /app
COPY . .
RUN gradle build --no-daemon -x test

# Create custom JRE
FROM mcr.microsoft.com/openjdk/jdk:21-ubuntu AS jre-builder
WORKDIR /jre
RUN jlink --output /opt/custom-jre \
      --add-modules java.base,java.logging,java.naming,java.xml,java.net.http,java.security.jgss,java.instrument,java.management,jdk.crypto.ec,java.desktop \
      --strip-debug \
      --no-man-pages \
      --no-header-files \
      --compress=2

# Run stage
FROM ubuntu:22.04
WORKDIR /app
COPY --from=jre-builder /opt/custom-jre /opt/custom-jre
ENV JAVA_HOME=/opt/custom-jre
ENV PATH="$JAVA_HOME/bin:${PATH}"
COPY --from=build /app/build/libs/*.jar app.jar
EXPOSE 9090
ENTRYPOINT ["java", "-jar", "app.jar"]