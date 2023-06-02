# BBC

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
