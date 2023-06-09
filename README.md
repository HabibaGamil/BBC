# BBC Newsfeed Website Replica
This repository represent out project for "Architecture of Massively Scalable Apps" Course. We implemented a bbc replica based on microservice architecture and design patterns.
<br/>
<br/>
<br/>
![Alt text](https://github.com/HabibaGamil/BBC/assets/75835933/a14c0404-c1f8-4492-8bd6-d5bca548b7ec)

# System Components

## Config Server
Following microservices cross cutting patterns, we implemented a central configuration server from which all app configurations are read, changes in a particular apps configuration properties reflect for all running instances as well as future instances. For our project, we used spring cloud config to implement the config server. Apps connect to the config server through http.

## Eureka Server
Eureka Server contains all the information regarding the deployed instances of all the apps in our application. Load balancers use the eureka server to balance the traffic fairly over the available instances of the app. Any new instance deployed in the system automatically registers with the eureka server.

## Controller
The controller acts as a remote control for the system. It administers apps remotely through the a dedicated message queue for each type of app. App instances of a misroservice listen to the controller in a publish subscribe fashion. App Logic is implemented based on the Command Pattern and the controller has the ability to add/update/delete an app command at runtime. Apps recieve controller commands through the message queue. The controller command might contain parametrs such as bytecode sent to an app to update a certain behavior/command using java class loaders.

## MQ Server
For our project it was required that apps receive user requests through a dedicated message queue rather than through HTTP protocal. The MQ server acts as an intermediary between the API gateway and the apps by placing the user request in the correct meassage queue for processing.

## API Gateway
API Gateway acts as a single entry point to the system. It uses the Eureka server to load balance in the undelying MQ servers. 

## Logging server
The logging server is a centralized location where all apps logs are kept. We used Zipkin as our logging server

## Health Monitoring
For our project, we used Spring boot's built in health micrometer along with prometheus and Grafana to built custom dashboards through which apps health could be monitored. Using Grafana, we also used web hooks which notify the controller server when certain conditions/events happen that would cause the controller to take corrective actions or scale the system apon spikes of traffic.

## Deployment Server
The deployement server is responsible for deploying new instances of our system to the cloud. It takes commands from the controller server regarding which app to deploy.

## Miniservices Implemented

### User App 
### Post App 
### Media APP 
### Search App 
### Directory App 
### Views App 
### Newfeed Generator App 
### Newsfeed App 

Note: each app code is in its own branch with its ReadMe file on the services it provides and how to run it.

load testing documents: https://drive.google.com/drive/folders/1CRLNShxbNLXgZCoz6EflRJ4WN2WR28s5?usp=sharing

