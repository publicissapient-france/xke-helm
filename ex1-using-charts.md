# Exercice 1 - Utiliser les charts


## 1. Installer `mongodb`

* Chercher le chart `mongodb`
* Installer `mongodb`
* Afficher les releases sur helm
* Avec [Kubernetes Dashboard](http://localhost:8001/api/v1/namespaces/kube-system/services/https:kubernetes-dashboard:/proxy/.) voir les pods
* Se connecter au mongo à l'aide de NOTES.txt affichés sur console
* Tester la connection avec `> show dbs`
* Supprimer la release 
* Afficher les releases
* Afficher les releases supprimés


## 2. Personnaliser le chart `mongodb`

* Installer le chart `mongodb` en personnalisant le `mongodbRootPassword` à `testPassword` 
* Verifier que le mot de passe est changé
* Ajouter quelques documents dans mongodb

	> use test
	> db.person.insert({name:"Steve Jobs", "age": 56})
	> db.person.insert({name:"Jeanne Calment", "age": 118})

* Supprimer la release 

[< Previous](README.md) | [Home](README.md) | [Next >](ex2-creating-charts.md)