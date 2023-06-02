# BBC
# Directory, Search, and Views App

Directory, Search, and Views App are three separate microservices that work together to provide a comprehensive platform for managing and searching posts. 

## Directory App

The Directory app is responsible for maintaining a directory of all posts, including metadata such as title, author, and category. It provides endpoints for adding, updating, and deleting posts, as well as searching for posts based on various criteria.

## Search App

The Search app is responsible for indexing the content of all posts and providing a search functionality that allows users to find posts based on keywords. It uses Elasticsearch to index the posts and provides endpoints for searching the index based on various criteria.

## Views App

The Views app is responsible for tracking views of each post and updating the view count in the search and directory post metadata through message queues, using fanout exchange. It provides endpoints for adding, updating, and deleting views, as well as publishing messages to update the records in directory and search.

## Prerequisites

To run these applications, you will need to have the following prerequisites installed on your system:

- RabbitMQ: You can download RabbitMQ from the official website: https://www.rabbitmq.com/download.html. Once downloaded, start RabbitMQ through the command line interface by accessing the path of the installed software and typing `rabbitmq-server`.
- Docker: You can download Docker from the official website: https://www.docker.com/products/docker-desktop. Once installed, you can start Docker to run the required services.
- Elasticsearch: You can download and install Elasticsearch from the official website: https://www.elastic.co/downloads/elasticsearch. Once installed, you can start Elasticsearch to run the Search app.

***

# Directory Mini Service

The Directory Mini Service is a Spring Boot application that provides APIs for managing directories and post metadata. It consists of two controllers: DirectoryController and PostMetadataController.

## Prerequisites

Before running the application, you will need to have Elasticsearch and Kibana installed on your system. You can download Elasticsearch and Kibana from the following URL: https://www.elastic.co/downloads/past-releases#elasticsearch. Make sure to set the version to 7.17.9 since this is the version used by the app. Once downloaded, run Elasticsearch and Kibana through the command line interface by accessing the path of the installed software and typing `elasticsearch.bat` or `kibana.bat` accordingly.

The application should then be accessible at http://localhost:8087.

## Endpoints


### DirectoryController

The DirectoryController provides the following endpoints:

* `GET /api/directory/categories`: Returns all categories.
* `GET /api/directory/subcategories`: Returns all subcategories.
* `GET /api/directory/topics`: Returns all topics.
* `GET /api/directory/category/{id}`: Returns a category by ID.
* `GET /api/directory/subcategory/{id}`: Returns a subcategory by ID.
* `GET /api/directory/topic/{id}`: Returns a topic by ID.
* `GET /api/directory/subcategoriesOfCategory/{id}`: Returns all subcategories belonging to a category.
* `GET /api/directory/topicsOfSubcategory/{id}`: Returns all topics belonging to a subcategory.
* `GET /api/directory/relatedTopics/{id}`: Returns all related topics.

### PostMetadataController

The PostMetadataController provides the following endpoints:

* `GET /api/postMetadata`: Returns all post metadata.
* `GET /api/postMetadata/{id}`: Returns post metadata by ID.
* `POST /api/postMetadata`: Saves post metadata.
* `POST /api/postMetadata/search`: Searches for post metadata using a search request DTO.

### IndexController

The IndexController provides the following endpoint:

* `POST /api/index/recreate`: Recreates the Elasticsearch index.

***

# Search Mini Service

Search app is responsible for retrieving post metadata that contain search terms provided by the user in the search field. It also performs pagination, and sorts metadata according to view count and date.

The application should then be accessible at http://localhost:8086.

## Endpoints

### IndexController

The IndexController provides the following endpoint:

* `POST /api/index/recreate`: Recreates the Elasticsearch index.

### PostMetadataController

The PostMetadataController provides the following endpoints:

* `GET /api/postMetadata`: Returns all post metadata.
* `GET /api/postMetadata/{id}`: Returns post metadata by ID.
* `POST /api/postMetadata`: Saves post metadata.
* `POST /api/postMetadata/search`: Searches for post metadata using a search request DTO.

***

# ViewsApp

ViewsApp is responsible for updating the view count of each post after it's being clicked by a user. The view count is stored in MongoDB and is later updated in the search and directory post metadatas through message queues, using fanout exchange.

## Getting Started

To get started with ViewsApp, you will need to have Docker Compose installed on your system. You can download Docker Compose from the official website: https://docs.docker.com/compose/install/. Once installed, navigate to the ViewsApp directory and run the following command to start MongoDB:

```
docker-compose up
```

After MongoDB is up and running, you can start the ViewsApp.

The application should then be accessible at http://localhost:8082.

## Endpoints

The ViewsApp provides the following endpoints:

* `GET /api/views`: Returns all views.
* `GET /api/views/{id}`: Returns views by ID.
* `POST /api/views`: Saves a view for a post.
* `DELETE /api/views/{id}`: Deletes a view by ID.
* `POST /api/views/publish_views`: Publishes a message on the queue for directory and search. This updates the records in directory and search and then deletes its records.
 
***

work documentation [here] (https://docs.google.com/document/d/1Jv8HhPOCEqj_thccbLFl6S44yQH1g84pkvIfNckCTqI/edit?usp=sharing)
