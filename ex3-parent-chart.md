# Exercice 3 - Créer un chart parent

Dans cet exercice nous allons créer le chart du `Microservice B`. 
Ce microservice communique avec le `Microservice A` via HTTP.
Pour cela il faudra créer un chart parent qui aura les charts du `Microservice A` et du `Microservice B` comme dépendances.

TODO: Photo

## 1. Créer le chart pour `Microservice B`

### Détails :
* `Microservice B` communique avec le `Microservice A` via HTTP
* Le healthcheck du `Microservice B` vérifie qu'il a accès à `mongodb` et au `Microservice A`
* Le `Microservice B` a besoin des variables d'environnement suivantes :
* Le `Microservice B` expose le port `9082

```
MONGODB_HOST : même valeur que pour le Microservice A
MONGODB_PORT : même valeur que pour le Microservice A
SERVICE_A_URL : pour le moment laisser à localhost:9081
```

### Instructions

* En se basant sur le chart du `Microservice A` construire le nouveau chart pour le `Microservice B`
* N'oublier pas renseigner le service `port` à `9082`
* Mettre la variable `SERVICE_A_URL` à `localhost:9081` pour le moment
* Déployer la release
* Vérifier que le healthcheck `échoue sytématiquement
* Supprimer la release

## 2. Distribuer les charts

### Détails
Helm se repose sur des `repositories` pour la distribution des charts.
Pour héberger des charts un serveur HTTP doit être capable de :
* Servir des fichiers YAML et des archives tar
* Accepter les requêtes GET

Pour le dévelopement en local Helm dispose d'un serveur interne (helm serve). 

### Instructions

* Lancer le serveur de repository local (dans un terminal distinct) : `$ helm serve`
* Packager les charts de `Microservice A` et `Microservice B` avec `$ helm package`

<details><summary>Solution</summary>
<p>

```sh
$ cd <chart directory>
$ helm package .
```

</p>
</details>

* Vérifier que les fichiers `*.tgz` des charts sont présents dans `~/.helm/repository/local` (le répértoire utilisé par le `helm serve`)


## 3. Créer le chart parent
 
### Détails
Ce chart parent représentera notre application dans son ensemble. 
La méthode conseillée pour packager une application complexe consiste à créer un chart parent à partir des autres charts utilisés. 
Ce chart parent exposera les configurations sous forme de variables globales.
On placera ensuite les subcharts dans le répértoire `charts/`.

Notre chart parent aura comme dépendances :

* `Microservice A`
* `Microservice B`

Ces dépendances déclencheront la création de deux pods qui auront pour nom: "`<release-name>-<chart name>`".
Etant donné que les 2 charts de microservices utilisent `mongodb` cela posera un problème de collision de nom entre les resources Kubernetes.

Pour s'en sortir il suffira de nommer respectivement les bases `mongodb-a` et `mongodb-b` dans les charts des `Microservice A et B`.
Cette surcharge se fera via la variable `nameOverride` :    

```yaml
mongodb:
  nameOverride: mongodb-a
  usePassword: false
``` 

### Instructions

* Ajouter `mongodb.nameOverride: mongodb-a` et `mongodb.nameOverride: mongodb-b` dans les charts des `Microservice A et B`
* Créer un nouveau chart parent avec `$ helm create xke-helm-parent`
* Supprimer le répertoire `/templates` qui est inutile pour cet exercice
* Définir toutes les dépendances vers `Microservice A` et `Microservice B`

<details><summary>Solution</summary>
<p>

Créer un fichier `requirements.yaml` contenant:

```yaml
    dependencies:
      - name: xke-helm-microservice-a
        version: 0.1.0
        repository: http://127.0.0.1:8879/charts
      - name: xke-helm-microservice-b
        version: 0.1.0
        repository: http://127.0.0.1:8879/charts
```

</p>
</details>

* A cette étape l'application ne marchera toujours pas
* Il faut modifier la variable d'environment `SERVICE_A_URL` qu'on a laissé à `localhost:9081` lors de l'étape précédente

<details><summary>Solution</summary>
<p>

File `xke-helm-microservice-b/templates/deployment.yaml` :

```yaml
    env:
    
       ...
    
       - name: SERVICE_A_URL
         value: "{{- printf "http://%s-%s:9081" .Release.Name "xke-helm-microservice-a" | trunc 63 | trimSuffix "" -}}"
         
       ...
```

</p>
</details>

* N'oubliez pas de le packager le `xke-helm-microservice-b` (`$ helm package .`) 
* Mettez à jour les dépendances au niveau de chart parent (`$ helm dep update .`)  
* Installez / Upgradez la release `xke-helm-parent`
* Validez le fonctionnement global (via le Kubernetes dashboard par exemple)
* Optional :
    * Redimensionner les `Microservices A et B` pour disposer de 3 instances de chaque (`replicaCount: 3`)
    * Modifiez uniquement le `values.yaml` du chart parent

[< Previous](ex2-create-charts.md) | [Home](README.md) | [Next >](ex4-template-helpers.md)