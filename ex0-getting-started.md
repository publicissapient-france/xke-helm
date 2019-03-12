# Exercice 1 - Utiliser les charts


## Local Kubernetes cluster

**Mac**

Activer le kubernetes dans "docker for mac"
![kube-for-mac](img/kube-for-mac.png | width=200)

> De préférence mettre `4Go` pour docker for mac

**Linux**

Install [minikube](https://kubernetes.io/docs/tasks/tools/install-minikube/)

## Vérifier Kubernetes Cluster

	$ kubectl version
	$ kubectl config current-context

## Install Kubernetes Dashboard (optionnel)

	$ kubectl apply -f https://raw.githubusercontent.com/kubernetes/dashboard/v1.10.1/src/deploy/recommended/kubernetes-dashboard.yaml
	$ kubectl proxy

Afficher [Kubernetes Dashboard](http://localhost:8001/api/v1/namespaces/kube-system/services/https:kubernetes-dashboard:/proxy/.) avec le browser

Si le bouton Skip n'est pas affiché dans la mire de login:
* Stopper le proxy.
* Vérifier que la configuration n'a pas l'option *enable-skip-login*.
* Patcher la configuration du dashboard.
* Vérifier que l'option a été rajoutée.
* Relancer le proxy.

    
    $ kubectl -n kube-system get deployment kubernetes-dashboard --output yaml | grep enable-skip-login
    $ kubectl -n kube-system patch deployment kubernetes-dashboard --patch "$(cat patch-kubernetes-dashboard-deployment.yaml)"
    $ kubectl -n kube-system get deployment kubernetes-dashboard --output yaml | grep enable-skip-login
    $ kubectl proxy


> Voir [kubernetes/dashboard](https://github.com/kubernetes/dashboard) pour plus d'instructions

## Installer Helm ctl

**Mac**

	$ brew update
	$ brew install kubernetes-helm

**Unix**

Voir [the installation guide](https://helm.sh/docs/using_helm/#installing-helm)


## Initialize Helm and Install Tiller

Installer tiller dans le clusteur Kubernetes vu avec `$ kubectl config current-context` :

	helm init


[< Previous](ex0-getting-started.md) | [Home](README.md) | [Next >](ex1-using-charts.md)