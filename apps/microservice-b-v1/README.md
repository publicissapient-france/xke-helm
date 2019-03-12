# MicroService A

    $ mvn clean package
    $ docker build -t xebiafrance/xke-helm-microservice-b:v1 .
    $ docker login
    $ docker push xebiafrance/xke-helm-microservice-b:v1
