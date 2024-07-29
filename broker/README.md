# Broker Component

## Overview

The Broker component serves as an intermediary between publishers and subscribers in the Real-Time Sports Hub system. It is responsible for managing topics, storing messages, and distributing them to subscribers. Brokers also participate in leader election and send periodic heartbeat signals to the Coordinator to indicate their availability. This component ensures data consistency and facilitates real-time updates across the network.

The Broker component can function both as a leader broker and a follower broker using the same codebase. When a broker is promoted to a leader, it takes on additional responsibilities such as coordinating the actions of follower brokers, managing topics, and ensuring data consistency. This dynamic role promotion ensures that the system remains resilient and adaptable.

### Prerequisites

- Java 11 or higher
- Maven 3.6.3 or higher

## Setup Instructions

1. **Navigate to the `broker` directory:**
   ```sh
   cd broker
   ```

2. **Package the application using Maven:**
   ```sh
   mvn clean package
   ```

3. **Start the Broker component:**
   ```sh
   java -jar target/broker-0.0.1-SNAPSHOT.jar --server.port=8380
   ```

## Key Functionalities

### Broker Registration

Brokers register with the Coordinator by sending a registration request. The Coordinator maintains a list of active brokers and assigns a unique ID to each broker.

### Heartbeat Monitoring

Brokers send periodic heartbeat signals to the Coordinator to indicate their health status. The Coordinator monitors these heartbeats to detect broker failures. If a broker fails to send a heartbeat within a specified interval, it is considered offline.

### Leader Election

The Broker component participates in the leader election process managed by the Coordinator. When a broker fails, the Coordinator initiates an election to select a new leader from the remaining brokers. The leader broker coordinates the actions of follower brokers, manages topics, and ensures data consistency.

### Dynamic Role Promotion

Brokers can be promoted to the role of a leader broker when the current leader fails. As a leader, the broker takes on additional responsibilities such as handling publisher requests for adding topics and messages, synchronizing data with follower brokers, and ensuring data consistency across the network.

### Message Handling

Brokers manage topics, store messages, and distribute them to subscribers. The leader broker handles publisher requests for adding topics and messages, synchronizes this data with follower brokers, and ensures timely delivery to subscribers.

### Resource Discovery

The Broker component interacts with the Coordinator to obtain information about active brokers and their statuses. This facilitates dynamic resource discovery and ensures that the system remains open and extensible.

## Core Algorithms

### Leader Election Algorithm (Bully Algorithm)

- **Purpose:** To ensure system consistency and availability by electing a new leader broker when the current leader fails.
- **Process:** The algorithm selects the new leader based on predefined criteria, such as the broker with the highest ID or the earliest registration time, ensuring prompt leader election and maintaining system integrity.

### Heartbeat Mechanism

- **Purpose:** To continuously monitor the health status of brokers.
- **Process:** Brokers send periodic heartbeat signals to the Coordinator. If a broker fails to send a heartbeat within a specified interval, it is considered offline, and the Coordinator may trigger a leader election.

### Data Replication and Synchronization

- **Purpose:** To ensure data consistency across brokers.
- **Process:** The leader broker periodically synchronizes data (topics, messages, subscribers) with follower brokers, ensuring all brokers have up-to-date information and maintaining data integrity even if the leader fails.

### Logical Clocks (Lamport Timestamps)

- **Purpose:** To maintain a consistent order of events and updates.
- **Process:** Each broker maintains a logical clock that increments with each significant event. When brokers communicate, they include their logical clock value, allowing the recipient to update its clock and maintain a consistent event order.

## API Endpoints

### Broker Registration

- **Endpoint:** `/api/register`
- **Method:** `POST`
- **Description:** Registers a new broker with the Coordinator.
- **Parameters:**
  - `brokerUrl` (String): The URL of the broker.
  - `timestamp` (long): The logical clock timestamp.

### Heartbeat

- **Endpoint:** `/api/heartbeat`
- **Method:** `POST`
- **Description:** Receives heartbeat signals from brokers.
- **Parameters:**
  - `brokerUrl` (String): The URL of the broker.
  - `timestamp` (long): The logical clock timestamp.

### Get Brokers

- **Endpoint:** `/api/brokers`
- **Method:** `GET`
- **Description:** Retrieves the list of active brokers.
- **Parameters:**
  - `timestamp` (long): The logical clock timestamp.

### Get Leader

- **Endpoint:** `/api/leader`
- **Method:** `GET`
- **Description:** Retrieves the current leader broker.
- **Parameters:**
  - `timestamp` (long): The logical clock timestamp.

### Get Topics

- **Endpoint:** `/api/topics`
- **Method:** `GET`
- **Description:** Retrieves the list of topics managed by the broker.
- **Parameters:**
  - `timestamp` (long): The logical clock timestamp.

### Get Messages

- **Endpoint:** `/api/messages`
- **Method:** `GET`
- **Description:** Retrieves messages for a specific topic and subscriber.
- **Parameters:**
  - `topic` (String): The topic of interest.
  - `subscriberUrl` (String): The URL of the subscriber.
  - `timestamp` (long): The logical clock timestamp.

### Get All Data

- **Endpoint:** `/api/data`
- **Method:** `GET`
- **Description:** Retrieves all data managed by the broker (topics, messages, subscribers).
- **Parameters:**
  - `timestamp` (long): The logical clock timestamp.

### Add Topic

- **Endpoint:** `/api/add-topic`
- **Method:** `POST`
- **Description:** Adds a new topic to the broker.
- **Parameters:**
  - `topic` (String): The name of the new topic.
  - `timestamp` (long): The logical clock timestamp.

### Add Message

- **Endpoint:** `/api/add-message`
- **Method:** `POST`
- **Description:** Adds a new message to a topic.
- **Parameters:**
  - `topic` (String): The topic to which the message is to be added.
  - `message` (String): The content of the message.
  - `timestamp` (long): The logical clock timestamp.

### Add Subscriber

- **Endpoint:** `/api/add-subscriber`
- **Method:** `POST`
- **Description:** Adds a subscriber to a topic.
- **Parameters:**
  - `topic` (String): The topic to which the subscriber is to be added.
  - `subscriberUrl` (String): The URL of the subscriber.
  - `timestamp` (long): The logical clock timestamp.

### Remove Subscriber

- **Endpoint:** `/api/remove-subscriber`
- **Method:** `POST`
- **Description:** Removes a subscriber from a topic.
- **Parameters:**
  - `topic` (String): The topic from which the subscriber is to be removed.
  - `subscriberUrl` (String): The URL of the subscriber.
  - `timestamp` (long): The logical clock timestamp.

### Ping

- **Endpoint:** `/api/ping`
- **Method:** `GET`
- **Description:** Responds with "pong" to check broker's availability.
- **Parameters:**
  - `timestamp` (long): The logical clock timestamp.

### Get Subscribers

- **Endpoint:** `/api/subscribers`
- **Method:** `GET`
- **Description:** Retrieves the list of subscribers with their subscribed topics.
- **Parameters:**
  - `timestamp` (long): The logical clock timestamp.

## Thymeleaf Controller

The Broker component also includes a Thymeleaf controller for rendering web pages and providing a user interface for managing the broker.

### Thymeleaf Endpoints

**Home**
- **Endpoint:** `/`
- **Method:** `GET`
- **Description:** Renders the home page with the current logical clock timestamp.

**Broker**
- **Endpoint:** `/broker`
- **Method:** `GET`
- **Description:** Renders the broker page, showing the current leader and logical clock timestamp.
- **Parameters:** `timestamp` (long): The logical clock timestamp.

**Leader**
- **Endpoint:** `/leader`
- **Method:** `GET`
- **Description:** Renders the leader page, showing the current leader and logical clock timestamp.
- **Parameters:** `timestamp` (long): The logical clock timestamp.

**Brokers**
- **Endpoint:** `/brokers`
- **Method:** `GET`
- **Description:** Renders the brokers page, showing the list of active brokers and the logical clock timestamp.
- **Parameters:** `timestamp` (long): The logical clock timestamp.

**Topics**
- **Endpoint:** `/topics`
- **Method:** `GET`
- **Description:** Renders the topics page, showing the list of topics managed by the broker and the logical clock timestamp.
- **Parameters:** `timestamp` (long): The logical clock timestamp.

**Messages**
- **Endpoint:** `/messages`
- **Method:** `GET`
- **Description:** Renders the messages page, showing the messages for a topic and the logical clock timestamp.
- **Parameters:** `timestamp` (long): The logical clock timestamp.

**Subscribers**
- **Endpoint:** `/subscribers`
- **Method:** `GET`
- **Description:** Renders the subscribers page, showing the list of subscribers and their subscribed topics, along with the logical clock timestamp.
- **Parameters:** `timestamp` (long): The logical clock timestamp.

## Conclusion

The Broker component is a critical part of the Real-Time Sports Hub system, providing robust and scalable message handling, leader election, and data synchronization functionalities. By supporting both leader and follower roles within the same codebase, it ensures high availability and fault tolerance. The detailed APIs and Thymeleaf controllers offer comprehensive interaction capabilities, making the broker a versatile and essential component in the distributed system architecture. For more detailed information, including UML diagrams and in-depth explanations, please refer to the [Project Report](../reports/Project_Report.pdf).