# Coordinator Component

## Overview

The Coordinator component is the central authority in the Real-Time Sports Hub system. It is responsible for maintaining the list of active brokers, managing the leader election process, and ensuring system stability and synchronization. The Coordinator receives heartbeat signals from brokers to monitor their health and coordinates the election of a new leader when the current leader fails. This component ensures the system remains operational and that brokers are synchronized.

### Prerequisites

- Java 11 or higher
- Maven 3.6.3 or higher

## Setup Instructions

1. **Navigate to the `coordinator` directory:**

```sh
cd coordinator
```

2. **Package the application using Maven:**

```sh
mvn clean package
```

3. **Start the Coordinator component:**

```sh
java -jar target/coordinator-0.0.1-SNAPSHOT.jar --server.port=8080
```

## Key Functionalities

### Broker Registration

Brokers register with the Coordinator by sending a registration request. The Coordinator maintains a list of active brokers and assigns a unique ID to each broker.

### Heartbeat Monitoring

Brokers send periodic heartbeat signals to the Coordinator to indicate their health status. The Coordinator monitors these heartbeats to detect broker failures. If a broker fails to send a heartbeat within a specified interval, it is considered offline.

### Leader Election

The Coordinator is responsible for managing the leader election process. When a broker fails, the Coordinator initiates an election to select a new leader from the remaining brokers. The leader election algorithm used is the Bully Algorithm, which selects the broker with the highest ID or the earliest registration time as the new leader.

### Resource Discovery

The Coordinator maintains an updated list of active brokers and their statuses. It provides this information to other components (publishers, subscribers) when requested, facilitating dynamic resource discovery and ensuring that the system remains open and extensible.

## Core Algorithms

### Leader Election Algorithm (Bully Algorithm)

- **Purpose:** To ensure system consistency and availability by electing a new leader broker when the current leader fails.
- **Process:** The algorithm selects the new leader based on predefined criteria, such as the broker with the highest ID or the earliest registration time, ensuring prompt leader election and maintaining system integrity.

### Heartbeat Mechanism

- **Purpose:** To continuously monitor the health status of brokers.
- **Process:** Brokers send periodic heartbeat signals to the Coordinator. If a broker fails to send a heartbeat within a specified interval, it is considered offline, and the Coordinator may trigger a leader election.

## API Endpoints

### Registration

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

## Thymeleaf Controller

The Coordinator component also includes a Thymeleaf controller to render web pages for monitoring and management purposes.

### Home

**Endpoint:** `/`  
**Method:** `GET`  
**Description:** Renders the home page.

### Brokers

**Endpoint:** `/brokers`  
**Method:** `GET`  
**Description:** Renders the brokers page, showing the list of active brokers.  
**Parameters:** None

### Leader

**Endpoint:** `/leader`  
**Method:** `GET`  
**Description:** Renders the leader page, showing the current leader broker.  
**Parameters:** None

By implementing these core functionalities and algorithms, the Coordinator component ensures that the Real-Time Sports Hub system remains robust, scalable, and efficient. This component plays a vital role in maintaining the system's stability, managing broker interactions, and ensuring consistent data synchronization across the network.

For more detailed information, please refer to the [Project Report](../reports/Project_Report.pdf).