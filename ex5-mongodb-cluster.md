# Exercice 4 - (Advanced) Mongodb cluster 

Actuellement, chaque microservice a sa propre instance de `mongodb`. 
Ces `mongodb` tournent actuellement en mono-instances, ce qui évidemment n'est pas adapté à l'environnement de production.     
Il n'est également pas opportun non plus d'avoir un clusteur mongodb par microservice.
La bonne pratique (à débattre) est d'installer le cluster mongodb redondé pour le partager entre differents microservices.
Il est évidemment très important d'isoler les bases de chaque microservice au sein de ce clusteur.   


Le but de cet exercice est d'installer le clusteur `mongodb` à l'aide de son propre chart pérsonnalisé. 
Et ensuite pointer les microservices A et B vers ce clusteur.  

<p>
<img src="img/target-architecture.png" height="300">
</p>

## 1. Clusteur mongodb

### Instructions

* Créer le nouveau chart `xke-helm-mongodb`
* Virer les templates
* Parametrer le `values.yaml` pour passer le mongodb en mode 

<details><summary>Solution</summary>
<p>

File `xke-helm-mongodb/values.yaml`

    ...
    
    replicaSet:
      
    

</p>
</details>



*Bravo ! Vous êtes arrivés à la fin de hands-on ! Felicitations !!!* 

[< Previous](ex4-template-helpers.md) | [Home](README.md)