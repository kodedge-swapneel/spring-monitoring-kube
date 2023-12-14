FROM openjdk:17-jdk-slim
EXPOSE 8080
ADD build/libs/spring-monitoring-kube.jar spring-monitoring-kube.jar
ENTRYPOINT ["java","-jar","/spring-monitoring-kube.jar"]