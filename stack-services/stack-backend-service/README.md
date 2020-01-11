stack backend service
docker build -t srilakshmi29/stack-backend-service:prod1 .
docker push srilakshmi29/stack-backend-service:prod1

#google
docker run -it -p 8082:8082 -v C:/Users/MSRILAKS/stackCred:/stackCred -e CRED_FILE=stackCredential -e CRED_DIR=/stackCred -e MONGO_HOST=mongo -e MONGO_PORT=27017 -e LOG_DIR=logs -e SPRING_PROFILES_ACTIVE=google --net my-net --name stack-backend-service  srilakshmi29/stack-backend-service

#develop
docker run -it -p 8082:8082 -v C:/Users/MSRILAKS/stackCred:/stackCred -e CRED_FILE=stackCredential -e CRED_DIR=/stackCred -e MONGO_HOST=mongo -e MONGO_PORT=27017 -e LOG_DIR=logs -e SPRING_PROFILES_ACTIVE=develop --net my-net --name stack-backend-service  srilakshmi29/stack-backend-service