docker stop stack-task-service
docker rm stack-task-service
docker network create my-net
call gradlew clean build
docker build -t srilakshmi29/stack-task-service:latest .
docker push srilakshmi29/stack-task-service:latest
docker run -p 8080:8080 -e LOG_DIR=logs -e SPRING_PROFILES_ACTIVE=develop --net my-net --name stack-task-service -d srilakshmi29/stack-task-service:latest
