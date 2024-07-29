# Publisher Component

## Overview

The Publisher component in the Real-Time Sports Hub system is responsible for creating topics and publishing messages. Publishers interact primarily with the leader broker to ensure that their messages are delivered to all relevant subscribers in a timely and consistent manner. This component facilitates the dissemination of real-time sports updates to a wide audience.

### Prerequisites

- Java 11 or higher
- Maven 3.6.3 or higher

## Setup Instructions

1. **Navigate to the `publisher` directory:**
   ```sh
   cd publisher
   ```

2. **Package the application using Maven:**
   ```sh
   mvn clean package
   ```

3. **Start the Publisher component:**
   ```sh
   java -jar target/publisher-0.0.1-SNAPSHOT.jar --server.port=8950
   ```

## Key Functionalities

### Topic Management

Publishers can create new topics to which they will publish messages. This is essential for organizing the messages under different categories.

### Message Publication

Publishers send messages to specific topics. These messages are then disseminated by the brokers to all subscribers of the respective topics.

### Resource Discovery

The Publisher component interacts with the Coordinator to discover the leader broker, ensuring it always communicates with the correct broker for topic creation and message publication.

## Core Algorithms

### Leader Election Algorithm (Bully Algorithm)

- **Purpose:** Ensures system consistency and availability by allowing the publisher to interact with the correct leader broker.
- **Process:** The Coordinator manages the leader election and informs the publisher of the current leader broker.

### Heartbeat Mechanism

- **Purpose:** To keep the system informed about the health status of the broker.
- **Process:** The publisher interacts with the leader broker, which sends heartbeat signals to the Coordinator.

## Thymeleaf Controller

The Publisher component includes a Thymeleaf controller for rendering web pages and providing a user interface for managing topics and publishing messages.

### Thymeleaf Endpoints

**Home**
- **Endpoint:** `/`
- **Method:** `GET`
- **Description:** Renders the home page, showing the current topic.
- **Parameters:** None

**Set Topic**
- **Endpoint:** `/set-topic`
- **Method:** `GET`, `POST`
- **Description:** Renders the set-topic page for creating a new topic. Handles the form submission for setting a new topic.
- **Parameters:** `topic` (String): The name of the new topic (POST).

**Publish**
- **Endpoint:** `/publish`
- **Method:** `GET`, `POST`
- **Description:** Renders the publish page for sending messages to a topic. Handles the form submission for publishing a new message.
- **Parameters:** `message` (String): The content of the message (POST).

**Broker**
- **Endpoint:** `/broker`
- **Method:** `GET`
- **Description:** Renders the broker page, showing the current leader broker URL.
- **Parameters:** None

## Conclusion

The Publisher component is essential for creating topics and publishing real-time sports updates. It interacts with the leader broker to ensure messages are delivered efficiently and reliably to all subscribers. The Thymeleaf controller provides a user-friendly interface for managing topics and publishing messages. For more detailed information, including UML diagrams and in-depth explanations, please refer to the [Project Report](../reports/Project_Report.pdf).