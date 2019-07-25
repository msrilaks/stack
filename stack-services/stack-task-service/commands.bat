docker-compose down
docker system prune -a --volumes -f
call gradlew clean build
docker build -t srilakshmi29/stack-task-service .
docker push srilakshmi29/stack-task-service
docker-compose up