FROM openjdk:8-jdk-alpine
VOLUME /tmp
COPY build/libs/stack-task-service*.jar stack-task-service.jar
ENTRYPOINT ["java","-jar","/stack-task-service.jar"]