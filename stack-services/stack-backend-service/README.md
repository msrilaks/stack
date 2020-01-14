stack backend service
docker build -t srilakshmi29/stack-backend-service:prod1 .
docker push srilakshmi29/stack-backend-service:prod1
kubectl set image deployment stack-backend-service-deployment stack-backend-service=srilakshmi29/stack-backend-service:prod1

https://accounts.google.com/o/oauth2/auth?access_type=offline&approval_prompt=auto&client_id=13854590117-intkscnrh88jlq2k4vob3k9nl0620s05.apps.googleusercontent.com&redirect_uri=urn:ietf:wg:oauth:2.0:oob&response_type=code&scope=https://www.googleapis.com/auth/gmail.modify%20https://www.googleapis.com/auth/gmail.readonly

#google
docker run -it -p 8082:8082 -v C:/Users/MSRILAKS/stackCred:/stackCred -e CRED_FILE=stackCredential -e CRED_DIR=/stackCred -e MONGO_HOST=mongo -e MONGO_PORT=27017 -e LOG_DIR=logs -e SPRING_PROFILES_ACTIVE=google --net my-net --name stack-backend-service  srilakshmi29/stack-backend-service

#develop
docker run -it -p 8082:8082 -v C:/Users/MSRILAKS/stackCred:/stackCred -e CRED_FILE=stackCredential -e CRED_DIR=/stackCred -e MONGO_HOST=mongo -e MONGO_PORT=27017 -e LOG_DIR=logs -e SPRING_PROFILES_ACTIVE=develop --net my-net --name stack-backend-service  srilakshmi29/stack-backend-service