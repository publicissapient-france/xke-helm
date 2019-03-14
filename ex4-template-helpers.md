# Exercice 4 - (Optionel) Utilisation de “PARTIALS” dans les templates

Vous avez certainement rémarqué l'utilisation de fonction `include` dans les differents templates. 
Le but de cet exercice est de créer une code reutilisable permettant de construire une variable `SERVICE_A_URL` dans le `Microservice B`.

## 1. (Optionel) Service Port

### Détails :

* Dans les charts `deployment.yaml` des charts `xke-helm-microservice-a` et `xke-helm-microservice-b`   

### Instructions :
* La variable helm `service.port` est déjà défini dans le `xke-helm-microservice-a/values.yaml` et `xke-helm-microservice-b/values.yaml`
* Utiliser cette variable dans `xke-helm-microservice-a/templates/deployment.yaml` pour remplacer le port en dur `spec.containers.containerPort`

<details><summary>Solution</summary>
<p>

File `xke-helm-microservice-a/templates/deployment.yaml` et `xke-helm-microservice-b/templates/deployment.yaml`

```yaml
    ...
    
    spec:
      containers:
          ports:
            - name: http
              containerPort: {{ .Values.service.port }}
    
    ...
```

</p>
</details>


## 2. Partial

* Créer un partial (par ex `xke-helm-microservice-a.service.url`) dans `xke-helm-microservice-b/templates/_helpers.tpl` permettant de construire le url vers `Microservice A`.  

<details><summary>Solution</summary>
<p>

File `xke-helm-microservice-b/templates/_helpers.tpl`

    ...
    
    {{/*
      Defines the url of "Microservice A"
    */}}
    {{- define "xke-helm-microservice-a.service.url" -}}
        {{- $host := printf "%s-%s" .Release.Name "xke-helm-microservice-a" -}}
        {{- $port := default "9081" .Values.microservice.a.port -}}
        {{- printf "http://%s:%s" $host $port | trunc 63 | trimSuffix "-" -}}
    {{- end -}}

    ...

</p>
</details>

* Utiliser cet partial dans `xke-helm-microservice-b/templates/deployment.yaml` à l'aide de fonction `include`
 
* Packager le chart `xke-helm-microservice-b` et redeployer avec `$ helm upgrade`

<details><summary>Solution</summary>
<p>

    $ helm package xke-helm-microservice-b
    $ helm dep up xke-helm-parent
    $ helm upgrade <relase name> xke-helm-parent

</p>
</details>




[< Previous](ex3-parent-chart.md) | [Home](README.md) | [Next >](ex5-mongodb-cluster.md)
