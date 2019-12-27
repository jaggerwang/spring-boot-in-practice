FROM maven:3-jdk-11 AS build

WORKDIR /app

COPY sources.list /etc/apt/
COPY settings.xml /root/.m2/
COPY pom.xml .
RUN mvn dependency:go-offline

COPY . .
RUN mvn package

FROM openjdk:11-jre
COPY --from=build /app/target/spring-boot-in-practice-1.0.0-SNAPSHOT.jar /app.jar

VOLUME [ "/data" ]
EXPOSE 8080
RUN mkdir -p /data/log /data/tmp /data/upload
CMD ["java", "-Djava.security.egd=file:/dev/./urandom", "-jar", "/app.jar"]
