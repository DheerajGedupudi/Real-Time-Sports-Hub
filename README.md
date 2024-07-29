# Real-Time Sports Hub

## Overview

Real-Time Sports Hub is a distributed publish-subscribe system designed to provide real-time sports score updates. The system is built using Spring Boot and employs RESTful APIs for seamless communication between components. The core components include a Coordinator, Brokers, Publishers, and Subscribers. The system ensures efficient data dissemination, fault tolerance, and scalability through advanced distributed algorithms.

This project was developed as part of the CSEN 317 - Distributed Systems course under the guidance of Professor [Ramin Moazeni](https://www.linkedin.com/in/raminmoazeni/). The project required implementing at least three of the fourteen distributed systems algorithms. We successfully implemented four algorithms, demonstrating the robustness and complexity of our system. Our project received one of the best grades in the class. For a detailed overview, including UML diagrams and other technical details, refer to the [Project_Report.pdf](./reports/Project_Report.pdf) located in the `reports` directory.

## Prerequisites

- Java 11 or higher
- Maven 3.6.3 or higher
- Git

## Setup Instructions

1. Clone the repository:

```sh
git clone https://github.com/DheerajGedupudi/Real-Time-Sports-Hub
```

2. Navigate to the root directory of each component (broker, coordinator, publisher, subscriber) and run the following command to package the application:

```sh
mvn clean package
```

3. To start each component, use the following commands, replacing `<port>` with the desired port number:

### Coordinator

```sh
java -jar target/coordinator-0.0.1-SNAPSHOT.jar --server.port=8080
```

### Broker

```sh
java -jar target/broker-0.0.1-SNAPSHOT.jar --server.port=8380
```

### Publisher

```sh
java -jar target/publisher-0.0.1-SNAPSHOT.jar --server.port=8950
```

### Subscriber

```sh
java -jar target/subscriber-0.0.1-SNAPSHOT.jar --server.port=8560
```

## Detailed Implementation

This project covers four key distributed systems algorithms, showcasing our understanding and application of complex distributed systems concepts.

### Core Algorithms

1. **Leader Election Algorithm (Bully Algorithm)**
   - Ensures system consistency and availability by electing a new leader broker when the current leader fails.
   - Brokers register with the Coordinator and send periodic heartbeat signals to indicate their status.
   - If the Coordinator detects a failure (i.e., missed heartbeats) from the current leader, it initiates a leader election process using the Bully Algorithm.
   - The new leader is selected based on predefined criteria, such as the broker with the highest ID or the earliest registration time, ensuring prompt leader election and maintaining system integrity.

2. **Resource Discovery Protocol**
   - Allows the system to dynamically discover and register new components such as brokers, publishers, and subscribers.
   - Ensures the system remains open and extensible, enabling it to adapt to changing demands and integrate new functionalities without major overhauls.

3. **Data Replication and Synchronization**
   - Ensures data consistency across brokers through replication and synchronization mechanisms.
   - The leader broker periodically synchronizes data (topics, messages, subscribers) with follower brokers.
   - This ensures that all brokers have up-to-date information, even if the leader fails, minimizing latency and ensuring consistency.

4. **Logical Clocks (Lamport Timestamps)**
   - Maintains a consistent order of events and updates across the system.
   - Each broker maintains a logical clock that increments with each significant event (e.g., publishing a message, subscribing to a topic).
   - When brokers communicate, they include their logical clock value, allowing the recipient to update its clock and maintain a consistent event order, preventing conflicts and ensuring data integrity.

### Key Components

1. **Coordinator**
   - Acts as the central authority responsible for maintaining the list of active brokers and managing the leader election process.
   - Receives heartbeat signals from brokers to monitor their health and coordinates the election of a new leader when the current leader fails.
   - Ensures that the system remains operational and that brokers are synchronized.
   - [Coordinator Component](./coordinator/README.md)

2. **Brokers**
   - Facilitate communication between publishers and subscribers by managing topics, storing messages, and distributing them to subscribers.
   - Participate in leader election and send periodic heartbeat signals to the Coordinator to indicate their availability.
   - Two types of brokers:
     - **Leader Broker:** Manages topics and ensures data consistency across the system, handling publisher requests for adding topics and messages.
     - **Follower Brokers:** Replicate data from the leader and can take over leadership if the current leader fails, ensuring high availability and fault tolerance.
   - [Broker Component](./broker/README.md)

3. **Publishers**
   - Entities that send messages to specific topics managed by brokers.
   - Interact primarily with the leader broker to publish new messages and create topics.
   - Ensure that their messages are delivered to all relevant subscribers in a timely and consistent manner.
   - [Publisher Component](./publisher/README.md)

4. **Subscribers**
   - Entities that receive messages from topics of interest.
   - Subscribe to specific topics and rely on brokers to deliver relevant messages.
   - Interact with the leader broker to manage their subscriptions and retrieve messages, and communicate with the Coordinator to obtain information about the current leader broker.
   - [Subscriber Component](./subscriber/README.md)

### Detailed Example Workflow

Consider a scenario where a new message is published to a topic:

- **Publisher Interaction:** The publisher sends a request to the leader broker to publish a message to a specific topic. The request includes the message content and the publisher's logical clock value.
- **Leader Broker Processing:** Upon receiving the request, the leader broker updates its logical clock based on the publisher's clock value, increments its clock, and stores the message. It then propagates the message and the updated clock value to follower brokers for synchronization.
- **Follower Broker Synchronization:** Follower brokers receive the message and the updated clock value from the leader broker. They update their clocks and store the message, ensuring that all brokers have a consistent view of the data.
- **Subscriber Notification:** Subscribers that have registered for the topic receive notifications about the new message. They retrieve the message from the nearest broker (leader or follower) and update their logical clocks accordingly. This ensures that subscribers receive timely updates while maintaining a consistent event order.

## Project Report

For a detailed explanation of the project, including architecture, design decisions, and testing results, refer to the [Project_Report.pdf](./reports/Project_Report.pdf) located in the `reports` directory.

## Conclusion

Real-Time Sports Hub demonstrates the effective use of distributed systems concepts to build a robust, scalable, and fault-tolerant pub-sub architecture. The system successfully addresses the challenges of real-time data dissemination, fault tolerance, and scalability, making it a reliable platform for real-time sports updates.