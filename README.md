# Example REST Service
Sample service to test REST application. It exposes REST endpoints for testing.


### Build
```shell
# Build application 
./gradlew clean build 

# 2. Run spring boot application 
./gradlew bootRun
    
    (or)
java -jar build/libs/example-service-0.0.1-SNAPSHOT.jar
```

### Build docker image  
```shell
docker build -t kubescope/example-service -f src/docker/Dockerfile  .
```

## To Run 
```shell
docker run -i -p 8080:8080 kubescope/example-service
```

## REST Operations
```shell
#To get all user  
curl --location --request GET 'http://localhost:8080/api/v1/users'

#To get by id   
curl --location --request GET 'http://localhost:8080/api/v1/users/12'

#To add new user   
curl --location --request POST 'http://localhost:8080/api/v1/users' \
--header 'Content-Type: application/json' \
--data-raw '{
"id": 13,
"name": "Test User",
"active": true
}'

#To modify   
curl --location --request PUT 'http://localhost:8080/api/v1/users' \
--header 'Content-Type: application/json' \
--data-raw '{
"id": 13,
"name": "Test User Modify",
"active": false
}'


#To delete  
curl --location --request DELETE 'http://localhost:8080/api/v1/users/13' \
--header 'Content-Type: application/json'

```

## Kubernetes Deployment
# deploys service and deployment 
kubectl apply -f kube/example-srv-deployment.yml