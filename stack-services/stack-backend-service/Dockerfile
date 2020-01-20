FROM openjdk:8
VOLUME /tmp
COPY build/libs/stack-backend-service*.jar stack-backend-service.jar
ENTRYPOINT ["java","-jar","/stack-backend-service.jar"]