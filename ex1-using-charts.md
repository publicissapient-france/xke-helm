# Exercice 1 - Utiliser les charts existants

Le but de l'exercice est de se familiariser avec l'utilisation des charts.
Il existe beaucoup de charts Helm tels que `mongodb`, `postgresql`, `wordpress` et bien d'autres encore.
Il s'agit souvent de "building blocks" que vous pouvez utiliser pour construire vos propres charts.

## 1. Installer `mongodb`

* Chercher le chart `mongodb`
* Installer `mongodb`
* Afficher dans le terminal les releases déployées sur Helm
* Vérifier l'état des pods présents via le [Kubernetes Dashboard](http://localhost:8001/api/v1/namespaces/kube-system/services/https:kubernetes-dashboard:/proxy/.)
* Afficher dans le terminal le statut du pod contenant mongodb avec `helm status`

<details><summary>Solution</summary>
<p>

    $ helm status <release name>

</p>
</details>

* Se connecter à la base mongo (Indice: Voir la section NOTES de la commande précédente)
* Tester la connection avec `> show dbs`
* Supprimer la release
* Afficher dans le terminal les releases
    * Avec le statut DEPLOYED
    * Avec le statut DELETED
    
> <details><summary>Solution</summary>
<p>

    $ helm ls --deployed
    $ helm ls --deleted

</p>
</details>    

## 2. Personnaliser le chart `mongodb`

* Afficher toutes les variables exposées par le chart `mongodb`

<details><summary>Solution</summary>
<p>

    $ helm inspect values stable/mongodb

</p>
</details>


* Installer le chart `mongodb` en personnalisant le MongoDB admin password

<details><summary>Solution</summary>
<p>

    $ helm install --set mongodbRootPassword=test

</p>
</details>    
    
* Afficher les variables surchargées par votre release

<details><summary>Solution</summary>
<p>

    $ helm get values <release name>

</p>
</details>

* Vérifier dans le dashboard que le mot de passe a été changé
* Supprimer la release 

[< Previous](README.md) | [Home](README.md) | [Next >](ex2-create-charts.md)