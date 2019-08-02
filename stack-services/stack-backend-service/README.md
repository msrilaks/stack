stack backend service

docker run -it -p 8082:8082 -e CRED_FILE=stackCredential -e CRED_DIR=C:/Users/MSRILAKS/stackCred -e MONGO_HOST=localhost -e MONGO_PORT=27017 -e LOG_DIR=logs -e SPRING_PROFILES_ACTIVE=develop --name stack-backend-service srilakshmi29/stack-backend-service

docker run -it -p 8082:8082 -e CRED_FILE=stackCredential -e
CRED_DIR=C:/Users/MSRILAKS/stackCred -e MONGO_HOST=mongo -e MONGO_PORT=27017 -e
LOG_DIR=logs -e SPRING_PROFILES_ACTIVE=google --net my-net --name stack-backend-service  srilakshmi29/stack-backend-service
