FROM maven:3.9.1-ibm-semeru-17-focal AS build
COPY src /home/app/src
COPY pom.xml /home/app
RUN mvn -f /home/app/pom.xml clean package dependency:copy-dependencies
RUN ls -la /home/app/target

FROM ibm-semeru-runtimes:open-17.0.6_10-jdk-jammy
#FROM ibm-semeru-runtimes:open-17.0.4.1_1-jdk-jammy
COPY --from=build /home/app/target/openj9-bug-*-SNAPSHOT.jar /app/app.jar
COPY --from=build /home/app/target/libs/* /app/libs/

COPY docker/ /app/scripts/
COPY src-jvmti-agent/ /tmp/jvmti-agent-build
RUN apt-get update && apt-get install -y gcc gdb build-essential

RUN export CPLUS_INCLUDE_PATH=/opt/java/openjdk/include/:/opt/java/openjdk/include/linux/ \
    && cd /tmp/jvmti-agent-build \
    && g++ -shared -o agent.o agent.cpp -fPIC && cp agent.o /app/agent.o \
    && chgrp -R 0 /app && chmod -R g+rwX /app

ENTRYPOINT [ "sh", "/app/scripts/entrypoint.sh" ]