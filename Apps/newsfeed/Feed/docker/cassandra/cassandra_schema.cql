-- Here, you can execute any CQL commands, e.g.

CREATE KEYSPACE newsFeed_keyspace
    WITH replication = {'class': 'SimpleStrategy', 'replication_factor': 3}
     AND durable_writes = true;

CREATE TABLE newsFeed_keyspace.user_category (userId text PRIMARY KEY,categoryCount map<text, int>);


CREATE TABLE newsFeed_keyspace.user_preferences (userId text PRIMARY KEY,preferences list<text>);


CREATE TABLE newsFeed_keyspace.user_subscriptions (userId text PRIMARY KEY,topicsId list<text>,topicsName list<text>);


