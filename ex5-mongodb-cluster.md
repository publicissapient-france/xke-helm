# Exercice 4 - (Advanced) Mongodb cluster 

Actuellement, chaque microservice a son propre instance de mongodb. Ces bases tournent actuellement en mono-instance, 
ce qui évidemment n'est pas adapté à l'environnement de production.     
Il n'est également pas opportun non plus d'avoir un clusteur mongodb par microservice.


Le but de cet exercice est d'installer le clusteur `mongodb` à l'aide de son propre chart pérsonnalisé. Ensuite, desactiver dinamiquement   

TODO: Photo

## 1. Créer le chart pour `Microservice B`

### Détails :
* `Microservice B` "parle" à `Microservice A` par http
* Le check-health de `Microservice B` vérifie qu'il a accès à `mongodb` et à `Microservice A`
* Le `Microservice B` a besoin des variables d'environnement suivantes :


    MONGODB_HOST - même que pour Microservice A
    MONGODB_PORT - même que pour Microservice A
    SERVICE_A_URL - pour le moment laisser à localhost:9081

### Instructions

* En se basant sur le chart du `Microservice A` construire le nouveau chart pour le `Microservice B`
* Laisser SERVICE_A_URL `localhost:9081` pour le moment
* Deployer la release
* Verifier que le health-check échoue sytématiquement

## 2. "Distribuer" les charts

### Détails
Helm se repose sur les chart répositories pour la distribution des charts.
Tout serveur HTTP pouvant servir les fichiers YAML et les fichiers tar et pouvant répondre aux demandes GET peut être utilisé comme serveur de référentiel.
Helm comes with built-in package server for developer testing (helm serve). 

### Instructions

* Lancer le serveur de répository locale (dans terminal distinct) : `$ helm serve`
* Packager les charts de `Microservice A` et `Microservice B` avec `$ helm package`

<details><summary>Solution</summary>
<p>

    $ cd <chart directory>
    $ helm package .

</p>
</details>

* Verifier que les `tgz` des charts sont présents dans `~/.helm/repository/local` (le répértoire utilisé par le `helm serve`)


## 3. Créer le chart parent
 
### Détails
Ce chart parent réprésentera notre application dans son ensemble. 
La meilleure pratique actuelle pour composer une application complexe à partir des autres charts consiste à créer un chart parent 
qui expose les configurations globales (les valeurs globales) et ensuite utiliser le répértoire `charts/` pour y ajouter des composants.

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

Dans `microservice-b` -> `deployment.yaml` :

    env:
    
       ...
    
       - name: SERVICE_A_URL
         value: `"{{- printf "http://%s-%s:9081" .Release.Name "microservice-a" | trunc 63 | trimSuffix "" -}}"`
         
         http://donkey-car-microservice-a:9081
         
       ...

</p>
</details>

* N'oublier pas de le repackager le `microservice-b` (`$ helm package .`) et mettre à jour les dépendances au niveau de chart parent (`$ helm dep update .`)  
* Installer / Upgrader le release `xke-helm-parent`
* Valider le fonctionnement (sur kubernetes dashboard par exemple)



*Bravo ! Vous avez arrivé à la fin d'exercice ! Felicitations !* 

[< Previous](ex4-template-helpers.md) | [Home](README.md)