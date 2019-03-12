# MicroService A

    $ ./mvnw clean package
    $ docker build -t xebiafrance/xke-helm-microservice-a .
    $ docker login
    $ docker push xebiafrance/xke-helm-microservice-a
