FROM maven:3.8.3-openjdk-17-slim AS maven
WORKDIR /usr/src/app
COPY . /usr/src/app
RUN mvn package

FROM openjdk:17-jdk-slim
ARG JAR_FILE=ua.nlp-0.0.1-SNAPSHOT.jar
WORKDIR /opt/app
COPY --from=maven /usr/src/app/target/${JAR_FILE} /opt/app/app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]