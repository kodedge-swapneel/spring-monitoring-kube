# Monitoring Kubernetes and Spring Boot service using Prometheus and Grafana

## [Click here to watch the video for demonstration.](https://youtu.be/5c-o6PKlhEQ)

In this project, I have demonstrated how to monitor Kubernetes and Spring Boot service deployed int the Kubernetes cluster using Prometheus and created a dashboard in Grafana to visualize the metrics.

### Prerequisites:
 - [Docker](https://docs.docker.com/engine/install/) or [Docker alternative - Colima](https://github.com/abiosoft/colima)
 - [Minikube](https://minikube.sigs.k8s.io/docs/start/)
 - [Helm](https://helm.sh/docs/intro/install/)
 - [kubectl](https://kubernetes.io/docs/tasks/tools/)
 - IDE and JDK


### Prerequisites on machine :
1. Start Docker
2. Start minikube using ```minikube start --driver=docker```


### Setup Prometheus and Grafana 
  1. Get Helm repository info
        ```
        helm repo add prometheus-community https://prometheus-community.github.io/helm-charts
        helm repo update
        ```
  2. Install Helm chart
      ```
      helm install [RELEASE_NAME] prometheus-community/kube-prometheus-stack
      ```
      - Note :
          - Replace RELEASE_NAME with your custom name. For example name used in the demonstration : prometheus
          - Hence command will be ` helm install prometheus prometheus-community/kube-prometheus-stack`
  3. For more information related to command and repository of kube-prometheus-stack, [Click here](https://github.com/prometheus-community/helm-charts/tree/main/charts/kube-prometheus-stack)
  4. Once helm chart is installed, verify it with commands like
        - `helm ls` to check helm chart installed.
        - `kubectl get all` to see all deployed resources in Kubernetes. Please wait until all resources UP in kubernetes.
  5. #### Now to access the prometheus:
      - Run `kubectl get services`
      - Copy service name of Prometheus and run follwing command :
        ```
        kubectl port-forward service/prometheus-kube-prometheus-prometheus 9090
        ```
      - Note:
          - Here use your deployed service name at the place of "prometheus-kube-prometheus-prometheus".
          - Use the port that shows in the service port section.
          - Now open the browser and run the url : `http://localhost:9090`. If this does not work try running url returned by port-forward command.
  6. #### To access the Grafana:
     
       - Copy service name of Grafana and run following command :
         ```
         kubectl port-forward service/prometheus-grafana 80
         ```
           - Note:
               - Here use your deployed service name at the place of "prometheus-grafana".
               - Also use the port that shows in the service ports section.
               - Generally it will be 80.
               - In case this command give any `bind: permission denied` issue,
               - then run command with sudo like this  `sudo kubectl port-forward service/prometheus-grafana 80`
       
       - Now open the browser and run url : `http://localhost:80`. If this does not work try running url returned by port-forward command.
       - Here username and password will be `username : admin and password : prom-operator`
       - In case this password does not works then do following:

            ```
            kubectl get secret
            ``` 
          there should be secrets for Grafana. Copy its name, and run following command :
          ```
          kubectl get secret --namespace default prometheus-grafana  -o jsonpath="{.data.admin-password}" | base64 --decode ; echo
          ```
          Note:  Here replace "prometheus-grafana" with your actual deployed secret name which returned by the command `kubectl get secret`


### Monitor Spring Boot service from Kubernetes:

- Clone this repository.
- cd into this repository from the terminal.
- Build project using command : ./gradlew clean build
- Start docker in your machine.
- Start minikube using ``` minikube start --driver=docker ```  (Ignore this command if minikube is already started.)
- Enable docker env using command :  ```eval $(minikube docker-env)  ```  [Command Reference](https://minikube.sigs.k8s.io/docs/commands/docker-env/)
- Build docker image in minikube : ```docker build -t spring-monitoring-kube . ```
- To deploy on kubernetes cluster run command : ``` helm install mychart ytchart ```
- To see deployed helm chart : ```helm ls ```
- Check deployments : ```kubectl get all ```
- To access the REST api, do following:
  - Run `kubectl get services` command
  - Copy spring-boot service name
  - Then run port-forward command
 
    ```
    kubectl port-forward service/mychart-ytchart 8080
    ```
- Test by calling `http://localhost:8080/data`
- Now login to Grafana Dashboard.
- Import dashboard, search dashboard here : [grafana/dashboards](https://grafana.com/grafana/dashboards/). For example try with dashboard id: 11378

