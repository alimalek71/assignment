FROM maven:3.8.6-openjdk-11-slim AS builder

ARG SKIP_TEST=true

WORKDIR /build

COPY ./pom.xml .

COPY ./src ./src

RUN mvn -Dmaven.test.skip="$SKIP_TEST" package

FROM openjdk:11-jre-slim

WORKDIR /

COPY --from=builder /build/target/*.jar app.jar

COPY startup.sh startup.sh
RUN chmod +x startup.sh

ENV SPRING_OUTPUT_ANSI_ENABLED=ALWAYS \
    JAVA_OPTS=""

EXPOSE 8080

CMD './startup.sh'