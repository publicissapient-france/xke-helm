# Exercice 3 - Créer un chart parent

Dans cet exercice nous allons créer le chart du `Microservice B`. 
Ce microservice communique avec le `Microservice A` via HTTP.
Pour cela il faudra créer un chart parent qui a contiendra les charts du `Microservice A`, du `Microservice B` et finalement le chart de `mongodb`.

TODO: Photo

## 1. Créer le chart pour `Microservice B`

### Détails :
* `Microservice B` communique avec le `Microservice A` via HTTP
* Le healthcheck du `Microservice B` vérifie qu'il a accès à `mongodb` et au `Microservice A`
* Le `Microservice B` a besoin des variables d'environnement suivantes :


    MONGODB_HOST - même valeur que pour le Microservice A
    MONGODB_PORT - même valeur que pour le Microservice A
    SERVICE_A_URL - pour le moment laisser à localhost:9081

### Instructions

* En se basant sur le chart du `Microservice A` construire le nouveau chart pour le `Microservice B`
* Laisser la variable SERVICE_A_URL à `localhost:9081` pour le moment
* Déployer la release
* Vérifier que le healthcheck échoue sytématiquement

### Détails
Helm se repose sur des `repositories` pour la distribution des charts.
Pour servir des charts un serveur HTTP doit être capable de :
    * Servir des fichiers YAML et des archives tar
    * Accepter les requêtes GET
Pour le dévelopement en local Helm dispose d'un serveur interne (helm serve). 

### Instructions

* Lancer le serveur de repository local (dans un terminal distinct) : `$ helm serve`
* Packager les charts de `Microservice A` et `Microservice B` avec `$ helm package`

<details><summary>Solution</summary>
<p>

    $ cd <chart directory>
    $ helm package .

</p>
</details>

* Vérifier que les `tgz` des charts sont présents dans `~/.helm/repository/local` (le répértoire utilisé par le `helm serve`)


## 3. Créer le chart parent
 
### Détails
Ce chart parent représentera notre application dans son ensemble. 
La méthode conseillée pour packager une application complexe consiste à créer un chart parent à partir des autres charts utilisés. 
Ce chart parent exposera les configurations sous forme de variables globales.
On placera ensuite les subcharts dans le répértoire `charts/`.

Notre chart parent aura comme dépendances :

* `Microservice A`
* `Microservice B`


### Instructions

* Créer un nouveau chart avec `$ helm create xke-helm-parent`
* Nous n'avons pas besoin de repertoire `/templates`
* Définir toutes les dépendences 

<details><summary>Solution</summary>
<p>

Créer `requirements.yaml` avec :

    dependencies:
      - name: microservice-a
        version: 0.1.0
        repository: http://127.0.0.1:8879/charts
      - name: microservice-b
        version: 0.1.0
        repository: http://127.0.0.1:8879/charts

</p>
</details>

* A cette étape l'application ne marchera toujours pas
* Il faut modifier la variable d'environment `SERVICE_A_URL` qu'on a laissé à `localhost:9081` lors de l'étape précédente

<details><summary>Solution</summary>
<p>

File `xke-helm-microservice-b/templates/deployment.yaml` :

    env:
    
       ...
    
       - name: SERVICE_A_URL
         value: `"{{- printf "http://%s-%s:9081" .Release.Name "xke-helm-microservice-a" | trunc 63 | trimSuffix "" -}}"`
         
         http://donkey-car-xke-helm-microservice-a:9081
         
       ...

</p>
</details>

* N'oublier pas de le repackager le `xke-helm-microservice-b` (`$ helm package .`) et mettre à jour les dépendances au niveau de chart parent (`$ helm dep update .`)  
* Installer / Upgrader le release `xke-helm-parent`
* Valider le fonctionnement (sur kubernetes dashboard par exemple)
* Optional :
    * Redimensionner les Microservices A et B pour en avoir 3 instances de chaqu'une
    * Ne toucher que values de chat parent


[< Previous](ex2-create-charts.md) | [Home](README.md) | [Next >](ex4-template-helpers.md)