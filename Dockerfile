# syntax=docker/dockerfile:experimental
FROM openjdk:11.0-jdk as build

WORKDIR /workspace/app

COPY mvnw mvnw
COPY .mvn .mvn
COPY pom.xml pom.xml
COPY src src

RUN --mount=type=cache,target=/root/.m2 ./mvnw package -DskipTests
RUN java -Djarmode=layertools -jar target/spring-one-0.0.1-SNAPSHOT.jar extract --destination target/extracted

FROM openjdk:11.0-jre
VOLUME /tmp

ENV EXTRACTED=/workspace/app/target/extracted
ENV SERVER_PORT=8080

WORKDIR /workspace/app

RUN addgroup --system --gid 500 apps && adduser --system --uid 500 --gid 500 apps && \
    mkdir logs && chown apps:apps logs

USER apps

COPY --from=build ${EXTRACTED}/dependencies/ ./
COPY --from=build ${EXTRACTED}/spring-boot-loader/ ./
COPY --from=build ${EXTRACTED}/snapshot-dependencies/ ./
COPY --from=build ${EXTRACTED}/application/ ./

COPY bin/entrypoint.sh bin/entrypoint.sh

EXPOSE $SERVER_PORT
ENTRYPOINT ["/workspace/app/bin/entrypoint.sh"]