# Exercice 2 - Créer son propre chart

## 1. Initialiser un chart

Initialisez un nouveau chart nommé `microservice-a`
<details><summary>Solution</summary>
<p>

    $ helm create microservice-a

</p>
</details>


## 2. Microservice A v1

* Ouvrir le fichier `deployment.yaml` et chercher la section `containers`
    * Par défaut le chart généré:
        * Utilise une image Docker de nginx
        * Expose le port `80`
        * Déclare un endpoint de monitoring à la racine
* Modifier la configuration afin de pointer sur l'image Docker du micro-service A
    * Les valeurs à utiliser sont déduites du `values.yaml`
    * L'image est disponible sur `xebiafrance/xke-helm-microservice-a`
        * Utiliser le tag *`v1`*
        * Ouvrir le port *`9081`* 
* Modifier le service en
    type: NodePort
    port: 9081
* Modifier la configuration pour inclure le monitoring du chart
    * Le micro-service A expose un endpoint de monitoring
        * [/actuator/health](http://localhost:8080/actuator/health)
    * Définir une variable `monitoringPath`
    * Compléter les sections `livenessProbe` et `readinessProbe`
        * Modifier le path
        * Ajouter un initialDelaySeconds (15s)
        * Ajouter un timeoutSeconds (5s)   
* Déployer le chart
* Vérifier dans le dashboard l'état du pod
* (Optionnel) Exposer le endpoint de monitoring en local
    * kubectl get services
    * kubectl port-forward svc/dealing-squid-microservice-a 9081:9081
    * curl
    
## 3. Microservice A v2

* Ajouter une dépendance à mongodb
    * Indice: requirements.yaml
* Mettre à jour les dépendances du chart

<details><summary>Solution</summary>
<p>

    $ helm dep update

</p>
</details>

* Désactiver le mode de passe de mongodb
    * [Configuration du chart mongodb](https://github.com/helm/charts/tree/master/stable/mongodb#configuration)
[< Previous](ex1-using-charts.md) | [Home](README.md) | [Next >]()