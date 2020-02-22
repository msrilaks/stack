# Kubectl (gcloud sdk)
gcloud auth login
gcloud container clusters get-credentials standard-cluster-1 --zone us-central1-a --project stackitdown
kubectl config current-context

https://accounts.google.com/o/oauth2/auth?access_type=offline&approval_prompt=auto&client_id=13854590117-intkscnrh88jlq2k4vob3k9nl0620s05.apps.googleusercontent.com&redirect_uri=urn:ietf:wg:oauth:2.0:oob&response_type=code&scope=https://www.googleapis.com/auth/gmail.modify%20https://www.googleapis.com/auth/gmail.readonly

#update latest image
#kubectl set image deployment stack-task-service-deployment stack-task-service=srilakshmi29/stack-task-service:latest