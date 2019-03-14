# Exercice 4 - (Optionel) Utilisation de “PARTIALS” dans les templates

Vous avez certainement rémarqué l'utilisation de fonction `include` dans les differents templates. 
Le but de cet exercice est de créer une code reutilisable permettant de construire une variable `SERVICE_A_URL` dans le `Microservice B`.

## 1. (Optionel) Variabilisation de ports

### Détails :

* Il est possible de rédéfinir les ports des `Microservices A et B` en leurs passant une variable d'environnement `SERVICE_PORT`  

### Instructions :
* La variable helm `service.port` est déjà défini dans le `xke-helm-microservice-a/values.yaml`
* Utiliser cette variable dans `xke-helm-microservice-a/templates/deployment.yaml` pour passer à microservice une variable d'environnement `SERVICE_PORT`

<details><summary>Solution</summary>
<p>

File `xke-helm-microservice-a/templates/deployment.yaml`

```yaml
    env:
      ...
    
      - name: SERVICE_PORT
        value: "{{- .Values.service.port -}}"
        
    ...
```

</p>
</details>


* Ecraser la variable `service.port` dans le chart parent `xke-helm-parent` avec la même valeur (9081) _(Attention, il faut la "namespacer" avec le nom de chart A)_.

<details><summary>Solution</summary>
<p>

File `xke-helm-parent/values.yaml`

```yaml
    ...
    
    xke-helm-microservice-a:
      service:
        port: 9081
        
    ...
```

</p>
</details>
 

* Créer un partial (par ex `xke-helm-microservice-a.service.url`) dans `templates/_helpers.tpl` du chart du `Microservice B` 
* Ce partial construira l'url vers le `Microservice A`.  

<details><summary>Solution</summary>
<p>

File `xke-helm-microservice-b/_helpers.tpl`

```yaml
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
```

</p>
</details>
 
* Redeployer avec `$ helm upgrade`

<details><summary>Solution</summary>
<p>

```sh
    $ cd <chart directory>
    $ helm upgrade <relase name> .
```

</p>
</details>

[< Previous](ex3-parent-chart.md) | [Home](README.md) | [Next >](ex5-mongodb-cluster.md)