#!/bin/bash

(
cd microservice-a-v1
mvn clean package
docker build -t xebiafrance/xke-helm-microservice-a:v1 .
docker push xebiafrance/xke-helm-microservice-a:v1
)

(
cd microservice-a-v2
mvn clean package
docker build -t xebiafrance/xke-helm-microservice-a:v2 .
docker push xebiafrance/xke-helm-microservice-a:v2
)

(
cd microservice-b-v1
mvn clean package
docker build -t xebiafrance/xke-helm-microservice-b:v1 .
docker push xebiafrance/xke-helm-microservice-b:v1
)
