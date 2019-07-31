stack task service

docker system prune -a --volumes

docker build -t srilakshmi29/stack-task-service .

docker push srilakshmi29/stack-task-service

docker pull mongo:latest

docker network create my-net

docker run -p 27017:27017 -e MONGO_INITDB_ROOT_USERNAME=root -e MONGO_INITDB_ROOT_PASSWORD=example  --net my-net --name mongo -d mongo

docker run -p 8081:8081 -e ME_CONFIG_MONGODB_ADMINUSERNAME=root -e ME_CONFIG_MONGODB_ADMINPASSWORD=example  --net my-net --name mongo-express -d mongo-express

docker run -p 8080:8080 -e MONGO_HOST=mongo -e MONGO_PORT=27017 -e LOG_DIR=logs -e SPRING_PROFILES_ACTIVE=google --net my-net --name stack-task-service -d srilakshmi29/stack-task-service