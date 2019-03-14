# Exercice 4 - (Optionel) Utilisation de “PARTIALS” dans les templates

Vous avez certainement rémarqué l'utilisation de fonction `include` dans les differents templates. 
Le but ici est de créer une "fonction" permettant de construire une variable `SERVICE_A_URL` dans le `Microservice B`.

## 1. Créer le chart pour `Microservice B`

### Détails :

* Il est possible de rédéfinir le ports des Microservices A et B en leurs passant une variable d'environnement `SERVICE_PORT`  

### Instructions :
* Définir une variable helm `service.port = 9081` dans le chart de `Microservice A`

<details><summary>Solution</summary>
<p>

File `xke-helm-microservice-a/values.yaml`

    ...
    
    service:
      port: 9081
        
    ...

</p>
</details>

* Utiliser cette variable dans `templates/deployment.yaml` de `Microservice A` pour lui passer une variable d'environement `SERVICE_PORT`

File `xke-helm-microservice-a/templates/deployment.yaml`

    env:
      ...
    
      - name: SERVICE_PORT
        value: "{{- .Values.service.port -}}"
        
    ...

</p>
</details>


* Ecraser la variable `service.port` dans le chart parent `xke-helm-parent` avec la même valeur (9081) _(Attention, il faut la "namespacer" avec le nom de chart A)_.

<details><summary>Solution</summary>
<p>

File `xke-helm-parent/values.yaml`

    ...
    
    xke-helm-microservice-a:
      service:
        port: 9081
        
    ...

</p>
</details>
 

* Créer un partial (par ex `xke-helm-microservice-a.service.url`) dans `templates/_helpers.tpl` de chart de `Microservice B` permettant de construire le url vers `Microservice A`.  

<details><summary>Solution</summary>
<p>

File `xke-helm-microservice-b/_helpers.tpl`

    ...
    
    {{/*
      Defines the url of "Microservice A"
    */}}
    {{- define "xke-helm-microservice-a.service.url" -}}
        {{- $scheme := default "http" .Values.xke-helm-microservice-a.service.scheme -}}
        {{- $host := printf "%s-%s" .Release.Name "xke-helm-microservice-a" -}}
        {{- $port := default "9081" .Values.xke-helm-microservice-a.service.port -}}
        {{- printf "%s://%s:%s" $scheme $host $port | trunc 63 | trimSuffix "-" -}}
    {{- end -}}

    ...

</p>
</details>
 
* Redeployer avec `$ helm upgrade`

<details><summary>Solution</summary>
<p>

    $ cd <chart directory>
    $ helm upgrade <relase name> .

</p>
</details>




[< Previous](ex3-parent-chart.md) | [Home](README.md) | [Next >](ex5-mongodb-cluster.md)
