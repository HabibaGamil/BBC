## Generate Service

- The Generate Service is responsible for generating and processing all the information necessary to generate the news feed.
- Generate service communication with
    - Search Service
    - Post Service
    - controller
- The main goal of the Generate Service is to prepare all the data that the feed service will need to send it to the controller.

# Communication:
### Communication with search service:

    - The Generate Service sends a request to the Search Service
    - The Search Service replies with the response, which contains the metadata of specific 
    categories/sub-categories/topics (depending on what is specified in the request).
    - The Generate Service updates the cache with the information taken from the response.

### Communication with post service:
    - The Post Service sends an event when there is an important post that should be posted.
    - The Generate Service updates the cache with the information taken from the event.

### Communication with the controller:

    - 
# Cache:

    - In this service, we use Redis cache.
    - We created 3 tables in the cache.
        - Categories_repository which contains categories metadata
        - Sub_Categories_repository which contains sub_categories metadata
        - Topics_repository which contains topics metadata
    - we also created a redis hash which will contains the new posts 
# Database:

# Running the service:
 - you have to follow the following steps:
    - Download redis server from https://github.com/microsoftarchive/redis/releases
    - Open the server from redis-server application
    - Run LastdemoMavenApplication class
