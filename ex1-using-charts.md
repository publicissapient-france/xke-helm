# Exercice 1 - Utiliser les charts

## 1. Installer `mongodb`

* Chercher le chart `mongodb`
* Installer `mongodb`
* Afficher dans le terminal les releases sur helm
* Afficher l'état des pods présents via le [Kubernetes Dashboard](http://localhost:8001/api/v1/namespaces/kube-system/services/https:kubernetes-dashboard:/proxy/.)
* Afficher dans le terminal le statut du pod contenant mongodb (`helm status`)
* Se connecter à la base mongo
    * Indice: Lire la section NOTES de la commande précédente
* Tester la connection avec `> show dbs`
* Supprimer la release
* Afficher dans le terminal les releases
    * Avec le statut DEPLOYED
    * Avec le statut DELETED

## 2. Personnaliser le chart `mongodb`

* Installer le chart `mongodb` en personnalisant le master admin password
    * Indice: --set
    * [Configuration du chart mongodb](https://github.com/helm/charts/tree/master/stable/mongodb#configuration)
* Vérifier dans le dashboard que le mot de passe a été changé
* Supprimer la release 

[< Previous](README.md) | [Home](README.md) | [Next >](ex2-create-charts.md)