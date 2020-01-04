# Kubectl (gcloud sdk)
gcloud auth login
gcloud container clusters get-credentials standard-cluster-1 --zone us-central1-a --project stackitdown
kubectl config current-context

#update latest image
#kubectl set image deployment stack-task-service-deployment stack-task-service=srilakshmi29/stack-task-service:latest