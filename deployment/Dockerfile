FROM gradle:9.1.0-jdk25-alpine AS build
WORKDIR /app
COPY . .
RUN gradle clean build -x test -x validateStructure

FROM eclipse-temurin:25-jdk-alpine
WORKDIR /app
COPY --from=build /app/applications/app-service/build/libs/*.jar app.jar

ENV JAVA_OPTS="-XX:+UseContainerSupport -XX:MaxRAMPercentage=70 -Djava.security.egd=file:/dev/./urandom"
EXPOSE 10000

ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -Dserver.port=${PORT:-10000} -jar app.jar"]