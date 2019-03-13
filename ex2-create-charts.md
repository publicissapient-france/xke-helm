# Exercice 2 - Créer son propre chart

Dans cet exercice on va créer et deployer le chart pour notre première microservice (`Microservice A`). 
La version `v1` de `Microservice A` ne depend d'aucune autre service. La version `v2` quand à elle, depend de la base (chart) `mongodb`.

TODO: Photo

## 1. Initialiser un chart

### Détails
Helm permet d'initialiser le nouveau chart (créer un répértoire avec l'arborescence des fichiers) à partir de template standard

### Instructions
* Initialisez un nouveau chart nommé `microservice-a` with `helm create`
<details><summary>Solution</summary>
<p>

    $ helm create microservice-a

</p>
</details>


## 2. Microservice A (v1)

### Details :
* Microservice A (v1) n'a aucune dependance
* L'image est disponible `xebiafrance/xke-helm-microservice-a:v1`
* L'application expose le port `9081`

Instructions:
* Le template généré utilise une image Docker de nginx, expose le port `80` et déclare un endpoint de health-check `/`
    * Voir `deployment.yaml` section `containers`
* Modifier `values.yaml` 
    * section `image:` - afin de deployer le `Microservice A` (v1). 
    * section `service:` (`type: NodePort` et `port: 9081`)

> _Note: Ne toucher qu'au `values.yaml`_
    
* Modifier le `deployment.yaml` pour inclure le health-check du chart
    * Compléter les sections `livenessProbe` et `readinessProbe`
        * Modifier le path : `/actuator/health`
        * Ajouter un `initialDelaySeconds` (30s)
        * Ajouter un `timeoutSeconds` (10s)
* Déployer le chart avec `helm install`

<details><summary>Solution</summary>
<p>

    $ cd <chart directory>
    $ helm install .

</p>
</details>


* Vérifier dans le dashboard l'état du pod
* (Optionnel) Exposer le endpoint de monitoring en local

<details><summary>Solution</summary>
<p>

    $ kubectl get services
    $ kubectl port-forward svc/<service name>-microservice-a 9081:9081
    $ curl http://localhost:9081

</p>
</details>

    
## 3. Microservice A (v2)

Détails :
* La v2 ne `Microservice A` nécessite mongodb
* Le host et port de mongodb sont injectés par les variables d'environnements :
```
    MONGODB_HOST
    MONGODB_PORT
```

Instructions :
* Ajouter une dépendance au chart de mongodb (`stable/mongodb:5.9.0`)

<details><summary>Solution</summary>
<p>

Créer un fichier `requirements.yaml` à la racine du chart

    dependencies:
      - name: mongodb
        version: 5.9.0
        repository: https://kubernetes-charts.storage.googleapis.com/

</p>
</details>

* Mettre à jour les dépendances du chart

<details><summary>Solution</summary>
<p>

    $ cd <chart directory>
    $ helm dep update .

</p>
</details>

* Désactiver le mot de passe de mongodb

<details><summary>Solution</summary>
<p>

Ajouter dans `values.yaml` :

    mongodb:
        usePassword: false

</p>
</details>

* Ajouter les variables d'environnement à la definition du conteneur (dans `deployment.yaml`, section `spec.containers`)
    * `MONGODB_HOST` - `"{{- printf "%s-%s" .Release.Name "mongodb" | trunc 63 | trimSuffix "" -}}"`
    * `MONGODB_PORT` - `"{{- .Values.mongodb.service.port -}}"`  

<details><summary>Solution</summary>
<p>

Ajouter dans `deployement.yaml` dans la section `spec.containers` :

    spec:
      containers:
        - name: {{ .Chart.Name }}

        ...

          env:
            - name: MONGODB_HOST
              value: "{{- printf "%s-%s" .Release.Name "mongodb" | trunc 63 | trimSuffix "" -}}"
            - name: MONGODB_PORT
              value: "{{- .Values.mongodb.service.port -}}"
              
        ...

</p>
</details>

* Upgrade release with `helm upgrade`

<details><summary>Solution</summary>
<p>

    $ cd <chart directory>
    $ helm upgrade <relase name> .

</p>
</details>

* Vérifier que la révision de votre release a été incrémentée 

    
    
[< Previous](ex1-using-charts.md) | [Home](README.md) | [Next >](ex3-parent-chart.md)