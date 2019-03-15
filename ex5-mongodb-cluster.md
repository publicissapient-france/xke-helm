# Exercice 4 - (Advanced) Mongodb cluster 

Actuellement chaque microservice est déployé avec sa propre instance de `mongodb`. 
Ces `mongodb` tournent en mono-instance ce qui n'est évidemment pas adapté dans un environnement de production.     
De la même manière, il n'est pas opportun que chaque microservice dispose de son propre cluster.

Le but de cet exercice sera donc d'installer un unique cluster partagé entre les différents microservices.
Nous commencerons par installer un cluster `mongodb` via son propre chart personnalisé puis nous ferons pointer les
microservices A et B sur le cluster fraîchement déployé.

Notez que, au sein du cluster, il sera très important d'isoler les bases de dédiées à chacun des microservices.   

<p>
<img src="img/target-architecture.png" height="300">
</p>

## 1. Clusteur mongodb

### Instructions

* Créer le nouveau chart `xke-helm-mongodb`
* Supprimer les templates
* Paramétrer le `values.yaml` pour passer le mongodb en mode replicaSet 

<details><summary>Solution</summary>
<p>

Fichier `xke-helm-mongodb/values.yaml`

```yaml
    ...
    
    replicaSet:
      enabled: true
    
    ...
```

</p>
</details>

* Installer le chart en spécifiant le name de release à créer : `xke-mongodb`

<details><summary>Solution</summary>
<p>

Fichier `xke-helm-mongodb/values.yaml`

```sh
$ cd <chart directory>
$ helm install . --name xke-mongodb
```

</p>
</details>

* Adapter les charts de `Microservice A` et `Microservice B` pour pointer `MONGODB_HOST` vers le nouveau clusteur
* Bonus: 
    * Définir une variable helm globale (par ex `mongodb.enabled = false`)
    * Utiliser `condition: mongodb.enabled` au niveau des `requirements.yaml` de 'Microservice A et B' pour éviter telecharger la dependance mongo 
    * Changer `MONGODB_HOST` dynamiquement

<details><summary>Solution</summary>
<p>

Fichier `xke-helm-microservice-a/templates/deployment.yaml` et `xke-helm-microservice-b/templates/deployment.yaml`

```yaml
    env:
    
      ...
    
      - name: MONGODB_HOST
      {{- if .Values.mongodb.enabled }}
        value: {{ template "mongodb.fullname" . }}
      {{- else }}
        value: {{ .Values.externalDatabase.host | quote }}
      {{- end }}
      
      ...
```
</p>
</details>

* Repackager er re-deployer

*Bravo ! Vous êtes arrivés à la fin de hands-on ! Felicitations !!!* 

[< Previous](ex4-template-helpers.md) | [Home](README.md)