# Exercice 4 - (Optionel) Utilisation de “PARTIALS” dans les templates

Vous avez certainement remarqué l'utilisation de la fonction `include` dans les différents templates.
Le but de cet exercice sera de créer une code réutilisable permettant de construire une variable `SERVICE_A_URL` dans le `Microservice B`.

## 1. (Optionel) Service Port

### Détails :

* Dans les fichiers `deployment.yaml` des charts `xke-helm-microservice-a` et `xke-helm-microservice-b`   

### Instructions :
* La variable helm `service.port` est déjà définie à deux endroits
    * `xke-helm-microservice-a/values.yaml` 
    * `xke-helm-microservice-b/values.yaml`
* Utiliser cette variable dans `xke-helm-microservice-a/templates/deployment.yaml` pour remplacer la valeur en dur de `spec.containers.containerPort`

<details><summary>Solution</summary>
<p>

Fichiers `xke-helm-microservice-a/templates/deployment.yaml` et `xke-helm-microservice-b/templates/deployment.yaml`

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

Fichier `xke-helm-microservice-b/templates/_helpers.tpl`

```yaml
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
```

Fichier `xke-helm-microservice-b/values.tpl`
```yaml
...
microservice:
  a:
    port: 9081
...
```

</p>
</details>

* Utiliser ce partial dans `xke-helm-microservice-b/templates/deployment.yaml` à l'aide de la fonction `include`

<details><summary>Solution</summary>
<p>

Fichier `xke-helm-microservice-b/templates/deployment.yaml`

```yaml
    value: "{{ include "xke-helm-microservice-a.service.url" . }}"
```
</p>
</details>
 
* Packager le chart `xke-helm-microservice-b` et redéployer avec `$ helm upgrade`

<details><summary>Solution</summary>
<p>

```sh
$ helm package xke-helm-microservice-b
$ helm dep up xke-helm-parent
$ helm upgrade <relase name> xke-helm-parent
```

</p>
</details>

[< Previous](ex3-parent-chart.md) | [Home](README.md) | [Next >](ex5-mongodb-cluster.md)
