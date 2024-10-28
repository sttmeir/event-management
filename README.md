H2 database can be accessed through: http://localhost:8081/h2-console

JUnit: 100% classes, 88% lines covered.

New methods in the EventServiceImpl class are below:

sendMessage: Sends a message to a Kafka topic, which can be picked up by any consumer listening to that topic.

listen: Listens for messages on the event-updates Kafka topic, parses the message to extract an event ID and new name, and updates the corresponding event in the database.
