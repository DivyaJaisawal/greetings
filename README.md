## To create a grpc service and a client for that which uses postgres as db


### DB set up
docker-compose ps
docker-compose up -d
docker exec -it <container_id> bash
psql -Upostgres
\c testgrpcdev

### Build
./gradlew build - to generate the .java file from the proto

### Start Server
./gradlew startServer - to start the server

### Start Client
./gradlew startClient - to start the client

### Start kafka consumer 
./gradlew startConsumer  - to start the consumer 




