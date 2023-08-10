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
This app has an associated SQL database that keeps user account info and recieves user requests such as login, logout, register. Users of type admin had privilages to edit website data such as editors picks and categories.
### Post App 
This app has an associated database of BBC posts. It recieves admin requests to add new articles as well as user requests to retrieve an post.
### Media APP 
This is the associated blob store which saves media such as photos included in BBC articles and videos. It recieves requests to retrieve media content post authentication of a request.
### Search App 
This app uses ElasticSearch database to store article metadata (title, summary, keywords) and efficiently query user text search requests. 
### Directory App 
This app maintains a view of categorized clustered by category, within each category articles are sorted by views. It also uses ElasticSearch to build those indexes. The directory app is internally used by the newsfeed generator app to get information such as the newest or most popular articles to be added to users newsfeeds
### Views App 
The views app is a log that logs user views on posts and their history. The search app periodically updates its article view counts from the views app's log. A separate app was created to record user views as its extremely write intensive so as not to affect the search apps performance.
### Newfeed Generator App 
This app has an associated database of users and their preferences. It generates newsfeeds based on groupings of user preferences and caches them for newsfeed app requests. User preferences are also periodically updated from the views apps logs.
### Newsfeed App 
This app recieves user newsfeed requests. It returns one of the pregenerated customized newsfeeds from the cache.

Note: each app code is in its own branch with its ReadMe file on the services it provides and how to run it.

load testing documents: https://drive.google.com/drive/folders/1CRLNShxbNLXgZCoz6EflRJ4WN2WR28s5?usp=sharing

