#!/bin/sh

JAVA_OPTS="${JAVA_OPTS} -Dserver.port=${SERVER_PORT}"
JAVA_OPTS="${JAVA_OPTS} -Dspring.cloud.nacos.config.server-addr=${NACOS_CONFIG_SERVER}"
JAVA_OPTS="${JAVA_OPTS} -Dspring.cloud.nacos.discovery.server-addr=${NACOS_DISCOVERY_SERVER}"
JAVA_OPTS="${JAVA_OPTS} -Dspring.profiles.active=${PROFILES_ACTIVE}"

exec java ${JAVA_OPTS} org.springframework.boot.loader.JarLauncher $@