# Subscriber Component

## Overview

The Subscriber component in the Real-Time Sports Hub system is responsible for subscribing to topics and receiving messages. Subscribers interact with brokers to manage their subscriptions and retrieve messages. This component ensures that subscribers receive timely updates for the topics they are interested in, providing a seamless real-time sports update experience.

### Prerequisites

- Java 11 or higher
- Maven 3.6.3 or higher

## Setup Instructions

1. **Navigate to the `subscriber` directory:**
   ```sh
   cd subscriber
   ```

2. **Package the application using Maven:**
   ```sh
   mvn clean package
   ```

3. **Start the Subscriber component:**
   ```sh
   java -jar target/subscriber-0.0.1-SNAPSHOT.jar --server.port=8560
   ```

## Key Functionalities

### Topic Subscription

Subscribers can subscribe to specific topics of interest. This allows them to receive updates whenever a new message is published to those topics.

### Message Retrieval

Subscribers can retrieve messages for the topics they are subscribed to. This ensures they are always up-to-date with the latest information.

### Resource Discovery

The Subscriber component interacts with the Coordinator to discover the current leader broker. This ensures that subscribers always communicate with the correct broker for managing their subscriptions and retrieving messages.

## Core Algorithms

### Leader Election Algorithm (Bully Algorithm)
**Purpose:** Ensures system consistency and availability by allowing the subscriber to interact with the correct leader broker.
**Process:** The Coordinator manages the leader election and informs the subscriber of the current leader broker.

### Heartbeat Mechanism
**Purpose:** To keep the system informed about the health status of the broker.
**Process:** The subscriber interacts with the leader broker, which sends heartbeat signals to the Coordinator.

## API Endpoints

### Get Leader
**Endpoint:** `/api/leader`
**Method:** `GET`
**Description:** Retrieves the current leader broker.
**Parameters:**
- `timestamp` (long): The logical clock timestamp.

### Get Topics
**Endpoint:** `/api/topics`
**Method:** `GET`
**Description:** Retrieves the list of topics managed by the broker.
**Parameters:**
- `timestamp` (long): The logical clock timestamp.

### Subscribe to Topic
**Endpoint:** `/api/subscribe`
**Method:** `POST`
**Description:** Subscribes to a specific topic.
**Parameters:**
- `topic` (String): The topic to subscribe to.
- `timestamp` (long): The logical clock timestamp.

### Get Messages
**Endpoint:** `/api/messages`
**Method:** `GET`
**Description:** Retrieves messages for a specific topic.
**Parameters:**
- `topic` (String): The topic of interest.
- `timestamp` (long): The logical clock timestamp.

### Ping
**Endpoint:** `/api/ping`
**Method:** `GET`
**Description:** Responds with "pong" to check subscriber's availability.
**Parameters:**
- `timestamp` (long): The logical clock timestamp.

## Thymeleaf Controller

The Subscriber component includes a Thymeleaf controller for rendering web pages and providing a user interface for managing subscriptions and retrieving messages.

### Thymeleaf Endpoints

**Home**
- **Endpoint:** `/`
- **Method:** `GET`
- **Description:** Renders the home page.
- **Parameters:** None

**Broker**
- **Endpoint:** `/broker`
- **Method:** `GET`
- **Description:** Renders the broker page, showing the current leader broker URL.
- **Parameters:** None

**Topics**
- **Endpoint:** `/topics`
- **Method:** `GET`
- **Description:** Renders the topics page, showing the list of topics and the topics the subscriber is subscribed to.
- **Parameters:** None

**Subscribe**
- **Endpoint:** `/subscribe`
- **Method:** `POST`
- **Description:** Handles the form submission for subscribing to a topic.
- **Parameters:** `topic` (String): The topic to subscribe to.

**Unsubscribe**
- **Endpoint:** `/unsubscribe`
- **Method:** `POST`
- **Description:** Handles the form submission for unsubscribing from a topic.
- **Parameters:** `topic` (String): The topic to unsubscribe from.

**Subscribed Topics**
- **Endpoint:** `/subscribed-topics`
- **Method:** `GET`
- **Description:** Renders the page showing the topics the subscriber is subscribed to.
- **Parameters:** None

**Messages**
- **Endpoint:** `/messages`
- **Method:** `GET`
- **Description:** Renders the messages page, showing the messages for a specific topic.
- **Parameters:** `topic` (String): The topic of interest.

## Conclusion

The Subscriber component is essential for receiving real-time updates in the Real-Time Sports Hub system. It allows users to subscribe to topics, manage their subscriptions, and retrieve messages efficiently. The Thymeleaf controller provides a user-friendly interface for these interactions. For more detailed information, including UML diagrams and in-depth explanations, please refer to the [Project Report](../reports/Project_Report.pdf).